package com.web.curation.model.mapping;

import java.time.LocalDateTime;

import com.web.curation.model.user.Feed;
import com.web.curation.model.user.User;

public interface CommentMapping {
	int getId();
	int getUserId();
	int getFeedId();
	String getContent();
	
}
