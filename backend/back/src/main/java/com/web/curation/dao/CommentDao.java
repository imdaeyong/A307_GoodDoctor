
package com.web.curation.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.curation.model.Comment;

public interface CommentDao extends JpaRepository<Comment, String> {
	Comment getCommentById(int id);

	List<Comment> findAllByFeedId(int feedId);
}
