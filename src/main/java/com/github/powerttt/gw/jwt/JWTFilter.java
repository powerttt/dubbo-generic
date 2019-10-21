package com.github.powerttt.gw.jwt;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
     *
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
//        if (StringUtils.isEmpty(header)) {
//            response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8_VALUE);
//            response.getWriter().write("{\"code\": \"401\", \"msg\": \"未登入或已过期\"}");
//            return false;
//        }
        if (StringUtils.isNotEmpty(header) && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                // 解析
                Claims claims = jwtUtils.parseJWT(token);
                if (claims != null) {
                    // 对比权限
                    // String role = (String) claims.get("roles");
                    return true;
                }
            } catch (Exception e) {
                response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8_VALUE);
                response.getWriter().write("{\"code\": \"401\", \"msg\": \"未登入或令牌已过期\"}");
            }
        }
        return false;
    }
}
