package com.web.curation.controller.qr;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
		Feed feed = new Feed();
		feed.setContent("");
		feed.setIsNew(true);
		Hospital hospital = hospitalDao.findById(hospitalId);
		User user = userDao.getUserById(1);
		//test 계정 ( 임시 )
		feed.setUser(user);
		feed.setHospital(hospital);
		feed.setIsClick(false);
		feedDao.save(feed);
		RedirectView redirectView = new RedirectView();
	    redirectView.setUrl("http://localhost:3000/feed/write");
	    //redirectView.setUrl("http://i3a307.p.ssafy.io/feed/write");
		return redirectView;
	}
}