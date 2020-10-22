package cn.jarod.bluecat.core.converter;

import cn.jarod.bluecat.core.common.enums.Constant;
import org.apache.commons.beanutils.converters.AbstractConverter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/5/9
 */
public class LocalDateConverter extends AbstractConverter {

    private String defaultTimePattern = Constant.Common.DEFAULT_DATE_FORMAT;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(defaultTimePattern);

    public LocalDateConverter() {
        super();
    }

    public LocalDateConverter(String timePattern) {
        super();
        if (StringUtils.isNotBlank(timePattern) && !this.defaultTimePattern.equals(timePattern)) {
            this.defaultTimePattern = timePattern;
            dateTimeFormatter = DateTimeFormatter.ofPattern(timePattern);
        }

    }

    public LocalDateConverter(Object defaultValue) {
        super(defaultValue);
    }

    @Override
    protected Class<?> getDefaultType() {
        return String.class;
    }

    @Override
    protected <T> T convertToType(Class<T> type, Object value) throws Throwable {
        // We have to support Object, too, because this class is sometimes
        // used for a standard to Object conversion
        if (LocalDate.class.equals(type)) {
            return type.cast(LocalDate.parse(value.toString(), dateTimeFormatter));
        }
        throw conversionException(type, value);
    }
}
