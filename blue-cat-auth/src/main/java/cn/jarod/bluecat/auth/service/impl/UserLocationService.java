package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.entity.UserLocationDO;
import cn.jarod.bluecat.auth.model.bo.LinkUserLocationBO;
import cn.jarod.bluecat.auth.repository.UserLocationRepository;
import cn.jarod.bluecat.auth.service.IUserLocationService;
import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @auther jarod.jin 2019/10/10
 */
@Slf4j
@Service
public class UserLocationService implements IUserLocationService {

    private final UserLocationRepository userLocationRepository;

    @Autowired
    public UserLocationService(UserLocationRepository userLocationRepository) {
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
    public UserLocationDO saveUserLocation(LinkUserLocationBO userLocationBO) {
        UserLocationDO userLocationDO = new UserLocationDO();
        userLocationDO.setUsername(userLocationBO.getUsername());
        userLocationDO.setOrgRoleId(userLocationBO.getOrgRoleId());
        if (userLocationRepository.exists(Example.of(userLocationDO)))
            throw new BaseException(ReturnCode.S401);
        userLocationDO.setCreator(userLocationBO.getOperator());
        userLocationDO.setModifier(userLocationBO.getOperator());
        return userLocationRepository.save(userLocationDO);
    }

    /**
     * 删除一个人员
     * @param userLocationBO
     * @return
     */
    @Override
    @Transactional
    public void delUserLocation(LinkUserLocationBO userLocationBO) {
        userLocationRepository.delete(userLocationRepository.findById(userLocationBO.getId()).orElseThrow(()->new BaseException(ReturnCode.D400)));
    }


}
