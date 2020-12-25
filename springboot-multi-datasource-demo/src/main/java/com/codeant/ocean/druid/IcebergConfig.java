package com.codeant.ocean.druid;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;


/**
 * 数据源配置
 * MapperScan注解用于绑定扫描的包和指定的数据源,且指定目录下的mapper无需加注解处理
 *
 */
@Component
@MapperScan(basePackages = "com.codeant.ocean.mapper.iceberg", sqlSessionFactoryRef = "icebergSqlSessionFactory")
public class IcebergConfig extends BaseProperty {


    @Bean(name = "icebergDataSource")
    @Primary
    public DataSource createDataSource() {
        return createDataSource(urlIceberg);
    }

    @Bean(name = "icebergTransactionManager")
    @Primary
    public DataSourceTransactionManager icebergTransactionManager() {
        return new DataSourceTransactionManager(createDataSource());
    }

    @Bean(name = "icebergSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("icebergDataSource") DataSource icebergDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(icebergDataSource);
        String mapperLocation = "classpath:mapper/iceberg/*.xml";
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(mapperLocation));
        return sessionFactory.getObject();
    }
}
