package com.web.curation.controller.account;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.validation.Valid;

import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.user.ChangepwdRequest;
import com.web.curation.model.user.SignupRequest;
import com.web.curation.model.user.User;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "*" }) //이쪽에 있는 내용만 받아온다는것.
@RestController
public class AccountController {

    @Autowired
    UserDao userDao;
    @Autowired
	public JavaMailSender javaMailSender;
    
    @GetMapping("/account/login")
    @ApiOperation(value = "로그인")
    public Object login(@RequestParam(required = true) final String email,
            @RequestParam(required = true) final String password) {
        Optional<User> userOpt = userDao.findUserByEmailAndPassword(email, password);
        
        System.out.println(userOpt.toString());
        ResponseEntity response = null;
        if (userOpt.isPresent()) {
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @PostMapping("/account/signup")
    @ApiOperation(value = "가입하기")
    public Object signup(@Valid @RequestBody SignupRequest request) {
    	if(userDao.getUserByEmail(request.getEmail())!=null){
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "email_fail";
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    	else if(userDao.getUserByNickname(request.getNickname())!=null) {
    		final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "nickname_fail";
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    	}
    	User user = new User();
    	user.setNickname(request.getNickname());
    	user.setEmail(request.getEmail());
    	user.setPassword(request.getPassword());
        User temp = userDao.save(user);
        
        

        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    
    @PutMapping("/account/changepwd")
    @ApiOperation(value = "비밀번호변경")
    public Object changepwd(@Valid @RequestBody ChangepwdRequest request) {
    	BasicResponse result = null;
    	Optional<User> userOpt = 
    			userDao.findUserByEmailAndPassword(request.getEmail(), request.getOldPassword());
    	
    	if(!userOpt.isPresent()){ //존재하지 않는 경우
            result = new BasicResponse();
            result.status = true;
            result.data = "fail";
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    	else { //존재하는 아이디 비번일 경우
    		User user = userOpt.get();
    		user.setPassword(request.getNewPassword());
    		userDao.save(user);
    		result = new BasicResponse();
            result.status = true;
            result.data = "success";
            return new ResponseEntity<>(result, HttpStatus.OK);
    	}	
    }
    
    // 이메일 인증
 	@PostMapping(value = "/emailSend")
 	@Async
 	@ApiOperation(value = "이메일인증")
 	public ResponseEntity<BasicResponse> sendMail(@Valid @RequestBody Map<String, String> data) {
 		SimpleMailMessage simpleMessage = new SimpleMailMessage();

 		// 이메일 인증
 		int random = new Random().nextInt(900000) + 100000;
 		String authCode = String.valueOf(random);
 		
 		System.out.println("인증번호 발송 " + data.get("email"));
 		System.out.println("인증번호는 "+ authCode);
 		simpleMessage.setTo(data.get("email"));
 		simpleMessage.setSubject("이메일 인증");
 		simpleMessage.setText("인증번호: "+authCode);
 		javaMailSender.send(simpleMessage);
 		
 	// 추가로 뷰에 autoCode저장 + 확인필요
		final BasicResponse result = new BasicResponse();
		result.status = true;
		result.data = "success";
		result.object = authCode;
		System.out.println(result);
		System.out.println(result.object);

		return new ResponseEntity<>(result, HttpStatus.OK);
 	}
}