package com.mapper;

import com.pojo.Person;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface PersonMapper {
    List<Person> selectAllPerson();
}




