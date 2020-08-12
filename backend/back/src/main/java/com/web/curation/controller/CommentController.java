package com.web.curation.controller;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.dao.CommentDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.Comment;

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
@RequestMapping("/comments")
public class CommentController {

	@Autowired
	CommentDao commentDao;

	@GetMapping("/{feedId}")
	@ApiOperation(value = "모든 댓글 가져오기")
	public Object getComments(@Valid @PathVariable("feedId") int feedId) {
		List<Comment> comments = commentDao.findAllByFeedId(feedId);
		if (!comments.isEmpty()) {
			return new ResponseEntity<>(comments, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/")
	@ApiOperation(value = "댓글 등록하기")
	public Object postComment(@Valid @RequestBody Comment comment) {
		BasicResponse result = new BasicResponse();
		comment.setCreateDate(LocalDateTime.now());
		result.status = true;
		if (commentDao.save(comment) != null) {
			result.data = "success";
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			result.data = "fail";
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
	}
}