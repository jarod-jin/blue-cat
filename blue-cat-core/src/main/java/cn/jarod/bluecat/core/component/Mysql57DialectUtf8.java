package cn.jarod.bluecat.core.component;

import org.hibernate.dialect.MySQL57Dialect;

/**
 * @author jarod.jin 2019/9/4
 */
public class Mysql57DialectUtf8 extends MySQL57Dialect {
    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci";
    }
}
