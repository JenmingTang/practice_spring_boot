package com.example.controller;


import com.example.entities.Person;
import com.example.mapper.PersonMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class PersonController {
    private final PersonMapper personMapper;

    public PersonController(PersonMapper personMapper) {
        this.personMapper = personMapper;
    }
    @RequestMapping("/getAll")
    public void getAll(){
        final List<Person> people = personMapper.getAll();
        System.out.println("==========================");
        System.out.println("getAll");
        people.forEach(System.out::println);
        System.out.println("==========================");
    }
    @RequestMapping("/findById")
    public void findById(){
        final Person person = personMapper.findById("10");
        System.out.println("==========================");
        System.out.println("findById");
        System.out.println("person = " + person);
        System.out.println("==========================");
    }
    @RequestMapping("/deleteById")
    public void deleteById(){
        final int delete = personMapper.deleteById("10");
        System.out.println("==========================");
        System.out.println("deleteById");
//        delete = 1
        System.out.println("delete = " + delete);
        System.out.println("==========================");
    }

}
