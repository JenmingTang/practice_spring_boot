package com.example.dao;

import com.example.entities.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//@Mapper 多数据源手动配时，不用 spring boot 的自动装配配置，可以不加此注解
//@Mapper 多数据源手动配时，不用 spring boot 的自动装配配置，可以不加此注解
//把 .yml 的 url 关了，和这也关了，还是会报 url 错，所以是因为 导入了 有自动配置功能的 mybatis datasource 依赖
//@Mapper
public interface PersonDao {
    @Select("select * from people")
    List<Person> getAll();
}
