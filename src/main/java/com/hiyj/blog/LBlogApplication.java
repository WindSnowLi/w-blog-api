package com.hiyj.blog;

import com.alibaba.druid.pool.DruidDataSource;
import com.hiyj.blog.caches.redis.RedisConfigurationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@Import(RedisConfigurationBuilder.class)
public class LBlogApplication {

    @Autowired
    public void setRedisConfigurationBuilder(RedisConfigurationBuilder redisConfigurationBuilder) {
        // 为了优先加载
    }

    public static void main(String[] args) {
        SpringApplication.run(LBlogApplication.class, args);
    }

    @Bean(name = "DruidDataSource", destroyMethod = "close", initMethod = "init")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource druidDataSource() {
        return new DruidDataSource();
    }
}
