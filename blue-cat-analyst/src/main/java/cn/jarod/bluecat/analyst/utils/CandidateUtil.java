package cn.jarod.bluecat.analyst.utils;

import org.assertj.core.util.Lists;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @auther jarod.jin 2019/12/10
 */
public class CandidateUtil {

    public static String findGander(List<String> context){
        String[] regex = new String[]{"性别","性 别","男","女"};
        String gander = context.stream().map(line->
            (Arrays.stream(regex).anyMatch(line::contains))?line:""
        ).filter(StringUtils::hasText).findFirst().orElse("");
        return gander.contains("女")?"女":"男";
    }

    public static int findAge(List<String> context){
        String[] regex = new String[]{"岁","年龄"};
        String ageLine = context.stream().map(line->
                (Arrays.stream(regex).anyMatch(line::contains))?line:""
        ).filter(StringUtils::hasText).findFirst().orElse("0岁");
        Pattern p= Pattern.compile("(\\d+)");
        Matcher m = p.matcher(ageLine);
        List<Double> digitList = Lists.newArrayList();
        while (m.find()) {
            String find= m.group(1);
            digitList.add(Double.valueOf(find));
        }
        return digitList.stream().findFirst().orElse(0.0).intValue();
    }
}
