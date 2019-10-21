package com.github.powerttt.gw.controller;

import com.github.powerttt.gw.jwt.JWTUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 认证controller
 */
@Slf4j
@RestController()
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private JWTUtils jwtUtils;

    @PostMapping("login")
    public String login(String username, String password) {
        log.info("{}  &  {}", username, password);
        String token = jwtUtils.createJWT("1", "{username:u}", "USERS");
        log.info("token : {}", token);
        return token;
    }

    @GetMapping("refresh")
    public String refresh(HttpServletRequest request) {
        return jwtUtils.refresh(request);
    }


}
