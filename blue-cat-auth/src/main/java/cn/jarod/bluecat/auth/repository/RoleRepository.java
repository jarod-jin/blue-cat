package cn.jarod.bluecat.auth.repository;

import cn.jarod.bluecat.auth.entity.RoleDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author jarod.jin 2019/9/9
 */
public interface RoleRepository extends JpaRepository<RoleDO,Long> {

    /**
     * 通过角色编码查询角色
     * @param roleCode 角色编码
     * @return Optional
     */
    Optional<RoleDO> findByRoleCode(String roleCode);

    /**
     * 通过角色编码和系统编码查询相关角色数据
     * @param sys 系统编码
     * @param codes 角色编码列表
     * @return List
     */
    List<RoleDO> findAllBySysCodeInAndRoleCodeIn(List<String> sys, List<String> codes);

}

