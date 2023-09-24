package com.study.loginpractice.controller;

import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import com.study.loginpractice.dto.SignupRequestDto;
import com.study.loginpractice.service.UserRegistrationService;

@RestController
public class UserController {

    private final UserRegistrationService userRegistrationService;
    private static final String BASE_FRONTEND_URL = "http://127.0.0.1:5500/front";

    public UserController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    // ユーザー登録のエンドポイント
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@ModelAttribute SignupRequestDto signupRequestDto,
            BindingResult bindingResult) {

        // バリデーションエラーのチェック
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().toString();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        try {
            // ユーザー登録処理
            userRegistrationService.registerUser(signupRequestDto);
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(BASE_FRONTEND_URL + "/index.html"))
                    .body("User registered successfully.");
        } catch (Exception ex) {
            // 登録エラーのハンドリング
            return redirectToErrorPage(ex.getMessage());
        }
    }

    // エラーメッセージを持ったエラーページへのリダイレクト
    private ResponseEntity<String> redirectToErrorPage(String errorMessage) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(BASE_FRONTEND_URL + "/signup.html")
                .queryParam("error", errorMessage);

        URI errorPageUri = builder.build().encode().toUri();
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(errorPageUri)
                .body(errorMessage);
    }
}
