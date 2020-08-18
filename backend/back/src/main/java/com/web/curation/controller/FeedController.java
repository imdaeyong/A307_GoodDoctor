package com.web.curation.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.apache.tomcat.util.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
	
	@GetMapping(value = "/hospital")
	   @ApiOperation(value = "병원아이디로 관련피드들 가져오기")
	   public Object searchFeedsByHospitalId(@RequestParam("userId") int userId, @RequestParam("id") int id) throws Exception {
	      List<Feed> feeds = feedDao.findAllByHospitalId(id);
	      imageUpdate(userId, feeds, "main");
	      return new ResponseEntity<>(feeds, HttpStatus.OK);
	   }	

	
	@GetMapping("/")
	@ApiOperation(value = "메인+검색 피드 infinite Loading")
	public Object getFeedsLimit(@RequestParam("userId") int userId, @RequestParam("limit") int limit,
			@RequestParam("word") String word) throws IOException {
		List<Feed> feeds = null;
		if (word.equals("")) { // 전체 검색 -> 검색 단어가 없을 때
			feeds = feedDao.selectMainFeedLimit(limit);
		} else { // 검색 단어가 있을 때
			feeds = feedDao.selectSearchFeedByWordLimit(word, limit);
		}

		imageUpdate(userId, feeds, "main");

		return new ResponseEntity<>(feeds, HttpStatus.OK);

	}

	@GetMapping("/write")
	@ApiOperation(value = "피드 작성 화면에서 userId의 피드를 가져와서 infinite Loading")
	public Object getFeedsWriteLimit(@RequestParam("userId") int userId, @RequestParam("limit") int limit,
			String list_code) throws IOException {

		List<Feed> feeds = feedDao.selectWriteFeedByUserIdLimit(userId, limit);

		imageUpdate(userId, feeds, "main");

		return new ResponseEntity<>(feeds, HttpStatus.OK);

	}

	@PutMapping("/")
	@ApiOperation(value = "피드 작성하기")
	public Object addImage(MultipartHttpServletRequest file) throws IllegalStateException, IOException {

		MultipartFile mFile = file.getFile("file");
		Feed feed = feedDao.getFeedById(Integer.parseInt(file.getParameter("feedId")));
		try {
			if (mFile == null) {
				feed.setImageUrl(null);
			} else {
				feed.setImageUrl("C:\\temptemp\\" + mFile.getOriginalFilename());
				mFile.transferTo(new File("C:\\temptemp\\" + mFile.getOriginalFilename()));
//				feed.setImageUrl("/home/ubuntu/var/feedImage/" + file.getParameter("feedId")+".png"); // 불러올 이미지 위치
//				mFile.transferTo(new File("/home/ubuntu/var/feedImage/" + file.getParameter("feedId")+".png"));
			}
			feed.setContent(file.getParameter("content"));
			feed.setStar(Double.parseDouble(file.getParameter("star")));
			feed.setIsNew(false);
			System.out.println(feed.toString());
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

		
		return feed;
		

	}
	@GetMapping("/crawling")
	@ApiOperation(value = "피드 작성 화면에서 userId의 피드를 가져와서 infinite Loading")
	public String crawling() throws IOException {
		String url = "https://search.naver.com/search.naver?sm=top_sug.pre&fbm=1&acr=1&acq=%EC%BD%94%EB%A1%9C%EB%82%98+%ED%98%86&qdt=0&ie=utf8&query=%EC%BD%94%EB%A1%9C%EB%82%98+%ED%98%84%ED%99%A9";
		
		Document doc = Jsoup.connect(url).get();
		
		Elements element1 = doc.select("div.status_info");
		int cnt = 0;
		String result ="";
		for(Element el : element1.select("li")) {
			if(cnt == 4) break;
			result += el.text() + " ";
			cnt++;
		}
		return result;
	}
	
	public void imageUpdate(int userId, List<Feed> feeds, String type) throws IOException {

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
			if (type.equals("main")) {
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
			} else if (type.equals("write")) {
				if (!feed.getIsNew())
					history.stream().filter(x -> x.getFeedId() == feed.getId()).forEach(x -> feed.setIsClick(true));
			}
		}
	}
	
	
	
	
	
}