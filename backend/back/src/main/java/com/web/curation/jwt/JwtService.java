package com.web.curation.jwt;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.web.curation.dao.UserDao;
import com.web.curation.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtService {
	
	@Autowired
	UserDao userDao;
	
	@Value("${jwt.salt}")
	private String salt;
	
	@Value("${jwt.expmin}")
	private int expireMin;	
	
	/**
	 * 로그인 성공시 사용자 정보로 JWT Token 생성
	 * @param user
	 * @return 
	 */
	
	public String create(final User user) {
		log.trace("time:{}",expireMin);
		final JwtBuilder builder = Jwts.builder();
		
		//Header 설정
		builder.setHeaderParam("typ", "JWT"); 
		
		//Payload 설정
		builder.setSubject("LoginToken").
		setExpiration(new Date(System.currentTimeMillis()+1000*60*expireMin)).
		claim("User",user).
		claim("second","더담을거");
		
		//Secret Key를 이용한 암호화
		builder.signWith(SignatureAlgorithm.HS256, salt.getBytes());
		
		//마지막 직렬화 처리
		
		final String jwt = builder.compact();
		log.debug("토큰 발행 : {}",jwt);
		return jwt;
	}
	
	/**
	 * JWT 토큰에서 정보 추출
	 */
	
	public Map<String,Object> get(final String jwt){
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
		} catch (Exception e) {
			throw new RuntimeException();
		}
		
		log.trace("claims :{}",claims);
		
		return claims.getBody();
	}

	public void checkValid(final String token) {
		log.trace("토크 점검 : {}",token);
		Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(token);
	}
	
	
	public User getUserInfo(String email) {
		return userDao.findUserByEmail(email);
	}
}

