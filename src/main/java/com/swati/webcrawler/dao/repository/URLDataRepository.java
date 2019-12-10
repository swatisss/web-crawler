package com.swati.webcrawler.dao.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.swati.webcrawler.dto.URLData;

public interface URLDataRepository extends JpaRepository<URLData, Long>{
	
	Optional<URLData> getByUuid(final String uuid);
	
//	@Query("update URLData u set c.status = :status WHERE c.uuid = :uuid")
//    void markCompleted(@Param("uuid") String  uuid, @Param("status") String status);
}
