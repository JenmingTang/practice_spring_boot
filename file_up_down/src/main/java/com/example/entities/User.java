package com.example.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private String name;
    private String sex;

    public static void main(String[] args) {
//        new User();
    }
}
