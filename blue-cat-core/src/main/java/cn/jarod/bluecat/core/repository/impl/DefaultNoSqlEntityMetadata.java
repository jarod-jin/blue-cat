package cn.jarod.bluecat.core.repository.impl;

import cn.jarod.bluecat.core.repository.NoSqlEntityMetadata;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2019/12/30
 */
@Component
public class DefaultNoSqlEntityMetadata<T> implements NoSqlEntityMetadata<T> {

    private final Class<T> entityClass;

    /**
     * Creates a new {@link DefaultNoSqlEntityMetadata} for the given entity class.
     * @param entityClass must not be {@literal null}.
     */
    public DefaultNoSqlEntityMetadata(Class<T> entityClass) {
        Assert.notNull(entityClass, "Entity class must not be null!");
        this.entityClass = entityClass;
    }


    @Override
    public Class<T> getEntityClass() {
        return entityClass;
    }
}
