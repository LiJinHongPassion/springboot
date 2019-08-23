package cn.codesheep.springbt_security_jwt.comm;

/**
 * @author LJH
 * @date 2019/8/23-10:35
 * @QQ 1755497577
 */
public class Const {

    public static final long EXPIRATION_TIME = 432_000_000;     // 5天(以毫秒ms计)
    public static final String SECRET = "CodeAntSecret";      // JWT密码
    public static final String TOKEN_PREFIX = "Bearer";         // Token前缀
    public static final String HEADER_STRING = "Authorization"; // 存放Token的Header Key
}
