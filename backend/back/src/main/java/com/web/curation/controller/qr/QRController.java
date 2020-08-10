package com.web.curation.controller.qr;

import java.time.LocalDateTime;

import javax.validation.Valid;

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
	
	@GetMapping("/{hospitalId}")
	@ApiOperation(value = "모든 피드 가져오기")
	public RedirectView redirectQr(@Valid @PathVariable("hospitalId") int hospitalId){
		RedirectView redirectView = new RedirectView();
	    redirectView.setUrl("http://localhost:3000/qr");
	    //redirectView.setUrl("https://i3a307.p.ssafy.io/qr");
	    redirectView.addStaticAttribute("hospitalId", hospitalId);
		return redirectView;
	}
	
	@PostMapping("/wlogin")
	@ApiOperation(value = "모든 피드 가져오기")
	public Object addQrFeedWithLogin(@RequestParam(required = true) final int hospitalId, @RequestParam(required = true) final String email,
			@RequestParam(required = true) final String password){
		final BasicResponse result = new BasicResponse();
		User user = userDao.getUserByEmailAndPassword(email, password);
		if (user == null) {
			result.data = "login_fail";
			return new ResponseEntity<>(result,HttpStatus.NOT_FOUND);
		}
		System.out.println("wlogin");
		System.out.println("hospitalId");
		System.out.println("email");
		System.out.println("password");
		Feed feed = new Feed();
		feed.setContent("");
		feed.setIsNew(true);
		Hospital hospital = hospitalDao.findById(hospitalId);
//		test 계정 ( 임시 )
		feed.setUser(user);
		feed.setHospital(hospital);
		feed.setIsClick(false);
		feed.setCreateDate(LocalDateTime.now());
		feed.setUpdateDate(LocalDateTime.now());
		try {
			feedDao.save(feed);
			return new ResponseEntity<>(user,HttpStatus.OK);			
		} catch (Exception e) {
			result.data = "qr_insert_fail";
			return new ResponseEntity<>(result,HttpStatus.NOT_FOUND);
		}
//		RedirectView redirectView = new RedirectView();
//		redirectView.setUrl("http://localhost:3000/feed/write");
//	    redirectView.setUrl("https://i3a307.p.ssafy.io/feed/write");
	}
	
	@PostMapping("/wologin")
	@ApiOperation(value = "모든 피드 가져오기")
	public Object addQrFeedWithoutLogin(@RequestParam(required = true) final int hospitalId, @RequestParam(required = true) final int userId){
		System.out.println("wologin");
		System.out.println(hospitalId);
		System.out.println(userId);
		Feed feed = new Feed();
		feed.setContent("");
		feed.setIsNew(true);
		Hospital hospital = hospitalDao.findById(hospitalId);
		User user = userDao.getUserById(userId);
//		test 계정 ( 임시 )
		feed.setUser(user);
		feed.setHospital(hospital);
		feed.setIsClick(false);
		feed.setCreateDate(LocalDateTime.now());
		feed.setUpdateDate(LocalDateTime.now());
		try {
			//feedDao.save(feed);
			return new ResponseEntity<>("success", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("fail", HttpStatus.NOT_FOUND);
		}
	}
}