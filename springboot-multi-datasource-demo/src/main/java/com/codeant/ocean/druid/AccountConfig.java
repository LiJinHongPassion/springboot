package com.codeant.ocean.druid;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

/**
 * account datasource
 * MapperScan（如果要使用表名映射实体使用tk）注解用于绑定扫描的包和指定的数据源,且指定目录下的mapper无需加注解处理
 *
 */
@Component
@MapperScan(basePackages = "com.codeant.ocean.mapper.account", sqlSessionFactoryRef = "accountSqlSessionFactory")
public class AccountConfig extends BaseProperty {

    @Bean(name = "accountDataSource")
    public DataSource createDataSource() {
        return createDataSource(urlAccount);
    }

    @Bean(name = "accountTransactionManager")
    public DataSourceTransactionManager accountTransactionManager() {
        return new DataSourceTransactionManager(createDataSource());
    }

    @Bean(name = "accountSqlSessionFactory")
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("accountDataSource") DataSource accountDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(accountDataSource);
        String mapperLocation = "classpath:mapper/account/*.xml";
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(mapperLocation));
        return sessionFactory.getObject();
    }
}
