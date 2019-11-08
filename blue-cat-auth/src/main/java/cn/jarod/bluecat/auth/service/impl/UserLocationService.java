package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.entity.UserLocationDO;
import cn.jarod.bluecat.auth.repository.UserLocationRepository;
import cn.jarod.bluecat.auth.service.IUserLocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserLocationRepository userLocationRepository;


    @Override
    @Transactional(readOnly = true)
    public List<Long> findOrgRoleIdsByUsername(String name) {
        return userLocationRepository.findAllByUsername(name).stream().map(UserLocationDO::getOrgRoleId).distinct().collect(Collectors.toList());
    }

}
