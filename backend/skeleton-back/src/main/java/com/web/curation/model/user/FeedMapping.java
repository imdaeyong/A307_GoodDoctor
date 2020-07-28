package com.web.curation.model.user;

import java.time.LocalDateTime;

public interface FeedMapping {
	String getContent();
	User getUser();
	int getId();
	LocalDateTime getCreateDate();
	LocalDateTime getUpdateDate();
	
}
