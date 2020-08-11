package com.web.curation.controller.search;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/search")
public class SearchController {

	@Autowired
	HospitalDao hospitalDao;
	@Autowired
	FeedDao feedDao;
	@Autowired
	HistoryDao historyDao;

//	@GetMapping("feed")
//	@ApiOperation(value = "검색한 모든 피드 가져오기")
//	public Object searchFeed(@RequestParam("word") String word, @RequestParam("userId") int userId) {
//		Set<Feed> list = feedDao.findAllByWord(word);
//		List<History> history = historyDao.findAllByUserId(userId);
//		for (Feed feed : list) {
//			history.stream().filter(x -> x.getFeedId() == feed.getId()).forEach(x -> feed.setIsClick(true));
//		}
//		return new ResponseEntity<>(list, HttpStatus.OK);
//	}

	@GetMapping("/feed")
	@ApiOperation(value = "검색된 피드 infinite Loading")
	public Object searchFeedByWordLimit(@RequestParam("userId") int userId, @RequestParam("limit") int limit,
			@RequestParam("word") String word, String list_code) throws IOException {
			
		Set<Feed> feeds = null;
		if(list_code == null) {
			feeds = feedDao.selectSearchFeedByWordLimit(word, limit);			
		} else {
			feeds = feedDao.findAllByWordCurrentSearchFeedSize(word, limit);
		}
		

		List<History> history = historyDao.findAllByUserId(userId);

		for (Feed feed : feeds) {
			User user = feed.getUser();
			if (feed.getImageUrl() != null) {
				File f = new File(feed.getImageUrl());
				String sbase64 = null;
				if (f.isFile()) {
					byte[] bt = new byte[(int) f.length()];
					FileInputStream fis = new FileInputStream(f);
					try {
						fis.read(bt);
						sbase64 = new String(Base64.encodeBase64(bt));
					} finally {
						fis.close();
					}
				}
				feed.setImageUrl("data:image/png;base64, " + sbase64);
			}
			if (user.getImageUrl() != null) {
				File f = new File(user.getImageUrl());
				String sbase64 = null;
				if (f.isFile()) {
					byte[] bt = new byte[(int) f.length()];
					FileInputStream fis = new FileInputStream(f);
					try {
						fis.read(bt);
						sbase64 = new String(Base64.encodeBase64(bt));
						user.setImageUrl("data:image/png;base64, " + sbase64);
						feed.setUser(user);
					} finally {
						fis.close();
					}
				}
			}
			history.stream().filter(x -> x.getFeedId() == feed.getId()).forEach(x -> feed.setIsClick(true));
		}

		return new ResponseEntity<>(feeds, HttpStatus.OK);
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

	@PutMapping("/like")
	@ApiOperation(value = "좋아요 값 업데이트하기")
	public Object updateLike(@Valid @RequestBody HashMap<String, String> request) throws Exception {

		int feedId = Integer.parseInt(request.get("feedId"));
		int userId = Integer.parseInt(request.get("userId"));
		int size = Integer.parseInt(request.get("size"));
		String word = request.get("word");

		Feed feed = null;
		if (request.get("isClick").equals("false")) {// 좋아요 누르는 경우 ( likes+1,history테이블에 값 추가 )
			History history = new History();
			history.setFeedId(feedId);
			history.setUserId(userId);
			feedDao.plusLikes(feedId);
			historyDao.save(history);
			feed = feedDao.getFeedById(feedId);
			feed.setIsClick(true);
		} else {// 좋아요 이미 눌러진 경우 ( likes -1, history테이블에서 값 제거 )
			feedDao.minusLikes(feedId);
			History history = historyDao.findByFeedIdAndUserId(feedId, userId);
			historyDao.delete(history);
			feed = feedDao.getFeedById(feedId);
			feed.setIsClick(false);
		}

		Set<Feed> feeds = null;

		if (request.get("likeType").equals("modal")) { // 모달창에서 실행한 경우 feed하나만 넘겨준다.
			return feed;
		} else {
			return searchFeedByWordLimit(userId, size, word, "search");
		}
	}
}
