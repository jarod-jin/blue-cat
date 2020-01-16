package cn.jarod.bluecat.core.utils;

import org.springframework.util.StringUtils;

import static cn.jarod.bluecat.core.common.CommonPattern.CN_PHONE_NUMBER;
import static cn.jarod.bluecat.core.common.CommonPattern.EMAIL;

/**
 * @author jarod.jin 2019/9/9
 */
public class CommonUtil {

    public static boolean validTel(String tel){
        return StringUtils.hasText(tel) && tel.matches(CN_PHONE_NUMBER.getPattern());
    }

    public static boolean validEmail(String email){
        return StringUtils.hasText(email) && email.matches(EMAIL.getPattern());
    }

    public static void main(String[] args) {
        System.out.println(validEmail("admin@163"));
    }
}
