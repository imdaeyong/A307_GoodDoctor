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

}