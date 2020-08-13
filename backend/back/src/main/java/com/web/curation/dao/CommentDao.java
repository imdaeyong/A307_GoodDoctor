
package com.web.curation.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.curation.model.Comment;

public interface CommentDao extends JpaRepository<Comment, String> {
	Comment getCommentById(int id);
	// 메인 피드
	@Query(value = "select c.*, u.image_url from comment c,user u where c.user_id=u.id and c.feed_id = ?1", nativeQuery = true)
	List<Comment> findAllByFeedId(int feedId);

}
