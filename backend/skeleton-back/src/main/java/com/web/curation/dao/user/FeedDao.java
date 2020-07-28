
package com.web.curation.dao.user;

import com.web.curation.model.user.Feed;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedDao extends JpaRepository<Feed, String> {
    Feed getFeedById(int id);
    //List<Feed> findAllFeeds();
}
