package com.study.loginpractice.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.study.loginpractice.entity.UserEntity;

@Mapper
public interface  UserMapper {
    UserEntity findByUsername(String username);

    void insertUser(UserEntity userEntity);
}
