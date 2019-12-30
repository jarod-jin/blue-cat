package cn.jarod.bluecat.core.repository;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2019/12/30
 */
public interface NoSqlEntityMetadata<T> {
    /**
     * 返回实际对象类型
     *
     * @return class
     */
    Class<T> getEntityClass();
}
