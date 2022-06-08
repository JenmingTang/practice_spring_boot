package com.example.controller;

import com.example.dao.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PersonController {
    private final PersonDao personDao, personDao2;

    public PersonController(@Qualifier("personDao") PersonDao personDao, @Qualifier("personDao2") PersonDao personDao2) {
        this.personDao = personDao;
        this.personDao2 = personDao2;
    }

    @RequestMapping("hello")
    public void hello() {
        System.out.println("====================");
        personDao.getAll().forEach(System.out::println);
        System.out.println("====================");
        System.out.println("====================");
        personDao2.getAll().forEach(System.out::println);
        System.out.println("====================");
    }
}
