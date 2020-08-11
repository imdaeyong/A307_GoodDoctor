package com.web.curation.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.curation.model.HospitalInfo;

public interface HospitalInfoDao extends JpaRepository<HospitalInfo, String> {
	HospitalInfo findById(int id);
}
