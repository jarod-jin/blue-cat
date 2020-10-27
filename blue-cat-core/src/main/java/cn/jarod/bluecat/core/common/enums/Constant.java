package cn.jarod.bluecat.core.common.enums;

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

        String API_RESULT_UTIL  = "ApiResultUtil";

        /** 默认日期时间格式 */
        String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

        /** 默认日期格式 */
        String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

        /** 默认时间格式 */
        String DEFAULT_TIME_FORMAT = "HH:mm:ss";

        /**存放Token的Header Key*/
        String AUTHORIZATION = "Authorization";

    }

    public interface Entity{

        String GMT_CREATE = "gmtCreate";

        String BASE_REPOSITORY = "cn.jarod.*";


    }

    public interface Authentication{

        /**无权限*/
        String NONE = "000000";

        /**只读全部*/
        String READ_ALL = "101111";

        /**修改删除全部*/
        String WRITE_ALL = "111111";

        /**修改标签*/
        String WRITE = "2";
    }


    public interface Symbol {

        String NUMBER_SIGN  = "#";

        String COMMA = ",";

        String HYPHEN = "-";

        String SLASH = "/";

        String BRACE = "{}";

        String UNDERLINE = "_";
    }

    public interface SqlSymbol {

        String NULL = "null";

        String ON = " on ";

        String AND = " and ";

        String OR = " or ";

        String SPACE = " ";

        String SQL_LIKE = "%";

        String POINT = ".";

        String SINGLE_QUOTES = "'";

        String OPEN_PARENTHESIS = "( ";

        String CLOSE_PARENTHESIS = " )";

    }
}
