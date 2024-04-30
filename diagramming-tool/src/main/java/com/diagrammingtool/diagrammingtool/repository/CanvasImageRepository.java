package com.diagrammingtool.app.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diagrammingtool.app.model.CanvasImage;

@Repository
public interface CanvasImageRepository extends JpaRepository<CanvasImage,Long> {	
}
