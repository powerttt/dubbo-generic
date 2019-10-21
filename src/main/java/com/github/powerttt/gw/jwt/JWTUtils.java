package com.github.powerttt.gw.sucirity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 *   iss: jwt签发者
 *     sub: jwt所面向的用户
 *     aud: 接收jwt的一方
 *     exp: jwt的过期时间，这个过期时间必须要大于签发时间
 *     nbf: 定义在什么时间之前，该jwt都是不可用的.
 *     iat: jwt的签发时间
 *     jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
 * @Author tongning
 * @Date 2019/10/20 0020
 * function:<
 * <p>
 * >
 */
@Component
@ConfigurationProperties(prefix = "jwt.config")
public class JWTUtils {

    private String key;
    private Long ttl;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    public static void main(String[] args) {
        JWTUtils jwtUtils = new JWTUtils();
        System.out.println( jwtUtils.createJWT("admin","admin","ADMIN"));
    }
    /**
     * 生成JWT
     */
    public String createJWT(String id, String subject, String roles) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(id)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, key).claim("roles", roles);
        if (ttl > 0) {
            builder.setExpiration(new Date(nowMillis + ttl));
        }
        return builder.compact();

    }

    /**
     * 解析JWT
     */
    public Claims parseJWT(String jwtStr) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(jwtStr).getBody();
    }

}
