package com.shop.services;

import com.shop.pojo.User;

import java.util.List;

public interface UserService {
    List<User> doLogin(String username,String Password);

    void registerUser(User user);
}
