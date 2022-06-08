package com.example.mapper;

import com.example.entities.Person;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PersonMapper {
    @Select("select * from people")
    List<Person> getAll();

    @Select("SELECT * FROM people WHERE id = #{id}")
    Person findById(@Param("id") String id);

    @Delete("delete from people where id=#{id}")
//    int long Integer Long 都得
    int deleteById(@Param("id") String id);
}
