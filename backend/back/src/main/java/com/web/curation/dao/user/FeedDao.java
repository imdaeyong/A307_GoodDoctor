
package com.web.curation.dao.user;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.curation.model.mapping.FeedMapping;
import com.web.curation.model.user.Feed;

public interface FeedDao extends JpaRepository<Feed, String> {
    Feed getFeedById(int id);
    List<Feed> findAllBy();
    List<FeedMapping> findAllByUserId(int userId);
    
    @Transactional
    @Modifying
    @Query(value="update feed set likes = likes + 1 where id = :feedId", nativeQuery=true)
    void plusLikes(@Param("feedId") int feedId);
    
    @Transactional
    @Modifying
    @Query(value="update feed set likes = likes - 1 where id = :feedId", nativeQuery=true)
    void minusLikes(@Param("feedId") int feedId);
    
    @Query(value = "select * from feed, hospital where feed.hospital_id = hospital.id and (hospital.name like %?1% or feed.content like %?1%)", nativeQuery = true)
	Set<Feed> findAllByWord(String word);

    
}
