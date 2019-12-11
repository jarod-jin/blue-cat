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

    public String findGander(List<String> context){
        String[] regex = new String[]{"性别","性 别","男","女"};
        String gander = context.stream().map(line->
            (Arrays.stream(regex).anyMatch(line::contains))?line:""
        ).filter(StringUtils::hasText).findFirst().orElse("");
        return gander.contains("女")?"女":"男";
    }
}
