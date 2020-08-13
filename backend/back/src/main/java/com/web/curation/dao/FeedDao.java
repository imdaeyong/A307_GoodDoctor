
package com.web.curation.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.curation.model.Feed;

public interface FeedDao extends JpaRepository<Feed, String> {
	Feed getFeedById(int id);

	List<Feed> findAllBy();

	List<Feed> findAllByUserId(int userId);

	//병원 디테일 페이지에서 피드 정보 불러오기
	List<Feed> findAllByHospitalId(int id);
	@Transactional
	@Modifying
	@Query(value = "update feed set likes = likes + 1 where id = :feedId", nativeQuery = true)
	void plusLikes(@Param("feedId") int feedId);

	@Transactional
	@Modifying
	@Query(value = "update feed set likes = likes - 1 where id = :feedId", nativeQuery = true)
	void minusLikes(@Param("feedId") int feedId);

	// 메인 피드
	@Query(value = "select * from feed order by is_new desc,update_date desc limit ?1", nativeQuery = true)
	List<Feed> findAllByCurrentMainFeedSize(int size);

	@Query(value = "select * from feed order by is_new desc,update_date desc limit ?1, 5", nativeQuery = true)
	List<Feed> selectMainFeedLimit(int limit);

	// 리뷰 쓰기
	@Query(value = "select * from feed where user_id = ?1 order by is_new desc,update_date desc limit ?2", nativeQuery = true)
	List<Feed> findAllByUserIdCurrentWriteFeedSize(int userId, int size);

	@Query(value = "select * from feed where user_id = ?1 order by is_new desc,update_date desc limit ?2, 5", nativeQuery = true)
	List<Feed> selectWriteFeedByUserIdLimit(int userId, int limit);

	// 피드 검색
	@Query(value = "select distinct * from feed, hospital where feed.hospital_id = hospital.id and (hospital.name like %?1% or feed.content like %?1%) order by is_new desc,update_date desc limit ?2", nativeQuery = true)
	List<Feed> findAllByWordCurrentSearchFeedSize(String word, int size);

	@Query(value = "select distinct * from feed, hospital where feed.hospital_id = hospital.id and (hospital.name like %?1% or feed.content like %?1%) order by is_new desc,update_date desc limit ?2, 5", nativeQuery = true)
	List<Feed> selectSearchFeedByWordLimit(String word, int limit);

}
