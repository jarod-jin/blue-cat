package cn.jarod.bluecat.general.service.impl;

import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.utils.BeanHelperUtil;
import cn.jarod.bluecat.general.entity.ReleaseNoteDO;
import cn.jarod.bluecat.general.model.bo.CrudReleaseNoteBO;
import cn.jarod.bluecat.general.model.dto.QueryReleaseDTO;
import cn.jarod.bluecat.general.repository.ReleaseNoteRepository;
import cn.jarod.bluecat.general.service.IReleaseNoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @auther jarod.jin 2019/11/20
 */
@Slf4j
@Service
public class ReleaseNoteService implements IReleaseNoteService {

    private final ReleaseNoteRepository releaseNoteRepository;

    @Autowired
    public ReleaseNoteService(ReleaseNoteRepository releaseNoteRepository) {
        this.releaseNoteRepository = releaseNoteRepository;
    }

    /**
     *
     * @param queryDTO
     * @return
     */
    @Override
    public Page<ReleaseNoteDO> queryPage(QueryReleaseDTO queryDTO) {
        Sort sort = new Sort(queryDTO.isASC()? Sort.Direction.ASC:Sort.Direction.DESC, queryDTO.getOrderProperty());
        Pageable pageable = PageRequest.of(queryDTO.getPageNum() - 1, queryDTO.getPageCount(), sort);
        return releaseNoteRepository.findAllByTerminalTypeAndSysCode(queryDTO.getTerminalType(), queryDTO.getSysCode(), pageable);
    }

    /**
     *
     * @param releaseNoteBO
     * @return
     */
    @Override
    @Transactional
    public ReleaseNoteDO saveReleaseNote(CrudReleaseNoteBO releaseNoteBO) {
        ReleaseNoteDO releaseNoteDO = releaseNoteBO.isNew()? new ReleaseNoteDO():releaseNoteRepository.findById(releaseNoteBO.getId()).orElseThrow(()->new BaseException(ReturnCode.S400));
        BeanHelperUtil.copyNotNullProperties(releaseNoteBO,releaseNoteDO);
        releaseNoteDO.setCreator(releaseNoteBO.getModifier());
        return releaseNoteRepository.save(releaseNoteDO);
    }
}
