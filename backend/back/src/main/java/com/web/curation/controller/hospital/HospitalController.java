package com.web.curation.controller.hospital;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.dao.HospitalDao;
import com.web.curation.dao.HospitalInfoDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.Hospital;
import com.web.curation.model.HospitalInfo;

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
@RequestMapping("/hospitals")
public class HospitalController {

	@Autowired
	HospitalDao hospitalDao;
	@Autowired
	HospitalInfoDao hospitalinfoDao;

	@GetMapping("/pagelink/count")
	@ApiOperation(value = "병원의 전체 수를 반환한다.")
	public Object selectHospitalTotalCount(@RequestParam("subject") String subject,
			@RequestParam("sido") String sido, @RequestParam("gu") String gu) {
		int total = 0;
		if (subject.equals("undefined")) { // 시도, 구 지역별 찾기
			total = hospitalDao.countBySidoAndGu(sido, gu);
		} else { // subject 별 찾기
			total = hospitalDao.countBySubject(subject);
		}
		return new ResponseEntity<>(total, HttpStatus.OK);
	}

	@GetMapping(value = "/pagelink")
	@ApiOperation(value = "병원 페이징: offset, 컨텐츠 수 : limit에 해당하는 병원의 정보를 반환한다.")
	public Object selectHospitalLimitOffset(int limit, int offset, String subject, String sido, String gu) {
		List<Hospital> list = new ArrayList<Hospital>();
		if (subject == null || subject.equals("undefined")) {
			list = hospitalDao.selectHospitalSidoAndGuLimitOffset(sido, gu, limit, offset);
		} else {
			list = hospitalDao.selectHospitalSubjectLimitOffset(subject, limit, offset);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping(value ="/{id}")
	@ApiOperation(value = "병원 상세정보 보기")
	public Object hospitalDetail(@PathVariable int id) {
		HospitalInfo hospitalInfo = hospitalinfoDao.findById(id);
		if (hospitalInfo!=null) {
			return new ResponseEntity<>(hospitalInfo, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

}
