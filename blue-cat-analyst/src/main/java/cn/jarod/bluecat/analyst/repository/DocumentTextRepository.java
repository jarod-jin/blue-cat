package cn.jarod.bluecat.analyst.repository;

import cn.jarod.bluecat.analyst.entity.DocumentTextDO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2019/12/30
 */
public interface DocumentTextRepository extends MongoRepository<DocumentTextDO, ObjectId> {
    /**
     * 根据标题查询文件
     * @param subject 标题
     * @return Optional
     */
    Optional<DocumentTextDO> findOneBySubject(String subject);
}
