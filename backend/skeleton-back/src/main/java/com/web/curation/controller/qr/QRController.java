package com.web.curation.controller.qr;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.web.curation.dao.user.FeedDao;
import com.web.curation.dao.user.HospitalDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.mapping.FeedMapping;
import com.web.curation.model.user.Feed;
import com.web.curation.model.user.Hospital;
import com.web.curation.model.user.SignupRequest;
import com.web.curation.model.user.User;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
      @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
      @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })
@EnableSwagger2
//@CrossOrigin(origins = { "http://localhost:3000" }) //이쪽에 있는 내용만 받아온다는것.
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
	public RedirectView redirectQr(@Valid @PathVariable("hospitalId") int hospitalId, HttpServletResponse response) throws IOException {
		
		Feed feed = new Feed();
		feed.setContent("");
		feed.setNew(true);
		Hospital hospital = hospitalDao.findById(hospitalId);
		//test 계정 ( 임시 )
		User user = userDao.getUserById(1);
		feed.setUser(user);
		feed.setHospital(hospital);
		feedDao.save(feed);
//		response.sendRedirect("http://localhost:3000/feed/write");
		RedirectView redirectView = new RedirectView();
	    redirectView.setUrl("http://localhost:3000/feed/write");
		return redirectView;
	}
}

//user_id, feed_id, content