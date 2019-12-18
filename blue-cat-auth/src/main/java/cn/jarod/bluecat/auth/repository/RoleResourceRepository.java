package cn.jarod.bluecat.auth.repository;

import cn.jarod.bluecat.auth.entity.RoleResourceDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author jarod.jin 2019/9/9
 */
public interface RoleResourceRepository extends JpaRepository<RoleResourceDO,Long> {

    /**
     * 根据角色编码和资源编码查询资源
     * @param resourceCode 资源编码
     * @param roleCode 角色编码
     * @return Optional
     */
    Optional<RoleResourceDO> findByResourceCodeAndRoleCode(String resourceCode,String roleCode);

    /**
     * 根据角色编码列表查询所有对应资源
     * @param roleCodes 角色编码列表
     * @return List
     */
    List<RoleResourceDO> findAllByRoleCodeIn(List<String> roleCodes);
}


