package com.web.curation.model.mapping;

import java.time.LocalDateTime;

import com.web.curation.model.user.Hospital;
import com.web.curation.model.user.User;

public interface FeedMapping {
	String getContent();
	User getUser();
	Hospital getHospital();
	int getId();
	LocalDateTime getCreateDate();
	LocalDateTime getUpdateDate();
	boolean getIsNew();
}
