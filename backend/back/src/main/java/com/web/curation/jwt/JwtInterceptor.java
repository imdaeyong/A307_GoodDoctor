package com.web.curation.jwt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

	@Autowired
	private JwtService jwtService;

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
			Object handler) throws Exception {
		System.out.println(req.getMethod() + ":" + req.getServletPath());
		
		if(req.getMethod().equals("OPTIONS")) {
			return true;
		}else {
			String token = req.getHeader("jwt-auth-token");
			if(token!=null & token.length()>0) {
				jwtService.checkValid(token);
				return true;
			}else {
				throw new RuntimeException("인증 토큰이 없습니다.");
			}
		}
	}
}
