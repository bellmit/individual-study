package cn.gyw.backend.components.common.utils;

import cn.gyw.backend.components.common.enums.CommonRespEnum;
import cn.gyw.backend.components.common.exceptions.BaseException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;

/**
 * JWT token util
 */
public final class JwtTokenUtil {
	
	// 服务器唯一秘钥
	private static final String SERVER_KEY = "gBackend";

	/**
	 * 生成Token
	 * @param userId 用户编号
	 * @return
	 */
	public static String getToken(String userId) {
		return JWT.create().withAudience(userId).sign(Algorithm.HMAC256(SERVER_KEY));
	}
	
	public static String getUserId(String token) {
	    verifyToken(token);
	    return JWT.decode(token).getAudience().get(0);
	}
	
	private static void verifyToken(String token) {
	    JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SERVER_KEY)).build();
	    try {
	        jwtVerifier.verify(token);
	    } catch (JWTVerificationException e) {
	        throw new BaseException(CommonRespEnum.TOKEN_ILLEGAL);
        }
	}
	
	private JwtTokenUtil() {}
}
