package per.codeant.controller;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

/**
 * @author LJH
 * @date 2019/7/16-6:16
 * @QQ 1755497577
 */
//官网例子
public class test {


    //选择加密方式创建密钥
    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);


    /**
     * 加密
     * jwt = 头部（至少指定算法） + 身体（JWT编码的所有声明） + 签名（将标题和正文的组合通过标题中指定的算法计算得出）
     * jws:JWT可以加密签名成为jws
     * @param setObject
     * @return 返回jws
     */
    public String encryption(String setObject) {

        String jws = Jwts.builder()
                .setSubject(setObject)//构建jwt
                .signWith(key)//用密钥签名
                .compact();//压缩形成string

        return jws;
    }

    /***
     * 验证
     * 验证不通过抛出JwtException异常
     * @param key
     * @param jws
     */
    public void validation(Key key, String jws){

        try {

            Jwts.parser().setSigningKey(key).parseClaimsJws(jws);

            //OK, we can trust this JWT

        } catch (JwtException e) {

            //don't trust the JWT!
        }
    }

}
