
package com.web.curation.dao.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.curation.model.user.Feed;
import com.web.curation.model.user.FeedMapping;

public interface FeedDao extends JpaRepository<Feed, String> {
    Feed getFeedById(int id);
    List<FeedMapping> findAllBy();
}
