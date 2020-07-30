package com.web.curation.dao.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.curation.model.user.Hospital;

public interface HospitalDao extends JpaRepository<Hospital, String> {
   List<Hospital> findTop100AllBySubject(String subject);
   Hospital findById(int id);
   List<Hospital> findTop100AllBySidoAndGu(String sido, String gu);
   int countBySubject(String subject);
   int countBySidoAndGu(String sido, String gu);
   
   @Query(value = "select * from hospital where subject = ?1 limit ?2 offset ?3", nativeQuery = true)
	List<Hospital> selectHospitalSubjectLimitOffset(String subject, int limit, int offset);
	
	@Query(value = "select * from hospital where sido = ?1 and gu = ?2 limit ?3 offset ?4", nativeQuery = true)
	List<Hospital> selectHospitalSidoAndGuLimitOffset(String sido, String gu, int limit, int offset);
}