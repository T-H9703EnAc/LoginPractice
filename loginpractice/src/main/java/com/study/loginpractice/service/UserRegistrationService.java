package com.study.loginpractice.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.study.loginpractice.dto.SignupRequestDto;
import com.study.loginpractice.entity.UserEntity;

@Service
public class UserRegistrationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // 新しいユーザーを登録
    @Transactional
    public void registerUser(SignupRequestDto userDto) throws Exception {
        // 二重登録のチェック
        if (userService.getUserByUsername(userDto.getUsername()) != null) {
            throw new Exception("ユーザ名が既に存在します。"); // カスタム例外を使用することを検討: UserAlreadyExistsException
        }

        // パスワード一致のチェック
        if (!userDto.getPassword().equals(userDto.getPasswordConfirm())) {
            throw new Exception("パスワードと確認用パスワードが一致しません。"); // カスタム例外を使用することを検討: PasswordMismatchException
        }

        // 新しいユーザーエンティティの作成と保存
        UserEntity user = new UserEntity();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(userDto.getPassword()));
        userService.createUser(user);
    }
}
