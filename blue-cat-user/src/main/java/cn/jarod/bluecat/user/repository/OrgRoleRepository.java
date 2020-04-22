package cn.jarod.bluecat.user.repository;

import cn.jarod.bluecat.user.entity.OrgRoleDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author jarod.jin 2019/9/9
 */
public interface OrgRoleRepository extends JpaRepository<OrgRoleDO,Long> {

    /**
     * 通过角色代码列表获取组织角色对应
     * @param codeList  角色代码列表
     * @return List
     */
    List<OrgRoleDO> findAllByRoleCodeIn(List<String> codeList);

    /**
     * 通过角色代码和组织代码查询角色组织
     * @param orgCode 组织代码
     * @param roleCode 角色代码
     * @return Optional
     */
    Optional<OrgRoleDO> findByOrgCodeAndRoleCode(String orgCode, String roleCode);
}
