package cn.jarod.bluecat.analyst.service;

import cn.jarod.bluecat.analyst.entity.DocumentTextDO;

import java.util.List;

/**
 * @auther jarod.jin 2019/12/5
 */
public interface IDocumentTextService {

    void save(DocumentTextDO entity, String collectionName);

    List<DocumentTextDO> getAllData(String collectionName);

}
