package com.example;

import com.example.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;

@SpringBootApplication
public class SqlOfExclusiveApp {

//    只能挂在 @Configuration 注解修饰的类里
    @Bean
    Person person() {
        return new Person(999, "PersonController", "PersonController");
    }

    public static void main(String[] args) {

        final ConfigurableApplicationContext run = SpringApplication.run(SqlOfExclusiveApp.class, args);
//        Arrays.stream(run.getBeanDefinitionNames()).forEach(System.out::println);

    }
}
