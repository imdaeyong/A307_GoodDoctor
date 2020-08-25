package com.web.curation.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.validation.Valid;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.web.curation.dao.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.ChangepwdRequest;
import com.web.curation.model.Feed;
import com.web.curation.model.SignupRequest;
import com.web.curation.model.User;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })
@EnableSwagger2
//@CrossOrigin(origins = { "https://i3a307.p.ssafy.io" }) //이쪽에 있는 내용만 받아온다는것.
@CrossOrigin(origins = { "*" })
@RestController
public class AccountController {

	@Autowired
	UserDao userDao;
	@Autowired
	public JavaMailSender javaMailSender;

	@PostMapping("/account/gooddoc")
	@ApiOperation(value = "gooddoc 로그인")
	public Object login(@RequestParam(required = true) final String email,
			@RequestParam(required = true) final String password) throws IOException {

		Optional<User> userOpt = userDao.findUserByEmailAndPassword(email, password);

		if (userOpt.isPresent()) {
			User user = new User();
			user = userOpt.get();
			if (userOpt.get().getImageUrl() != null) {
				File f = new File(userOpt.get().getImageUrl());
				String sbase64 = null;
				if (f.isFile()) {
					byte[] bt = new byte[(int) f.length()];
					FileInputStream fis = new FileInputStream(f);
					try {
						fis.read(bt);
						sbase64 = new String(Base64.encodeBase64(bt));
						user.setImageUrl("data:image/png;base64, " + sbase64);
					} finally {
						fis.close();
					}
				}
			}
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/account")
	@ApiOperation(value = "가입하기")
	public Object addImage(MultipartHttpServletRequest file) throws IllegalStateException, IOException {
		final BasicResponse result = new BasicResponse();
//		MultipartFile mFile = file.getFile("file");
		result.status = true;
		if (userDao.getUserByEmail(file.getParameter("email")) != null) {
			result.data = "email_fail";
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		} else if (userDao.getUserByNickname(file.getParameter("nickname")) != null) {
			result.data = "nickname_fail";
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		User user = new User();
		user.setNickname(file.getParameter("nickname"));
		user.setEmail(file.getParameter("email"));
		user.setPassword(file.getParameter("password"));
		user.setImageUrl(null);
		user.setAccountType(0); // gooddoc
		userDao.save(user);
		result.data = "success";

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PutMapping("/pwd")
	@ApiOperation(value = "비밀번호변경")
	public Object changepwd(@Valid @RequestBody ChangepwdRequest request) {
		BasicResponse result = new BasicResponse();
		result.status = true;
		Optional<User> userOpt = userDao.findUserByEmailAndPassword(request.getEmail(), request.getOldPassword());

		if (!userOpt.isPresent()) { // 존재하지 않는 경우
			result.data = "fail";
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		} else { // 존재하는 아이디 비번일 경우
			User user = userOpt.get();
			user.setPassword(request.getNewPassword());
			userDao.save(user);
			result.data = "success";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}

	@PostMapping(value = "/pwd")
	@ApiOperation(value = "비밀번호 찾기")
	public Object sendMailToFindPwd(@Valid @RequestBody Map<String, String> data) {
		SimpleMailMessage simpleMessage = new SimpleMailMessage();

		String email = data.get("email");
		User user = userDao.getUserByEmail(email);
		simpleMessage.setTo(email);
		simpleMessage.setSubject(email + "님에 대한 비밀번호 찾기 결과입니다");
		simpleMessage.setText(email + "님에 대한 비밀번호는 " + user.getPassword());
		HttpStatus httpStatus = HttpStatus.OK;
		if (user.getAccountType() == 0) {
			try {
				javaMailSender.send(simpleMessage);
			} catch (Exception e) {
				// e.printStackTrace();
				httpStatus = HttpStatus.NOT_FOUND;
			}
		}
		return new ResponseEntity<>(user, httpStatus);
	}

	@PostMapping(value = "/email")
	@ApiOperation(value = "이메일인증")
	public ResponseEntity<BasicResponse> sendMail(@Valid @RequestBody Map<String, String> data) {
		SimpleMailMessage simpleMessage = new SimpleMailMessage();
		int random = new Random().nextInt(900000) + 100000;
		String authCode = String.valueOf(random);

		simpleMessage.setTo(data.get("email"));
		simpleMessage.setSubject("이메일 인증 확인 메일");
		simpleMessage.setText(" 인증번호 : " + authCode);
		javaMailSender.send(simpleMessage);

		// 추가로 뷰에 autoCode저장 + 확인필요
		final BasicResponse result = new BasicResponse();
		result.status = true;
		result.data = "success";
		result.object = authCode;

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/account/google")
	@ApiOperation(value = "구글 로그인, 이메일 DB에 있으면 로그인시켜주고 없으면 회원가입 후 로그인!")
	public Object gLogin(@Valid @RequestBody Map<String, String> data) {
		String email = data.get("gEmail");
		String nickname = data.get("gNickname");
		User user = new User();
		user.setAccountType(1);
		user.setEmail(email);
		user.setNickname(nickname);
		user.setPassword("");
		if (userDao.getUserByEmailAndAccountType(email, 1) == null) {
			userDao.save(user);
		}
		user = userDao.getUserByEmailAndAccountType(email, 1);

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PostMapping("/account/kakao")
	@ApiOperation(value = "카카오 로그인, 이메일 DB에 있으면 로그인시켜주고 없으면 회원가입 후 로그인!")
	public Object kLogin(@Valid @RequestBody Map<String, String> data) {

		String email = data.get("email");
		String nickname = data.get("nickname");
		User user = new User();
		user.setAccountType(2);
		user.setEmail(email);
		user.setNickname(nickname);
		user.setPassword("");
		if (userDao.getUserByEmailAndAccountType(email, 2) == null) {
			userDao.save(user);
		}
		user = userDao.getUserByEmailAndAccountType(email, 2);

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@DeleteMapping("/account")
	@ApiOperation(value = "회원 탈퇴")
	public Object remove(@RequestParam("nickname") String nickName, @RequestParam("email") String email,
			@RequestParam("id") int id) {
		User user = userDao.getUserByEmailAndNicknameAndId(email, nickName, id);
		if (user != null) {
			userDao.delete(user);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/profile")
	@ApiOperation(value = "프로필사진 변경")
	public Object changeImage(MultipartHttpServletRequest file) throws IllegalStateException, IOException {

		MultipartFile mFile = file.getFile("file");

		User user = userDao.getUserById((Integer.parseInt(file.getParameter("id"))));
		try {
			if (mFile == null) {
				user.setImageUrl(null);
				userDao.save(user);
			} else {
//				user.setImageUrl("C:\\temptemp\\" + mFile.getOriginalFilename()+".png");
				user.setImageUrl("/home/ubuntu/var/userImage/"+file.getParameter("id")+".png"); //불러올 이미지 위치
				File f = new File(user.getImageUrl());
		       	mFile.transferTo(f);
				userDao.save(user);
				String sbase64 = null;
				if (f.isFile()) {
					byte[] bt = new byte[(int) f.length()];
					FileInputStream fis = new FileInputStream(f);
					try {
						fis.read(bt);
						sbase64 = new String(Base64.encodeBase64(bt));
						user.setImageUrl("data:image/png;base64, " + sbase64);
					} catch (Exception e) {
						return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
					} finally {
						fis.close();
					}
				}
			}
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

}