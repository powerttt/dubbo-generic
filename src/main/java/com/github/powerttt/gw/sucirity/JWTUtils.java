package com.github.powerttt.gw.sucirity;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author tongning
 * @Date 2019/10/20 0020
 * function:<
 * <p>
 * >
 */
public class JWTUtils {

    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String secret;
    private String isUser;

    public JWTUtils(String secret, String isUser) {
        this.secret = secret;
        this.isUser = isUser;
    }

    /**
     * 验证token
     */
    public Map<String, String> verifyToken(String token) throws Exception {
        Algorithm algorithm;
        Map<String, Claim> map = new HashMap<>();
        try {
            algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(isUser).build();
            DecodedJWT jwt = verifier.verify(token);
            map = jwt.getClaims();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (JWTVerificationException e) {
            throw new Exception("签名验证失败");
        }
        Map<String, String> resultMap = new HashMap<>(map.size());
        map.forEach((k, v) -> resultMap.put(k, v.asString()));
        return resultMap;
    }

}
