package com.example.springbootredisdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

//@Configuration
class Lettuce_RedisConfiguration {

//  @Bean
  public LettuceConnectionFactory redisConnectionFactory() {

//    return new LettuceConnectionFactory(new RedisStandaloneConfiguration("server", 6379));
    return new LettuceConnectionFactory(new RedisStandaloneConfiguration());
  }
}