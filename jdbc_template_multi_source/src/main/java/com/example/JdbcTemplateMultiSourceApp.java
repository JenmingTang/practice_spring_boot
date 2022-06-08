package com.example;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import javax.sql.DataSource;

//====================================

/**
 * 多构造要指定 容器走那个
 * private final CustomerPreferenceDao customerPreferenceDao;
 * 能和构造一起用
 *
 * @Autowired private MovieCatalog movieCatalog;
 * @Autowired public MovieRecommender(CustomerPreferenceDao customerPreferenceDao) {
 * this.customerPreferenceDao = customerPreferenceDao;
 * }
 */
//====================================

/**
 * 特定类型所有
 *
 * @Autowired private MovieCatalog[] movieCatalogs;
 */
//====================================
//    @Qualifier("bean_name")
//====================================
@SpringBootApplication
public class JdbcTemplateMultiSourceApp implements WebMvcConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(JdbcTemplateMultiSourceApp.class, args);
    }

    //    ================================================
//    implements WebMvcConfigurer 且要挂 @Configuration
//    路径映射
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        src/main/resources/static/tang.html
        registry.addViewController("/tang").setViewName("tang.html");
        WebMvcConfigurer.super.addViewControllers(registry);
    }

    //拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    //    ================================================
    @Bean
    DataSource dataSource() {
        return getDruidDataSource("spring_boot");
    }

    private DruidDataSource getDruidDataSource(String databaseName) {
        final DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl("jdbc:mysql:///" + databaseName + "?useUnicode=true&characterEncoding=UTF-8");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("root");
        return druidDataSource;
    }

    @Bean
    DataSource dataSource2() {
        return getDruidDataSource("ssm");
    }

    @Bean
    JdbcTemplate jdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    JdbcTemplate jdbcTemplate2(@Qualifier("dataSource2") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    //    ================================================

}
