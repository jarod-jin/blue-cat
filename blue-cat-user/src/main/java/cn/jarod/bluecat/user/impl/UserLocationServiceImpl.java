package cn.jarod.bluecat.user.impl;

import cn.jarod.bluecat.user.entity.UserLocationDO;
import cn.jarod.bluecat.auth.model.bo.LinkUserLocationBO;
import cn.jarod.bluecat.user.repository.UserLocationRepository;
import cn.jarod.bluecat.auth.service.UserLocationService;
import cn.jarod.bluecat.core.utils.ApiResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jarod.jin 2019/10/10
 */
@Slf4j
@Service
public class UserLocationServiceImpl implements UserLocationService {

    private final UserLocationRepository userLocationRepository;

    @Autowired
    public UserLocationServiceImpl(UserLocationRepository userLocationRepository) {
        this.userLocationRepository = userLocationRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Long> findOrgRoleIdsByUsername(String name) {
        return userLocationRepository.findAllByUsername(name).stream().map(UserLocationDO::getOrgRoleId).distinct().collect(Collectors.toList());
    }

    /**
     * 保存人员的职位信息
     * @param userLocationBO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserLocationDO saveUserLocation(LinkUserLocationBO userLocationBO) {
        UserLocationDO userLocationDO = new UserLocationDO();
        userLocationDO.setUsername(userLocationBO.getUsername());
        userLocationDO.setOrgRoleId(userLocationBO.getOrgRoleId());
        if (userLocationRepository.exists(Example.of(userLocationDO))){
            throw ApiResultUtil.fail4Existed();
        }
        userLocationDO.setCreator(userLocationBO.getModifier());
        userLocationDO.setModifier(userLocationBO.getModifier());
        return userLocationRepository.save(userLocationDO);
    }

    /**
     * 删除一个人员
     * @param userLocationBO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delUserLocation(LinkUserLocationBO userLocationBO) {
        userLocationRepository.delete(userLocationRepository.findById(userLocationBO.getId()).orElseThrow(ApiResultUtil::fail4NotFound));
    }


}
