package cn.jarod.bluecat.core.config;

import cn.jarod.bluecat.core.common.enums.Constant;
import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * @author jarod.jin 2019/5/17
 */
@Slf4j
@Configuration
@ConditionalOnClass({DataSource.class, EmbeddedDatabaseType.class})
@EnableConfigurationProperties({DataSourceProperties.class})
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {Constant.DataSource.BASE_REPOSITORY})
public class DataSourceConfig {

    private static final String MASTER = "master";

    private static final String SLAVE = "slave";

    private final Environment props;

    private final JpaProperties jpaProperties;

    private final HibernateProperties hibernateProperties;

    public DataSourceConfig(Environment props, JpaProperties jpaProperties, HibernateProperties hibernateProperties) {
        this.props = props;
        this.jpaProperties = jpaProperties;
        this.hibernateProperties = hibernateProperties;
    }


    /**
     * basic setting
     */
    private DruidDataSource abstractDataSource() {
        DruidDataSource abstractDataSource = new DruidDataSource();
        abstractDataSource.setDriverClassName(props.getProperty("datasource.driver-class-name"));
        abstractDataSource.setValidationQuery(props.getProperty("datasource.validation-query"));
        abstractDataSource.setMaxActive(Integer.parseInt(Objects.requireNonNull(props.getProperty("datasource.max-active"))));
        abstractDataSource.setMinIdle(Integer.parseInt(Objects.requireNonNull(props.getProperty("datasource.min-idle"))));
        abstractDataSource.setInitialSize(Integer.parseInt(Objects.requireNonNull(props.getProperty("datasource.initial-size"))));
        abstractDataSource.setTestOnBorrow(true);
        abstractDataSource.setTestWhileIdle(true);
        abstractDataSource.setMinEvictableIdleTimeMillis(30000);
        abstractDataSource.setPoolPreparedStatements(true);
        abstractDataSource.setMaxOpenPreparedStatements(100);
        return abstractDataSource;
    }

    /**
     * maste setting
     */
    @Bean(destroyMethod = "close", name = MASTER)
    @Primary
    public DruidDataSource masterDataSource() {
        DruidDataSource masterDataSource = abstractDataSource();
        masterDataSource.setUrl(props.getProperty("datasource.master.url"));
        masterDataSource.setUsername(props.getProperty("datasource.master.username"));
        masterDataSource.setPassword(props.getProperty("datasource.master.password"));
        return masterDataSource;
    }

    /**
     * slave setting
     */
    @Bean(destroyMethod = "close", name = SLAVE)
    public DruidDataSource slaveDataSource() {
        DruidDataSource slaveDataSource = abstractDataSource();
        slaveDataSource.setUrl(props.getProperty("datasource.slave.url"));
        slaveDataSource.setUsername(props.getProperty("datasource.slave.username"));
        slaveDataSource.setPassword(props.getProperty("datasource.slave.password"));
        return slaveDataSource;
    }

    @Bean(name="dynamicDataSource")
    public DataSource dynamicDataSource() {
        Map<Object, Object> targetDataSources = ImmutableMap.<Object,Object>builder()
                .put(MASTER, masterDataSource())
                .put(SLAVE, slaveDataSource())
                .build();
        AbstractRoutingDataSource dynamicDataSource = new AbstractRoutingDataSource() {
            @Override
            protected Object determineCurrentLookupKey() {
                return TransactionSynchronizationManager.isCurrentTransactionReadOnly() ? SLAVE : MASTER;
            }
        };
        dynamicDataSource.setDefaultTargetDataSource(targetDataSources.get(MASTER));
        dynamicDataSource.setTargetDataSources(targetDataSources);
        return dynamicDataSource;
    }

    @Bean
    public DataSource dataSource(){
        return new LazyConnectionDataSourceProxy(dynamicDataSource());
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder)  {
        return  builder
                .dataSource(dataSource())
                .packages(props.getProperty("datasource.entity-path").split(Constant.Symbol.COMMA))
                .properties(getVendorProperties())
                .build();
    }

    private Map<String, Object> getVendorProperties() {
        return  hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
    }


    @Bean(name = "transactionManager")
    public JpaTransactionManager transactionManager(EntityManagerFactoryBuilder builder) throws IOException {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory(builder).getObject()));
    }


}
