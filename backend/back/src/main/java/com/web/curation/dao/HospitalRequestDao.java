package com.web.curation.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.curation.model.Hospital;
import com.web.curation.model.HospitalRequest;

public interface HospitalRequestDao extends JpaRepository<HospitalRequest, String> {
	@Query(value = "select h.*,count(f.id) review_cnt, avg(f.star) avg_star from hospital h left JOIN feed f on h.id=f.hospital_id where h.subject = ?1 group by h.id order by CASE WHEN sido IN ('서울') THEN 0 ELSE 1 END, h.sido, h.gu limit ?2 offset ?3", nativeQuery = true)
	List<HospitalRequest> selectHospitalSubjectLimitOffset(String subject, int limit, int offset);
	
	@Query(value = "select h.*,count(f.id) review_cnt, avg(f.star) avg_star from hospital h left JOIN feed f on h.id=f.hospital_id where h.sido = ?1 and h.gu = ?2 group by h.id limit ?3 offset ?4", nativeQuery = true)
	List<HospitalRequest> selectHospitalSidoAndGuLimitOffset(String sido, String gu, int limit, int offset);
	
	@Query(value = "select h.*,count(f.id) review_cnt, avg(f.star) avg_star from hospital h left JOIN feed f on h.id=f.hospital_id where name like %?1% or address like %?1% or sido like %?1% or gu like %?1% or subject like %?1%  group by h.id limit ?2 offset ?3", nativeQuery = true)
	List<HospitalRequest> selectHospitalByWord(String word, int limit, int offset);

}
