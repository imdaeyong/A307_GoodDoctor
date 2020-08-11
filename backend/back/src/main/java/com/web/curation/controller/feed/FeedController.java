package com.web.curation.controller.feed;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.web.curation.dao.FeedDao;
import com.web.curation.dao.HistoryDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.Feed;
import com.web.curation.model.History;
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
@RequestMapping("/feeds")
public class FeedController {

	@Autowired
	FeedDao feedDao;
	@Autowired
	HistoryDao historyDao;

	@GetMapping("/")
	@ApiOperation(value = "메인 피드 infinite Loading")
	public Object getFeedsLimit(@RequestParam("userId") int userId, @RequestParam("limit") int limit, String list_code) throws IOException {
		List<Feed> feeds = null;
		if(list_code == null) {	// 메인 부르는 경우
			feeds = feedDao.selectMainFeedLimit(limit);	
		} else { // 좋아요를 눌렀을 경우
			feeds = feedDao.findAllByCurrentMainFeedSize(limit);
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

	@GetMapping("/write")
	@ApiOperation(value = "피드 작성 화면에서 userId의 피드를 가져와서 infinite Loading")
	public Object getFeedsWriteLimit(@RequestParam("userId") int userId, @RequestParam("limit") int limit, String list_code) throws IOException {

		List<Feed> feeds = null;
		if(list_code == null) {//NavBar에서 리뷰쓰기 클릭 시
			feeds = feedDao.selectWriteFeedByUserIdLimit(userId, limit);			
		} else {//좋아요 누르면 "like"로 구분해서 현재 infiniteloading 된거 만큼 가져옴
			feeds = feedDao.findAllByUserIdCurrentWriteFeedSize(userId, limit);
		}
		

		List<History> history = historyDao.findAllByUserId(userId);

		for (Feed feed : feeds) {
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
			System.out.println(feed);
			if (!feed.getIsNew())
				history.stream().filter(x -> x.getFeedId() == feed.getId()).forEach(x -> feed.setIsClick(true));
		}

		return new ResponseEntity<>(feeds, HttpStatus.OK);

	}

	@PutMapping("/")
	@ApiOperation(value = "피드 작성하기")
	public Object addImage(MultipartHttpServletRequest file) throws IllegalStateException, IOException {
		MultipartFile mFile = file.getFile("file");
		Feed feed = feedDao.getFeedById(Integer.parseInt(file.getParameter("feedId")));
		try {
			if (mFile == null) {
				feed.setImageUrl("");
			} else {
				feed.setImageUrl("C:\\temptemp\\" + mFile.getOriginalFilename());			
				mFile.transferTo(new File("C:\\temptemp\\" + mFile.getOriginalFilename()));
//		feed.setImageUrl("/home/ubuntu/var/images"+mFile.getOriginalFilename()); //불러올 이미지 위치
//	    mFile.transferTo(new File("/home/ubuntu/var/images"+mFile.getOriginalFilename()));
			}
			feed.setContent(file.getParameter("content"));
			feed.setIsNew(false);
			feedDao.save(feed);			
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/like")
	@ApiOperation(value = "좋아요 값 업데이트하기")
	public Object updateLike(@Valid @RequestBody HashMap<String, String> request) throws Exception {
		int feedId = Integer.parseInt(request.get("feedId"));
		int userId = Integer.parseInt(request.get("userId"));
		int size = Integer.parseInt(request.get("size"));

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

		if (request.get("likeType").equals("modal")) { // 모달창에서 실행한 경우 feed하나만 넘겨준다.
			return feed;
		} else if (request.get("likeType").equals("write")) {
			return getFeedsWriteLimit(userId, size, "like");
		} else {//메인에서 likeType 넘겨줬을 때
			return getFeedsLimit(userId, size, "like");
		}
	}
}