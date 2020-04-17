package com.bzm.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.bzm.entity.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {


    /**
     * 过期时间为一天
     * TODO 正式上线更换为15分钟
     */
    private static final long EXPIRE_TIME = 24*60*60*1000;

    /**
     * token私钥
     */
    private static final String TOKEN_SECRET = "joijsdfjlsjfljfljl5135313135";
    private static final String ISSUER = "mouse";

    /**
     * 生成签名,15分钟后过期
     * @param user
     * @return
     */
    public static String createToken(User user){
        //过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        //私钥及加密算法
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        //设置头信息
        HashMap<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        //附带username和userID生成签名
        return JWT.create()
                .withHeader(header)
                .withIssuer(ISSUER)
                .withClaim("loginName",user.getUsername())
                .withClaim("userId",user.getUserId())
                .withExpiresAt(date)
                .sign(algorithm);
    }

    /**
     * 解析Token
     * @author px
     * @createTime: 2020/4/17 0017
     * @param:
     * @return :
     * */
    public static User verityToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
            DecodedJWT jwt = verifier.verify(token);
            long time = jwt.getExpiresAt().getTime();
            if(System.currentTimeMillis()< time){
                // token 过期了
                return null;
            }
            Map<String, Claim> userMap = jwt.getClaims();
            String loginName = userMap.get("loginName").asString();
            Integer userId = userMap.get("userId").asInt();
            return new User(userId,loginName,null);
        } catch (IllegalArgumentException e) {
            return null;
        } catch (JWTVerificationException e) {
            return null;
        }

    }
}