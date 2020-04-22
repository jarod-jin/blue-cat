package cn.jarod.bluecat.core.common;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/1/15
 */
public class Constant {

    public interface Redis {

        String OK = "OK";

        String TOKEN_PREFIX = "token:";

        String MSG_CONSUMER_PREFIX = "consumer:";

        String SIGN_UP_PREFIX = "signUpText:";

        String USER_INFO_PREFIX = "userInfo:";

        String ACCESS_LIMIT_PREFIX = "accessLimit:";
    }

    public interface Common {

        String SYS_ROOT = "sys";

        String UNKNOWN = "unknown";

        String ERROR = "error";

        String API_RESULT_UTIL  = "ApiResultUtil";

        /** 默认日期时间格式 */
        String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

        /** 默认日期格式 */
        String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

        /** 默认时间格式 */
        String DEFAULT_TIME_FORMAT = "HH:mm:ss";

        /**存放Token的Header Key*/
        String ACCESS_TOKEN = "authorization";
    }

    public interface Entity{

        String GMT_CREATE = "gmtCreate";

    }

    public interface Auth {

        String PERMIT = "permitAll()";

        String AUTHENTICATED = "isAuthenticated()";

        String DENY = "denyAll()";

    }


    public interface Symbol {

        String COMMA = ",";

        String HYPHEN = "-";

        String SLASH = "/";

        String SQL_LIKE = "%";

        String BRACE = "{}";

        String UNDERLINE = "_";
    }

}
