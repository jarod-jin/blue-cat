package cn.jarod.bluecat.access.group.repository;

import cn.jarod.bluecat.access.group.pojo.entity.GroupPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author jarod.jin 2019/9/9
 */
public interface GroupRepository extends MongoRepository<GroupPO,Long> {

    /**
     * 通过组织代码查询组织结构
     * @param orgCode 组织代码
     * @return Optional
     */
    Optional<GroupPO> findByGroupCode(String orgCode);

    /**
     * 通过组织全编码查询相关组织列表，可以用于查询下属组织机构
     * @param fullCode 组织编码
     * @return List
     */
    List<GroupPO> findAllByFullCodeLike(String fullCode);

    /**
     * 通过所属系统编号和组织机构代码列表查询相关组织列表
     * @param sys 系统代码
     * @param codes  组织编码列表
     * @return List
     */
    List<GroupPO> findAllByBelongToInAndOrgCodeIn(List<String> sys, List<String> codes);

}
