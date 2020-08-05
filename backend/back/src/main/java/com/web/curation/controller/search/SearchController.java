package com.web.curation.controller.search;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.dao.user.FeedDao;
import com.web.curation.dao.user.HospitalDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.user.Feed;
import com.web.curation.model.user.Hospital;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@EnableSwagger2
@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/search")

public class SearchController {

	@Autowired
	HospitalDao hospitalDao;
	
	@Autowired
	   FeedDao feedDao;
	
	@GetMapping("feed")
	@ApiOperation(value = "검색한 모든 피드 가져오기")
	public Object searchFeed(@RequestParam("word") String word) {
		Set<Feed> list = feedDao.findAllByWord(word);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
		
	}
	

	@GetMapping("")
	@ApiOperation(value = "검색")
	public Object search(@RequestParam("word") String word) {
		Set<Hospital> list = hospitalDao.findAllByWord(word);
		if (list.size() == 0) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(list, HttpStatus.OK);
		}

	}

	@GetMapping("/pagelink/count")
	@ApiOperation(value = "검색된 병원의 전체 수를 반환한다.")
	public ResponseEntity<Integer> selectHospitalTotalCount(@RequestParam("word") String word) {

		int total = hospitalDao.countByWord(word);

		return new ResponseEntity<Integer>(total, HttpStatus.OK);
	}
	

	@GetMapping(value = "/pagelink")
	@ApiOperation(value = "limit offset 에 해당하는 검색한 병원의 정보를 반환한다.  ")
	public ResponseEntity<Set<Hospital>> selectHospitalLimitOffset(int limit, int offset, String word) {

		Set<Hospital> list = hospitalDao.selectHospitalByWord(word, limit, offset);

		return new ResponseEntity<Set<Hospital>>(list, HttpStatus.OK);
	}

}
