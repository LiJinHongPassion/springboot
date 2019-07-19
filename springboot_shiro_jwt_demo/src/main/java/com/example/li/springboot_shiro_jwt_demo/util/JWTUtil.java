package com.example.li.springboot_shiro_jwt_demo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class JWTUtil {

    // 过期时间5分钟
    private static final long EXPIRE_TIME = 5*60*1000;

    /**
     * 校验token是否正确
     * @param token token
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String secret) {
        try {
            //指定加密算法
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,5min后过期
     * @param username 用户名
     * @param secret 密钥，这里用的是用户密码，可以自己设置一个密钥
     * @return 加密的token
     */
    public static String sign(String username, String secret) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带username信息
            return JWT.create()
                    .withClaim("username", username)
                    .withExpiresAt(date)
                    .sign(algorithm);
            /*
            jwt = header + payload + signature
            JWT.create().withHeader(map) // header

                    .withClaim("iss", "Service") // payload
                    .withClaim("aud", "APP").// payload
                    withClaim("user_id", null == user_id ? null : user_id.toString())// payload
                    .withIssuedAt(iatDate) // sign time // payload
                    .withExpiresAt(expiresDate) // expire time  // payload

                    .sign(Algorithm.HMAC256(SECRET)); // signature
             */
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
