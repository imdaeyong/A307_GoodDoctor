package com.web.curation.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.curation.model.Hospital;

public interface HospitalDao extends JpaRepository<Hospital, String> {
	Hospital findById(int id);

	int countBySubject(String subject);

	int countBySidoAndGu(String sido, String gu);

	
	@Query(value = "select * from hospital where subject = ?1 order by CASE WHEN sido IN ('서울') THEN 0 ELSE 1 END, sido,gu limit ?2 offset ?3", nativeQuery = true)
	List<Hospital> selectHospitalSubjectLimitOffset(String subject, int limit, int offset);

	@Query(value = "select * from hospital where sido = ?1 and gu = ?2 limit ?3 offset ?4", nativeQuery = true)
	List<Hospital> selectHospitalSidoAndGuLimitOffset(String sido, String gu, int limit, int offset);

	@Query(value = "select * from hospital where name like %?1% or address like %?1% or sido like %?1% or gu like %?1% or subject like %?1%", nativeQuery = true)
	Set<Hospital> findAllByWord(String word);

	@Query(value = "select count(*) from hospital where name like %?1% or address like %?1% or sido like %?1% or gu like %?1% or subject like %?1%", nativeQuery = true)
	int countByWord(String word);

	@Query(value = "select * from hospital where name like %?1% or address like %?1% or sido like %?1% or gu like %?1% or subject like %?1% limit ?2 offset ?3", nativeQuery = true)
	Set<Hospital> selectHospitalByWord(String word, int limit, int offset);
}
