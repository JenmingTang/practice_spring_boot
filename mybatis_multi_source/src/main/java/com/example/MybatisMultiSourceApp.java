package com.example;

import com.example.dao.PersonDao;
import lombok.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
public class MybatisMultiSourceApp {
    public static void main(String[] args) {
        SpringApplication.run(MybatisMultiSourceApp.class, args);

    }
//    =============================================

    /**
     * spring:
     * datasource:
     * type: com.alibaba.druid.pool.DruidDataSource
     * driver-class-name: com.mysql.cj.jdbc.Driver
     * url: jdbc:mysql:///ssm?useUnicode=true&characterEncoding=UTF-8
     * #    url: jdbc:mysql://localhost:3306/ssm?useUnicode=true&characterEncoding=UTF-8
     * username: root
     * password: root
     *
     * @return
     */

    private DataSource getDataSource(final String databaseName) {
//        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//        已经可以 多数据源 了 但一直报下面错
//        已经可以 多数据源 了 但一直报下面错
//                personDao.getAll().forEach(System.out::println);
        /**
         * 未加 .type(HikariDataSource.class) 直接崩溃 加了也崩溃 和 localhost:3306 加不加也一样
         * Failed to configure a DataSource:
         * 'url' attribute is not specified and no embedded datasource could be configured.
         *
         * Reason: Failed to determine a suitable driver class
         */
//        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql:///" + databaseName + "?useUnicode=true&characterEncoding=UTF-8")
//                可加 可不加
                /**
                 * 看信息觉得用的是 HikariPool ，加时 不加时 .type(HikariDataSource.class)
                 * ++++++++++++++++++++++++++++
                 * personDao = org.apache.ibatis.binding.MapperProxy@2a49fe
                 * 2022-05-28 23:13:35.616  INFO 5504 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
                 * 2022-05-28 23:13:35.956  INFO 5504 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
                 * Person(id=8, name=boot0, remark=boot0)
                 *
                 */
//                .type(HikariDataSource.class)
                .username("root").password("root").build();
    }

    @Bean
    public PersonDao personDao() throws Exception {
        final DataSource spring_boot = getDataSource("spring_boot");
//        ================
        final PersonDao personDao = getPersonDao(spring_boot);
        System.out.println("++++++++++++++++++++++++++++");
        System.out.println("personDao = " + personDao);
        personDao.getAll().forEach(System.out::println);
        System.out.println("++++++++++++++++++++++++++++");
        return personDao;
        //        ================
    }

    @Bean
    public PersonDao personDao2() throws Exception {
        final DataSource ssm = getDataSource("ssm");

        final PersonDao personDao2 = getPersonDao(ssm);
        System.out.println("++++++++++++++++++++++++++++");
        System.out.println("personDao2 = " + personDao2);
        personDao2.getAll().forEach(System.out::println);
        System.out.println("++++++++++++++++++++++++++++");
//        ================
        return personDao2;
        //        ================
    }

    private PersonDao getPersonDao(DataSource spring_boot) throws Exception {
        //        ================
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(spring_boot);
//        ================
        final SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
        assert sqlSessionFactory != null;
        sqlSessionFactory.getConfiguration().addMapper(PersonDao.class);
        final PersonDao personDao = sqlSessionFactory.getConfiguration().getMapper(PersonDao.class, sqlSessionFactory.openSession());
        return personDao;
    }
//    =============================================
}
