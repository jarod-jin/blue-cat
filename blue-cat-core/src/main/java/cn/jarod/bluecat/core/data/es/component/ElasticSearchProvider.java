package cn.jarod.bluecat.core.data.es.component;

import cn.jarod.bluecat.core.api.exception.BaseException;
import cn.jarod.bluecat.core.security.annotation.ContextType;
import cn.jarod.bluecat.core.api.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.security.pojo.DataConditionDO;
import cn.jarod.bluecat.core.security.pojo.DataPermissionDO;
import cn.jarod.bluecat.core.api.util.ApiResultUtil;
import cn.jarod.bluecat.core.common.utils.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.assertj.core.internal.Conditions;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
public abstract class ElasticSearchProvider {

    private final RestHighLevelClient esClient;
    
    private static final int retryLimit = 3;

    public ElasticSearchProvider(RestHighLevelClient esClient) {
        this.esClient = esClient;
    }


    /**
     * 搜索
     *
     * @param index
     * @param searchSourceBuilder
     * @param clazz 需要封装的obj
     * @param pageable
     * @return Page<T>
     */
    public <T> Page<T> search(String index, SearchSourceBuilder searchSourceBuilder, Class<T> clazz,
                              Pageable pageable){

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(searchSourceBuilder);
        log.info("DSL语句为：{}",searchRequest.source().toString());
        try {
            SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);
            List<T> dataList = new LinkedList<>();
            Arrays.stream(response.getHits().getHits()).forEach(hit-> dataList.add(JsonUtil.parseObject(hit.getSourceAsString(), clazz)));
            return new PageImpl<>(dataList,pageable,response.getHits().getTotalHits().value);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "error to execute searching,because of " + e.getMessage());
        }
    }


    /**
     * 聚合
     *
     * @param index
     * @param searchSourceBuilder
     * @param aggName 聚合名
     * @return Map<Integer, Long>  key:aggName   value: doc_count
     */
    public Map<Integer, Long> aggSearch(String index, SearchSourceBuilder searchSourceBuilder, String aggName){
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(searchSourceBuilder);
        log.info("DSL语句为：{}",searchRequest.source().toString());
        try {
            SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);
            Aggregations aggregations = response.getAggregations();
            Terms terms = aggregations.get(aggName);
            List<? extends Terms.Bucket> buckets = terms.getBuckets();
            Map<Integer, Long> responseMap = new HashMap<>(buckets.size());
            buckets.forEach(bucket-> responseMap.put(bucket.getKeyAsNumber().intValue(), bucket.getDocCount()));
            return responseMap;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "error to execute aggregation searching,because of " + e.getMessage());
        }

    }



    /**
     *  新增或者更新文档
     *
     *  对于更新文档，建议可以直接使用新增文档的API，替代 UpdateRequest
     *  避免因对应id的doc不存在而抛异常：document_missing_exception
     * @param obj
     * @param index
     * @return
     */
    public <T>  Boolean saveDocToEs(Object obj, String index){
        try {
            String str= JsonUtil.toJson(obj);
            IndexRequest indexRequest = new IndexRequest(index).id(getESId(obj))
                    .source(str, XContentType.JSON);
            int times = 0;
            while (times < retryLimit) {
                IndexResponse indexResponse = esClient.index(indexRequest, RequestOptions.DEFAULT);
                if (indexResponse.status().equals(RestStatus.CREATED) || indexResponse.status().equals(RestStatus.OK)) {
                    return true;
                } else {
                    log.info(JsonUtil.toJson(obj));
                    times++;
                }
            }
            return false;
        } catch (Exception e) {
            log.error("Object = {}, index = {}, id = {} , exception = {}", obj, index, getESId(obj) , e.getMessage());
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "error to execute add doc,because of " + e.getMessage());
        }

    }

    /**
     * 判断索引是否存在
     * @param indexName
     * @return
     */
    public boolean isExistsIndex(String indexName){
        GetIndexRequest request = new GetIndexRequest(indexName);
        try {
            return esClient.indices().exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("IOException: {} ",e.getMessage());
            return false;
        }
    }


    /**
     * 创建索引
     * @param indexName
     * Class<T> tClass
     * @throws IOException
     */
    public <T> void  createIndex(String indexName,Class<T> tClass) throws IOException {
        Field[] fields = tClass.getDeclaredFields();
        // 标识开始设置值
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                    .startObject("mappings")
                        .startObject("properties");
                            for(Field field : fields) {
                                field.setAccessible(true);
                                builder.startObject(field.getName());
                                ContextType contextType = field.getAnnotation(ContextType.class);
                                String index = contextType.analyzer();
                                builder.field("type", contextType.value());
                                if (StringUtils.hasText(index)){
                                    builder.field("analyzer", index);
                                }
                                if (field.getType().equals(Date.class)||field.getType().equals(LocalDate.class)||field.getType().equals(LocalDateTime.class)){
                                    builder.field("format", contextType.format());
                                }
                                builder.endObject();
                            }
                        builder.endObject()
                    .endObject();
                    builder.startObject("settings")
                            //设置主分片个数，默认值是 `5`
                        .field("number_of_shards",5)
                            //设置主分片的复制分片个数，默认是 `1`
                        .field("number_of_replicas",1)
                    .endObject();
                builder.endObject();
        //版本不一样注意这个方法不一样，有些版本是source方法
        CreateIndexRequest request = new CreateIndexRequest(indexName).source(builder);
        CreateIndexResponse createIndexResponse = esClient.indices().create(request, RequestOptions.DEFAULT);
        if (!createIndexResponse.isAcknowledged()){
            throw new BaseException(500, "ElasticSearch index create error");
        }
    }


    /**
     *  删除文档
     *
     * @param index
     * @param id
     * @return
     */
    public Boolean deleteDocToEs(Object id, String index) {
        try {
            DeleteRequest request = new DeleteRequest(index, JsonUtil.toJson(id));
            int times = 0;
            while (times < retryLimit) {
                DeleteResponse delete = esClient.delete(request, RequestOptions.DEFAULT);
                if (delete.status().equals(RestStatus.OK)) {
                    return true;
                } else {
                    log.info(JsonUtil.toJson(delete));
                    times++;
                }
            }
            return false;
        } catch (Exception e) {
            log.error("index = {}, id = {} , exception = {}", index, id , e.getMessage());
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "error to execute update doc,because of " + e.getMessage());
        }
    }


    /**
     * 批量插入 或者 更新
     *
     * @param array 数据集合
     * @param index
     * @return
     */
    public <T> Boolean batchAddOrUptToEs(List<T> array, String index) {

        try {
            BulkRequest request = new BulkRequest();
            for (Object obj : array) {
                IndexRequest indexRequest = new IndexRequest(index).id(getESId(obj))
                        .source(JsonUtil.toJson(obj), XContentType.JSON);
                request.add(indexRequest);
            }
            BulkResponse bulk = esClient.bulk(request, RequestOptions.DEFAULT);
            return bulk.status().equals(RestStatus.OK);
        } catch (Exception e) {
            log.error("index = {}, exception = {}", index, e.getMessage());
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "error to execute batch add doc,because of " + e.getMessage());
        }
    }


    /**
     * 批量删除
     * @param deleteIds 待删除的 _id list
     * @param index
     * @return
     */
    public Boolean batchDeleteToEs(List<Integer> deleteIds, String index){
        try {
            BulkRequest request = new BulkRequest();
            for (Integer deleteId : deleteIds) {
                DeleteRequest deleteRequest = new DeleteRequest(index, deleteId.toString());
                request.add(deleteRequest);
            }
            BulkResponse bulk = esClient.bulk(request, RequestOptions.DEFAULT);
            return bulk.status().equals(RestStatus.OK);
        } catch (Exception e) {
            log.error("index = {}, exception = {}", index, e.getMessage());
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "error to execute batch update doc,because of " + e.getMessage());
        }
    }


    /**
     * 将obj的id 作为 doc的_id
     * @param obj
     * @return
     */
    private String getESId(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.valueToTree(obj);
        String id = JsonUtil.toJson(node.get("id"));
        if (StringUtils.isEmpty(id)||"null".equals(id)){
            throw ApiResultUtil.fail4BadParameter(ReturnCode.NOT_ACCEPTABLE,"Id is missing when data save to elastic search server.");
        }
        return id;
    }

    private void takeDataAccessBuilder(DataPermissionDO permissionsDTO, SearchSourceBuilder builder){
        ConditionAnalysis conditionAnalysis = new ConditionAnalysis(permissionsDTO.getObjectPerm());
        if (conditionAnalysis.isUpdateAll()||conditionAnalysis.isReadAll()) {
            return;
        }
        if (!conditionAnalysis.isRead()){
            throw ApiResultUtil.fail4BadParameter(ReturnCode.NOT_ACCEPTABLE,"no such search authentication for this data");
        }
        /*
         *         SearchSourceBuilder builder = SearchSourceBuilder.searchSource()
         *                 .fetchSource(new String[]{"id","originalId","content","title"}, new String[]{});
         *         WildcardQueryBuilder matchPhraseQueryBuilder = QueryBuilders.wildcardQuery("title", "*acc*");
         *         BoolQueryBuilder childBoolQueryBuilder = new BoolQueryBuilder().must(matchPhraseQueryBuilder);
         *         builder.query(childBoolQueryBuilder);
         */
        List<BoolQueryBuilder> listBuilder = permissionsDTO.getShareRules().stream().map(s->{
            BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
            List<QueryBuilder> matchQueryBuilders = s.getConditions().stream()
                    .map(this::takeMatchPhraseQuery).collect(Collectors.toList());
            if (StringUtils.hasText(s.getFilter())){
                //todo

            }else{
                matchQueryBuilders.forEach(boolQueryBuilder::must);
            }
            return boolQueryBuilder;
        }).collect(Collectors.toList());


    }


    private QueryBuilder takeMatchPhraseQuery(DataConditionDO condition){
        switch (condition.getOperator()) {
            //不等于
            case "!=":
                return QueryBuilders.boolQuery().mustNot(QueryBuilders.termQuery(condition.getFieldName(), condition.getFieldValue()));
            //包含
            case "in":
                return QueryBuilders.termQuery(condition.getFieldName(), condition.getFieldValue().split(","));
            //包含
            case "not in":
                return QueryBuilders.boolQuery().mustNot(QueryBuilders.termQuery(condition.getFieldName(), condition.getFieldValue().split(",")));
            //小于
            case "<":
                return QueryBuilders.rangeQuery(condition.getFieldName()).lt(condition.getFieldValue());
            //小于或等于
            case "<=":
                return QueryBuilders.rangeQuery(condition.getFieldName()).lte(condition.getFieldValue());
            //大于
            case ">":
                return QueryBuilders.rangeQuery(condition.getFieldName()).gt(condition.getFieldValue());
            //大于或等于
            case ">=":
                return QueryBuilders.rangeQuery(condition.getFieldName()).gte(condition.getFieldValue());
            //以什么开始
            case "start with":
                return QueryBuilders.matchQuery(condition.getFieldName(), condition.getFieldValue());
            //类似
            case "like":
                return QueryBuilders.fuzzyQuery(condition.getFieldName(), condition.getFieldValue());
            //不类似
            case "not like":
                return QueryBuilders.boolQuery().mustNot(QueryBuilders.fuzzyQuery(condition.getFieldName(), condition.getFieldValue()));
            //为空
            case "null":
                return QueryBuilders.termQuery(condition.getFieldName(), "null");
            //不为空
            case "not null":
                return QueryBuilders.boolQuery().mustNot(QueryBuilders.termQuery(condition.getFieldName(), "null"));
            //等于
            default:
                return QueryBuilders.termQuery(condition.getFieldName(), condition.getFieldValue());
        }
    }

}
