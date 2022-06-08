package com.example.dao.impl;

import com.example.dao.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
//@NoArgsConstructor
public class PersonDaoImpl implements PersonDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addBook() {
        jdbcTemplate.update("insert into people (name ,remark) values (?,?)","tangdada","tangdada");
//        jdbcTemplate.query()
    }
}
