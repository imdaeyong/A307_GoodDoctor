package com.web.curation.controller.comment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.web.curation.dao.user.CommentDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.mapping.CommentMapping;
import com.web.curation.model.mapping.FeedMapping;
import com.web.curation.model.user.ChangepwdRequest;
import com.web.curation.model.user.Comment;
import com.web.curation.model.user.SignupRequest;
import com.web.curation.model.user.User;

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
@RequestMapping("/comments")
public class CommentController {

	@Autowired
	CommentDao commentDao;

	@GetMapping("/")
	@ApiOperation(value = "모든 댓글 가져오기")
	public Object getComments() {
		//List<CommentMapping> comments = commentDao.findAllBy();
		ResponseEntity response = null;
//		if (!comments.isEmpty()) {
//			response = new ResponseEntity<>(comments, HttpStatus.OK);
//		} else {
//			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//		}
		return response;
	}
	
	@PostMapping("/")
	@ApiOperation(value = "모든 댓글 등록하기")
	public Object getCommentByFeedId(@Valid @RequestBody Comment comment) {
		System.out.println("--------------------댓글-----------------");
		System.out.println(comment.getUserId());
		System.out.println(comment.getFeedId());
		ResponseEntity response = null;
		BasicResponse result = new BasicResponse();
		if (commentDao.save(comment) != null) {
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			result.status = true;
			result.data = "fail";
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		return response;
	}
}