package com.web.curation.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.curation.model.Hospital;
import com.web.curation.model.HospitalRequest;

public interface HospitalDao extends JpaRepository<Hospital, String> {
	Hospital findById(int id);

	int countBySubject(String subject);

	int countBySidoAndGu(String sido, String gu);

	@Query(value = "select h.*,count(f.id) review_cnt, avg(f.star) avg_star from hospital h left JOIN feed f on h.id=f.hospital_id where h.subject = ?1 group by h.id order by CASE WHEN sido ='서울' THEN 0 ELSE 1 END, h.sido, h.gu,h.id limit ?2 offset ?3", nativeQuery = true)
//	@Query(value = "select * from hospital where subject = ?1 order by CASE WHEN sido IN ('서울') THEN 0 ELSE 1 END, sido,gu limit ?2 offset ?3", nativeQuery = true)
//	List<Hospital> selectHospitalSubjectLimitOffset(String subject, int limit, int offset);
	List<HospitalRequest> selectHospitalSubjectLimitOffset(String subject, int limit, int offset);
	
//	@Query(value = "select * from hospital where sido = ?1 and gu = ?2 limit ?3 offset ?4", nativeQuery = true)
	@Query(value = "select h.*,count(f.id) review_cnt, avg(f.star) avg_star from hospital h left JOIN feed f on h.id=f.hospital_id where h.sido = ?1 and h.gu = ?2 limit ?3 offset ?4", nativeQuery = true)
	List<Hospital> selectHospitalSidoAndGuLimitOffset(String sido, String gu, int limit, int offset);

	@Query(value = "select distinct * from hospital where name like %?1% or address like %?1% or sido like %?1% or gu like %?1% or subject like %?1%", nativeQuery = true)
	List<Hospital> findAllByWord(String word);

	@Query(value = "select count(*) from hospital where name like %?1% or address like %?1% or sido like %?1% or gu like %?1% or subject like %?1%", nativeQuery = true)
	int countByWord(String word);

	@Query(value = "select distinct * from hospital where name like %?1% or address like %?1% or sido like %?1% or gu like %?1% or subject like %?1% limit ?2 offset ?3", nativeQuery = true)
	List<Hospital> selectHospitalByWord(String word, int limit, int offset);
	
	
}
