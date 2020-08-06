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

import com.web.curation.dao.FeedDao;
import com.web.curation.dao.HistoryDao;
import com.web.curation.dao.HospitalDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.Feed;
import com.web.curation.model.History;
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
@RequestMapping("/search")
public class SearchController {

	@Autowired
	HospitalDao hospitalDao;
	@Autowired
	FeedDao feedDao;
	@Autowired
	HistoryDao historyDao;

	@GetMapping("feed")
	@ApiOperation(value = "검색한 모든 피드 가져오기")
	public Object searchFeed(@RequestParam("word") String word, @RequestParam("userId") int userId) {
		Set<Feed> list = feedDao.findAllByWord(word);
		List<History> history = historyDao.findAllByUserId(userId);
		for (Feed feed : list) {
			history.stream().filter(x -> x.getFeedId() == feed.getId()).forEach(x -> feed.setIsClick(true));
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/pagelink/count")
	@ApiOperation(value = "검색된 병원의 전체 수를 반환한다.")
	public Object selectHospitalTotalCount(@RequestParam("word") String word) {
		int total = hospitalDao.countByWord(word);
		return new ResponseEntity<>(total, HttpStatus.OK);
	}

	@GetMapping(value = "/pagelink")
	@ApiOperation(value = "limit offset 에 해당하는 검색한 병원의 정보를 반환한다.  ")
	public Object selectHospitalLimitOffset(int limit, int offset, String word) {
		Set<Hospital> list = hospitalDao.selectHospitalByWord(word, limit, offset);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

}
