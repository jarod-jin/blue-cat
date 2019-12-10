package cn.jarod.bluecat.analyst.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @auther jarod.jin 2019/12/10
 */
@Service
public class CandidateService {

    public String findCandidateName(List<String> context){
        String[] regex = new String[]{"姓名","姓名：","姓 名","姓名:"};
        String nameLine = context.stream().map(line->
            (Arrays.stream(regex).anyMatch(line::contains))?line:""
        ).filter(StringUtils::hasText).findFirst().orElse("");
        return nameLine;
    }
}
