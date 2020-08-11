package com.web.curation.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import com.web.curation.model.BasicResponse;
import com.web.curation.model.Hospital;

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

	@GetMapping("/count")
	@ApiOperation(value = "병원의 전체 수를 반환한다.")
	public Object selectHospitalTotalCount(@RequestParam("subject") String subject,
			@RequestParam("sido") String sido, @RequestParam("gu") String gu, @RequestParam("word") String word) {
		int total = 0;
		if (!subject.equals("")) { // subject 별 찾기
			total = hospitalDao.countBySubject(subject);
		} else if(!sido.equals("") & !gu.equals("")) { // 시도, 구별 찾기
			total = hospitalDao.countBySidoAndGu(sido, gu);
		} else { // 검색한 단어 별 찾기
			total = hospitalDao.countByWord(word);
		}
		return new ResponseEntity<>(total, HttpStatus.OK);
	}

	@GetMapping(value = "")
	@ApiOperation(value = "병원 페이징: offset, 컨텐츠 수 : limit에 해당하는 병원의 정보를 반환한다.")
	public Object selectHospitalLimitOffset(int limit, int offset, String subject, String sido, String gu, String word) {
		List<Hospital> list = new ArrayList<Hospital>();
		if (!subject.equals("")) { // subject
			list = hospitalDao.selectHospitalSubjectLimitOffset(subject, limit, offset);
		} else if(!sido.equals("") & !gu.equals("")) { // 시도, 구별 찾기
			list = hospitalDao.selectHospitalSidoAndGuLimitOffset(sido, gu, limit, offset);			
		} else { // 검색한 단어 별 찾기
			list = hospitalDao.selectHospitalByWord(word, limit, offset);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping(value ="/{id}")
	@ApiOperation(value = "병원 상세정보 보기")
	public Object hospitalDetail(@PathVariable int id) {
		Hospital hospital = hospitalDao.findById(id);
		if (hospital!=null) {
			return new ResponseEntity<>(hospital, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

}
