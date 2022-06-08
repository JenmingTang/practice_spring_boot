package com.example;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.sql.DataSource;
//在 spring-boot-starter-jdbc（JdbcTemplate） or spring-boot-starter-data-jpa（JdbcTemplate）
import com.zaxxer.hikari.HikariDataSource;
//在 org.springframework.boot spring-boot 2.7.0
import org.springframework.boot.jdbc.DataSourceBuilder;
class demo {
    {
//        看网上可以不指定 HikariDataSource.class 类型
        final HikariDataSource hikariDataSource = DataSourceBuilder.create().type(HikariDataSource.class).driverClassName("").url("").username("").password("").build();
        final DataSource dataSource = DataSourceBuilder.create().driverClassName("").url("").username("").password("").build();

    }
}

@SpringBootApplication
public class MybatisWithSpringBootApp {
    public static void main(String[] args) {
        SpringApplication.run(MybatisWithSpringBootApp.class, args);
    }
}
