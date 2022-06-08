package com.example.controller;

import com.example.dao.impl.PersonDaoImpl;
import com.example.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Controller
public class PersonController {



    @Autowired
    private Person person;
    @Autowired
    private PersonDaoImpl personDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/changePerson")
    private String change(Person person) {
//        System.out.println("\"change\" = " + "change");
//        System.out.println("\"change\" = " + "change");
//        System.out.println("\"change\" = " + "change");
//        mysql> update `runoob_tbl` set `submission_date`='2016-05-06' where `runoob_id`=3;
        final int i = jdbcTemplate.update("update people set name=?,remark=? where id=?", person.getName(), person.getRemark(), person.getId());
        return "redirect:/personShow";
    }

    @RequestMapping("/changePerson/{id}")
    private String change(@PathVariable String id, Model model) {
        final Person person = jdbcTemplate.queryForObject("select * from people where id=?", new BeanPropertyRowMapper<>(Person.class), id);
        model.addAttribute("person", person);
        return "changePersonPage";
    }

    @RequestMapping("/delete/{id}")
    private String delete(@PathVariable String id) {
//        DELETE FROM <表名> [WHERE 子句] [ORDER BY 子句] [LIMIT 子句]
        jdbcTemplate.update("delete from people where id=?", id);
//        personShow();
        return "redirect:/personShow";
    }

    @RequestMapping("/personShow")
    private ModelAndView personShow() {


        System.out.println("person.toString() = " + person.toString());
        System.out.println("person.toString() = " + person.toString());
        System.out.println("person.toString() = " + person.toString());
//        jdbcTemplate.query()
//        jdbcTemplate.update()
//        jdbcTemplate.queryForObject()
//        new BeanPropertyRowMapper<>(Person.class)

        final List<Person> query = jdbcTemplate.query("select * from people", new BeanPropertyRowMapper<>(Person.class));
        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("people", query);
        return modelAndView;
    }

    @RequestMapping("/addPerson")
    private String addPerson(Person person) {
//        value values 添加多条
        final int i = jdbcTemplate.update("insert into people (name ,remark) value (?,?)", person.getName(), person.getRemark());
//        return "forward:/personShow"; == /personShow
        return "redirect:/personShow";
    }
}
