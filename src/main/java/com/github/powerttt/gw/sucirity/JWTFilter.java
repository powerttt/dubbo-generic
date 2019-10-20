package com.github.powerttt.gw.sucirity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;

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
@Data
@WebFilter(filterName = "JWTFilter", urlPatterns = {"/api/*"})
public class JWTFilter implements Filter {

    @Value(value = "${jwt.user}")
    private String user;

    @Value(value = "${jwt.user}")
    private String secret;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String jwtValue = request.getHeader("Authorization");
        long start = System.currentTimeMillis();
        try {
            Map<String, String> map = new JWTUtils(user, secret).verifyToken(jwtValue);
            for (String s : map.keySet()) {
                request.setAttribute(s, map.get(s));
            }
            log.info("JWT : {}, map : {}", jwtValue, map);
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write("{code:\"401\",\"身份验证失败\"}");
            response.getWriter().close();
        }
    }
}
