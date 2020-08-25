package com.web.curation.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
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
	public Object getComments(@Valid @PathVariable("feedId") int feedId) throws IOException {
		List<Comment> comments = commentDao.findAllByFeedId(feedId);
		for(Comment temp : comments) {
//			System.out.println(temp.getUser());
		}
		if (!comments.isEmpty()) {
			imageUpdate(comments);
			return new ResponseEntity<>(comments, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
	}

	@PostMapping("/")
	@ApiOperation(value = "댓글 등록하기")
	public Object postComment(@Valid @RequestBody Comment comment) throws IOException {
		BasicResponse result = new BasicResponse();
		comment.setCreateDate(LocalDateTime.now());
		String image = comment.getImageUrl();
		if (commentDao.save(comment) != null) {
			List<Comment> comments = commentDao.findAllByFeedId(comment.getFeedId());
			imageUpdate(comments);
			comments.get(comments.size()-1).setImageUrl(image);
			return new ResponseEntity<>(comments, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	 
	public void imageUpdate(List<Comment> comments) throws IOException {

		for (Comment comment : comments) {
			if (comment.getImageUrl() != null) {
				File f = new File(comment.getImageUrl());
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
				comment.setImageUrl("data:image/png;base64, " + sbase64);
			}
		}
	}

}