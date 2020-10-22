package cn.jarod.bluecat.core.converter;

import cn.jarod.bluecat.core.common.enums.Constant;
import org.apache.commons.beanutils.converters.AbstractConverter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/5/9
 */
public class LocalDateTimeConverter extends AbstractConverter {

    private String defaultTimePattern = Constant.Common.DEFAULT_DATE_TIME_FORMAT;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(defaultTimePattern);

    public LocalDateTimeConverter() {
        super();
    }

    public LocalDateTimeConverter(String timePattern) {
        super();
        if (StringUtils.isNotBlank(timePattern) && !this.defaultTimePattern.equals(timePattern)) {
            this.defaultTimePattern = timePattern;
            dateTimeFormatter = DateTimeFormatter.ofPattern(timePattern);
        }

    }

    public LocalDateTimeConverter(Object defaultValue) {
        super(defaultValue);
    }

    @Override
    protected Class<?> getDefaultType() {
        return LocalDateTime.class;
    }

    @Override
    protected <T> T convertToType(Class<T> type, Object value) throws Throwable {
        // We have to support Object, too, because this class is sometimes
        // used for a standard to Object conversion
        if (LocalDateTime.class.equals(type)) {
            return type.cast(LocalDateTime.parse(value.toString(), dateTimeFormatter));
        }
        throw conversionException(type, value);
    }
}
