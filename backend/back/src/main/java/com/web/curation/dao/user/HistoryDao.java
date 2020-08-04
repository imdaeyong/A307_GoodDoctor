
package com.web.curation.dao.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.curation.model.mapping.FeedMapping;
import com.web.curation.model.user.Feed;
import com.web.curation.model.user.History;

public interface HistoryDao extends JpaRepository<History, String> {
    List<History> findAllByUserId(int userId);
    History findByFeedIdAndUserId(int feedId, int userId);
}
