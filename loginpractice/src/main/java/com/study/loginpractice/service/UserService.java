package com.study.loginpractice.service;

import com.study.loginpractice.entity.UserEntity;
import com.study.loginpractice.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public UserEntity getUserByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}
