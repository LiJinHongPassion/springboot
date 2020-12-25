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
 * ocean datasource
 * MapperScan注解用于绑定扫描的包和指定的数据源,且指定目录下的mapper无需加注解处理
 *
 */
@Component
@MapperScan(basePackages = "com.codeant.ocean.mapper.ocean", sqlSessionFactoryRef = "oceanSqlSessionFactory")
public class OceanConfig extends BaseProperty {

    @Bean(name = "oceanDataSource")
    public DataSource createDataSource() {
        return createDataSource(urlOcean);
    }

    @Bean(name = "oceanTransactionManager")
    public DataSourceTransactionManager oceanTransactionManager() {
        return new DataSourceTransactionManager(createDataSource());
    }

    @Bean(name = "oceanSqlSessionFactory")
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("oceanDataSource") DataSource oceanDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(oceanDataSource);
        String mapperLocation = "classpath:mapper/ocean/*.xml";
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(mapperLocation));
        return sessionFactory.getObject();
    }
}
