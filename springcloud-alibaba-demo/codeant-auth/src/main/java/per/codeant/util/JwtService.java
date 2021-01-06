package per.codeant.util;

import io.jsonwebtoken.Claims;

import java.util.Map;
import java.util.UUID;

/**
 * 描述:
 * JWT服务类
 *
 * @author LJH
 * @date 2019/7/16-10:50
 * @QQ 1755497577
 */
public class JwtService {

    /**
     * token 过期时间, 单位: 秒. 这个值表示 30 天
     */
    private static final long TOKEN_EXPIRED_TIME = 30 * 24 * 60 * 60;

    /**
     * jwt 加密解密密钥
     */
    private static final String JWT_SECRET = "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY=";


    /**
     * jwt 加密解密密钥
     */
    private static final String JWT_ISSUER = "LJH";

    /**
     * 描述:创建令牌
     * @author LJH-1755497577 2019/7/16 10:52
     * @param map 主题，也差不多是个人的一些信息，为了好的移植，采用了map放个人信息，而没有采用JSON
     * @param audience 发送谁
     * @return java.lang.String
     */
    public static String createPersonToken(Map map, String audience) {
        String personToken = JwtUtils.createJWT(map, audience, UUID.randomUUID().toString(), JWT_ISSUER, TOKEN_EXPIRED_TIME, JWT_SECRET);
        return personToken;
    }


    /**
     * 描述:解密JWT
     *
     * @author LJH-1755497577 2019/7/16 10:54
     * @param personToken JWT字符串,也就是token字符串
     * @return io.jsonwebtoken.Claims
     */
    public static Claims parsePersonJWT(String personToken) {
        Claims claims = JwtUtils.parseJWT(personToken, JWT_SECRET);
        return claims;
    }
}
