package cn.jarod.bluecat.core.service;

import org.hibernate.dialect.MySQL57Dialect;

/**
 * @auther jarod.jin 2019/9/4
 */
public class MySQL5DialectUTF8 extends MySQL57Dialect {
    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8_unicode_ci";
    }
}
