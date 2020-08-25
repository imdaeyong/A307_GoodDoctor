
package com.web.curation.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.curation.model.History;

public interface HistoryDao extends JpaRepository<History, String> {
	List<History> findAllByUserId(int userId);

	History findByFeedIdAndUserId(int feedId, int userId);
}
