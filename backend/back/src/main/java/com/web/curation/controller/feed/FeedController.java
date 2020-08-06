package com.web.curation.controller.feed;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

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

import com.web.curation.dao.user.FeedDao;
import com.web.curation.dao.user.HistoryDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.user.Feed;
import com.web.curation.model.user.History;

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
@RequestMapping("/feeds")
public class FeedController {

   @Autowired
   FeedDao feedDao;
   @Autowired
   HistoryDao historyDao;

   @GetMapping("/{userId}")
   @ApiOperation(value = "모든 피드 가져오기")
   public Object getFeeds(@Valid @PathVariable("userId") int userId)  {
      List<Feed> feeds = feedDao.findAllBy();
      List<History> history = historyDao.findAllByUserId(userId);
      for (Feed feed : feeds) {
    	  history.stream().filter(x-> x.getFeedId() == feed.getId()).forEach(x -> feed.setIsClick(true));
      }
      //feeds.stream().forEach(x-> System.out.println(x.toString()));
      ResponseEntity response = null;
      Collections.sort(feeds, new Comparator<Feed>() {
	       @Override
	       public int compare(Feed s1, Feed s2) {
	        	  return s2.getId() - s1.getId();
	       }
	  });
      if (!feeds.isEmpty()) {
         response = new ResponseEntity<>(feeds, HttpStatus.OK);
      } else {
         response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
      }
      return response;
   }

   @GetMapping("write/{userId}")
   @ApiOperation(value = "모든 피드 가져오기")
   public Object getFeedsByUserId(@Valid @PathVariable("userId") int userId) {
      List<Feed> feeds = feedDao.findAllByUserId(userId);
      Collections.sort(feeds, new Comparator<Feed>() {
            @Override
            public int compare(Feed s1, Feed s2) {
            	return s2.getId() - s1.getId();
            }
      });
      Collections.sort(feeds, new Comparator<Feed>() {
          @Override
          public int compare(Feed s1, Feed s2) {
              if(s1.getIsNew() && !s2.getIsNew()) {
            	  return -1;
              }
              else return 1;
          }
      });
   
      ResponseEntity response = null;
      if (!feeds.isEmpty()) {
         response = new ResponseEntity<>(feeds, HttpStatus.OK);
      } else {
         response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
      }
      return response;
   }
   
   @PutMapping("/")
   @ApiOperation(value = "피드 작성하기")
   public Object addFeed(@Valid @RequestBody Feed request) {
      ResponseEntity response = null;
      Feed feed = feedDao.getFeedById(request.getId());
      feed.setContent(request.getContent());
      feed.setIsNew(false);
      feedDao.save(feed);
      response = new ResponseEntity<>(feed, HttpStatus.OK);
      return response;
   }

   
   //이미지 업로드 테스트
   @PostMapping("/")
   @ApiOperation(value = "이미지 업로드")
   public Object addImage(@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
	   System.out.println(file.getOriginalFilename() + " =================================================");
	   ResponseEntity response = null;
	   response = new ResponseEntity<>(null, HttpStatus.OK);
	   file.transferTo(new File("C:\\temptemp\\"+file.getOriginalFilename()));
	   //DB저장용 String
	   String img = "C:\\temptemp\\"+file.getOriginalFilename();
	   //Feed feed = feedDao.getFeedById(request.getId());
	   //feedDao.save(feed);
	   return response;
   }

   @PutMapping("/like")
   @ApiOperation(value = "좋아요 값 업데이트하기")
   public Object updateLike(@Valid @RequestBody HashMap<String, String> request) throws Exception {
      ResponseEntity response = null;
      System.out.println(request);
      int feedId = Integer.parseInt(request.get("feedId"));
      int userId = Integer.parseInt(request.get("userId"));
      if (request.get("isClick").equals("false")) {//좋아요 누르는 경우 ( likes+1,history테이블에 값 추가 )
    	  History history = new History();
          history.setFeedId(feedId);
          history.setUserId(userId);
    	  feedDao.plusLikes(feedId);
    	  historyDao.save(history);
      } else {//좋아요 이미 눌러진 경우 ( likes -1, history테이블에서 값 제거 )
    	  feedDao.minusLikes(feedId);
    	  History history = historyDao.findByFeedIdAndUserId(feedId, userId);
    	  historyDao.delete(history);
      }
      List<Feed> feeds = feedDao.findAllBy();
      List<History> history = historyDao.findAllByUserId(userId);
      Collections.sort(feeds, new Comparator<Feed>() {
	       @Override
	       public int compare(Feed s1, Feed s2) {
	        	  return s2.getId() - s1.getId();
	       }
	  });
      for (Feed feed : feeds) {
    	  history.stream().filter(x-> x.getFeedId() == feed.getId()).forEach(x -> feed.setIsClick(true));
      }
      response = new ResponseEntity<>(feeds, HttpStatus.OK);
      return response;
   }
   
   
}

//user_id, feed_id, content