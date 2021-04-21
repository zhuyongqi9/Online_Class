package pri.kirin.onlineclass.Utils;

import io.jsonwebtoken.*;
import pri.kirin.onlineclass.Model.entity.User;

import java.util.Date;

public class JWTUtils {

    private static final long EXPIRE = 1000 * 60 * 60 * 24 * 7;

    private static final String SECRET = "kirin.onlineclass";

    private static String TOKEN_PREFIX = "onlineclass";

    private static String SUBJECT = "kirin";

    /**
     * 令牌生成
     *
     * @param user
     * @return
     */
    public static String geneJsonWebToken(User user) {
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("head_img", user.getHeadImg())
                .claim("id", user.getId())
                .claim("name", user.getName())
                .setIssuedAt(new Date())
                //注意时间要加上当前时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();

        token += TOKEN_PREFIX;
        return token;
    }

    /**
     * 检验令牌
     *
     * @param token
     * @return
     */
    public static Claims checkJWT(String token) {
        try {
            final Claims claims = Jwts.parser().setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
            return claims;
        } catch (Exception e) {
            return null;
        }
    }
}
