package cn.jarod.bluecat.core.model.test;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/5/9
 */
@Data
public class DateBean4Test {

    private Date date;

    private LocalDate localDate;

    private LocalDateTime localDateTime;

    private BigDecimal decimal;
}
