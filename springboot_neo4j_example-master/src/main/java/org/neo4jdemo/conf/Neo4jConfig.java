package org.neo4jdemo.conf;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;


//文档介绍：https://docs.spring.io/spring-data/neo4j/docs/5.2.1.RELEASE/reference/html/#reference.getting_started.spring-configuration
@Configuration
@EnableNeo4jRepositories(basePackages = "org.neo4jdemo.repository")//扫描dao
@EnableTransactionManagement//开启事务，在neo4j的service上可以使用@Transactional支持注解
@EntityScan(basePackages = {"org.neo4jdemo.model"})//扫描entity，在neo4j中叫domain
public class Neo4jConfig {

    @Value("${spring.data.neo4j.uri}")
    private String uri;

    @Value("${spring.data.neo4j.username}")
    private String username;

    @Value("${spring.data.neo4j.password}")
    private String password;

    @Resource
    private SessionFactory sessionFactory;

    @Bean
    public Driver getCypherDriver() {
        return GraphDatabase.driver(uri, AuthTokens.basic(username, password));
    }

    /**
     * 描述:配置支持事务
     *
     * @author LJH-1755497577 2019/11/14 13:32
     * @param
     * @return org.springframework.transaction.PlatformTransactionManager
     */
    @Bean("transactionManager")
    public PlatformTransactionManager neo4jTransactionManager() {
        return new Neo4jTransactionManager(sessionFactory);
    }
}
