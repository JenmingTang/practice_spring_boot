package com.pojo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cat implements Serializable {
    private Integer id;
    private Integer person_id;
    private String name;
//    一对一
    private Person person;
}