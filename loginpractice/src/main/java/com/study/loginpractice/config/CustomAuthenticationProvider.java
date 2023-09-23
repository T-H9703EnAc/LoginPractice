package com.study.loginpractice.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// @Componentをつけることで、このクラスがSpringのコンテナにBeanとして登録される
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String inputPassword = (String) authentication.getCredentials();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (passwordEncoder.matches(inputPassword, userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(username, inputPassword, userDetails.getAuthorities());
        } else {
            throw new BadCredentialsException("Authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // authentication(認証方式)がUsernamePasswordAuthenticationToken.class(ユーザー名とパスワード認証)か判定
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    
}
