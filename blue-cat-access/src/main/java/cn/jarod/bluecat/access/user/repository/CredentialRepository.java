package cn.jarod.bluecat.access.user.repository;




import java.util.Optional;

/**
 * @author jarod.jin 2019/9/9
 */
public interface CredentialRepository extends MongoRepository<CredentialPO, ObjectId> {

    /**
     * 通过用户名查询认证对象
     * @param username 用户名
     * @return Optional
     */
    Optional<CredentialPO> findByUsername(String username);

    /**
     * 通过电话查询认证对象
     * @param tel 电话
     * @return Optional
     */
    Optional<CredentialPO> findByTel(String tel);

    /**
     * 通过邮箱查询认证对象
     * @param email 邮箱
     * @return Optional
     */
    Optional<CredentialPO> findByEmail(String email);


}
