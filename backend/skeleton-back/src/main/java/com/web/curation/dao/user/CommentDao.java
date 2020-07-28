
package com.web.curation.dao.user;

import java.util.List;
import java.util.Optional;

import com.web.curation.model.mapping.CommentMapping;
import com.web.curation.model.user.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentDao extends JpaRepository<Comment, String> {
    Comment getCommentById(int id);
    //List<CommentMapping> findAllBy();
}
