package com.shop.services.impl;

import com.shop.mapper.UserMapper;
import com.shop.pojo.User;
import com.shop.pojo.UserExample;
import com.shop.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<User> doLogin(String username, String password) {
        //创建查询对象
        final UserExample userExample = new UserExample();
        final UserExample.Criteria criteria = userExample.createCriteria();
//        静态嵌套类 private 修饰，这里作为 UserExample 装数据的一部分
        criteria.andUsernameEqualTo(username);
        criteria.andUserpasswordEqualTo(password);
        return userMapper.selectByExample(userExample);
    }

    @Override
    public void registerUser(User user) {
        user.setUserisadmin(0);
        userMapper.insert(user);
    }

}
