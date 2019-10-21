package com.github.powerttt.gw.sucirity;

import io.jsonwebtoken.Claims;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Author tongning
 * @Date 2019/10/20 0020
 * function:<
 * <p> JWT 拦截
 * >
 */
@Slf4j
@Component
public class JWTFilter implements HandlerInterceptor {

    @Autowired
    private JWTUtils jwtUtils;

    /**
     * 请求之前处理
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //只是解析token
        String header = request.getHeader("Authorization");
        if (handler != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                // 解析
                Claims claims = jwtUtils.parseJWT(token);
                if (claims != null) {

                    // 对比权限
                    // String role = (String) claims.get("roles");

                }
            } catch (Exception e) {
                throw new RuntimeException("令牌有误");
            }
        }
        return true;
    }
}
