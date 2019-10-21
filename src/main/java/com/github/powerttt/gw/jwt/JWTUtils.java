package com.github.powerttt.gw.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 *   iss: jwt签发者
 *     sub: jwt所面向的用户
 *     aud: 接收jwt的一方
 *     exp: jwt的过期时间，这个过期时间必须要大于签发时间
 *     nbf: 定义在什么时间之前，该jwt都是不可用的.
 *     iat: jwt的签发时间
 *     jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
 *
 * @Author tongning
 * @Date 2019/10/20 0020
 * function:<
 * <p>
 * >
 */
@Slf4j
@Component
@ConfigurationProperties(prefix = "jwt.config")
public class JWTUtils {

    private String key;
    private Long expiration;
    private Long refresh;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public Long getRefresh() {
        return refresh;
    }

    public void setRefresh(Long refresh) {
        this.refresh = refresh;
    }

    /**
     * 生成JWT
     */
    public String createJWT(String id, String subject, String roles) {
        Date now = new Date();
        JwtBuilder builder = Jwts.builder().setId(id)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, key).claim("roles", roles);
        return builder.compact();

    }

    /**
     * 解析JWT
     */
    public Claims parseJWT(String jwtStr) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(jwtStr).getBody();
    }


    /**
     * 判断刷新JWT
     * 过期时间小于24小时，刷新
     */
    public String refresh(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.substring(7);
        Claims claims = parseJWT(token);
        log.info("令牌未过期");
        return claims.getExpiration().getTime() - System.currentTimeMillis() < refresh ? createJWT(claims.getId(), claims.getSubject(), claims.get("roles").toString()) : token;
    }

    /**
     * 获取当前用户
     */
    public String getId(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.substring(7);
        Claims claims = parseJWT(token);
        return claims.getId();
    }

}
