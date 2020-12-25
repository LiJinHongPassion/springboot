package com.codeant.ocean.druid;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * datasource base class
 */
@Data
@ConfigurationProperties(prefix = "druid")
@Component
class BaseProperty {

    protected String user;

    protected String password;

    protected String driverClass;

    protected int initialSize;

    protected int maxActive;

    protected int minIdle;

    protected boolean testOnBorrow;

    protected String urlAccount;

    protected String urlOcean;

    protected String urlIceberg;


    DataSource createDataSource(String url) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setInitialSize(initialSize);
        dataSource.setMaxActive(maxActive);
        dataSource.setMinIdle(minIdle);
        dataSource.setTestOnBorrow(testOnBorrow);
        return dataSource;
    }
}
