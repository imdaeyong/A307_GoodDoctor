package com.web.curation.controller.feed;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.dao.user.FeedDao;
import com.web.curation.dao.user.HistoryDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.mapping.FeedMapping;
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

   @GetMapping("/")
   @ApiOperation(value = "모든 피드 가져오기")
   public Object getFeeds() {
      List<Feed> feeds = feedDao.findAllBy();
      List<History> history = historyDao.findAllByUserId(1);
      for (Feed feed : feeds) {
    	  history.stream().filter(x-> x.getFeed().getId() == feed.getId()).forEach(x -> feed.setClick(true));
      }
      feeds.stream().forEach(x-> System.out.println(x.toString()));
      ResponseEntity response = null;
//      Collections.sort(feeds, new Comparator<Feed>() {
//          @Override
//          public int compare(Feed s1, Feed s2) {
//              if (s1.getIsNew()) {
//              	if(s1.getId() > s2.getId())
//              		return -1;
//              	else return 1;
//              } else {
//              	if(s1.getId() > s2.getId())
//              		return -1;
//              	else return 1;
//              }
//          }
//      });
      if (!feeds.isEmpty()) {
         response = new ResponseEntity<>(feeds, HttpStatus.OK);
      } else {
         response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
      }
      return response;
   }

   @GetMapping("/{userId}")
   @ApiOperation(value = "모든 피드 가져오기")
   public Object getFeedsByUserId(@Valid @PathVariable("userId") int userId) {
      List<FeedMapping> feeds = feedDao.findAllByUserId(userId);
      Collections.sort(feeds, new Comparator<FeedMapping>() {
            @Override
            public int compare(FeedMapping s1, FeedMapping s2) {
                if (s1.getIsNew()) {
                	if(s1.getId() > s2.getId())
                		return -1;
                	else return 1;
                } else {
                	if(s1.getId() > s2.getId())
                		return -1;
                	else return 1;
                }
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
      feed.setNew(false);
      
      feedDao.save(feed);
      response = new ResponseEntity<>(feed, HttpStatus.OK);
      return response;
   }
   
}

//user_id, feed_id, content