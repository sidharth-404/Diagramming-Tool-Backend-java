package com.diagrammingtool.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.diagrammingtool.app.model.CanvasImage;

@Repository
public interface CanvasImageRepository extends JpaRepository<CanvasImage, Long> {
	@Query("SELECT c FROM CanvasImage c WHERE c.user.id = :userId")
    List<CanvasImage> findByUserId(@Param("userId") Long userId);
	}
