package com.example.controller;

import com.example.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class PersonController {

    private final JdbcTemplate jdbcTemplate, jdbcTemplate2;


    public PersonController(@Qualifier("jdbcTemplate") JdbcTemplate jdbcTemplate, @Qualifier("jdbcTemplate2") JdbcTemplate jdbcTemplate2) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcTemplate2 = jdbcTemplate2;
    }


    @RequestMapping("hello")
    public void hello() {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        org.springframework.jdbc.IncorrectResultSetColumnCountException: Incorrect column count: expected 1, actual 3
//        jdbcTemplate.queryForList()报错


        final List<Person> people = jdbcTemplate.query("select * from people", new BeanPropertyRowMapper<>(Person.class));
        people.forEach(System.out::println);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        final List<Person> people2 = jdbcTemplate2.query("select * from people", new BeanPropertyRowMapper<>(Person.class));
        people2.forEach(System.out::println);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }
}
