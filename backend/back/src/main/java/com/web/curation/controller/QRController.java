package com.web.curation.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.Valid;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.web.curation.dao.FeedDao;
import com.web.curation.dao.HospitalDao;
import com.web.curation.dao.UserDao;
import com.web.curation.jwt.AES256Cipher;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.Feed;
import com.web.curation.model.Hospital;
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
@RequestMapping("/qr")
public class QRController {
	
	@Autowired
	FeedDao feedDao;
	@Autowired
	HospitalDao hospitalDao;
	@Autowired
	UserDao userDao;

	@GetMapping("")
	@ApiOperation(value = "QR코드 입력 시 페이지 리다이렉트 -> 로그인여부 확인")
	public RedirectView redirectQr(@Valid @RequestParam("hospitalId") String hospitalId) throws Exception {
		AES256Cipher a256 = AES256Cipher.getInstance();
		
//		String str = a256.AES_Encode(hospitalId);
//		a256.AES_Decode(str);
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("http://localhost:3000/qr");
//		redirectView.setUrl("https://i3a307.p.ssafy.io/qr");
		redirectView.addStaticAttribute("hospitalId", a256.AES_Decode(hospitalId));
		return redirectView;
	}

	@PostMapping("")
	@ApiOperation(value = "로그인 필요할 경우, 로그인 동작해주고 피드 추가")
	public Object addQrFeed(@RequestParam(required = true) final int hospitalId,
			@RequestParam(required = true) final String email, @RequestParam(required = true) final String password,
			@RequestParam(required = true) final int userId) {
		final BasicResponse result = new BasicResponse();
		Feed feed = new Feed();
		feed.setContent("");
		feed.setIsNew(true);
		Hospital hospital = hospitalDao.findById(hospitalId);
		feed.setHospital(hospital);
		feed.setIsClick(false);
		feed.setCreateDate(LocalDateTime.now());
		feed.setUpdateDate(LocalDateTime.now());
		if (userId == -1) {
			try {
				User user = userDao.getUserByEmailAndPassword(email, password);
				feed.setUser(user);
				if (user == null) {
					result.data = "login_fail";
					return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
				}
				feedDao.save(feed);
				return new ResponseEntity<>(user, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				result.data = "qr_insert_fail";
				return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			}
		} else {
			try {
				User user = userDao.getUserById(userId);
				feed.setUser(user);
				feedDao.save(feed);
				return new ResponseEntity<>(user, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>("fail", HttpStatus.NOT_FOUND);
			}
		}
	}
}