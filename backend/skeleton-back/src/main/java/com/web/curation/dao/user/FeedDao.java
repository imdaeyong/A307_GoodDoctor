
package com.web.curation.dao.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.curation.model.mapping.FeedMapping;
import com.web.curation.model.user.Feed;

public interface FeedDao extends JpaRepository<Feed, String> {
    Feed getFeedById(int id);
    List<FeedMapping> findAllBy();
    List<FeedMapping> findAllByUserId(int userId);
    
}
