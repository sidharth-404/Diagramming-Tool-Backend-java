package com.diagrammingtool.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diagrammingtool.app.model.CanvasImage;
import com.diagrammingtool.app.model.UserRegistration;
import com.diagrammingtool.app.repository.CanvasImageRepository;

@Service
public class CanvasImageService {

    private final CanvasImageRepository canvasImageRepository;

    @Autowired
    public CanvasImageService(CanvasImageRepository canvasImageRepository) {
        this.canvasImageRepository = canvasImageRepository;
    }

    public CanvasImage saveCanvasImage(CanvasImage canvasImage) {
        return canvasImageRepository.save(canvasImage);
    }
    
    public List<CanvasImage> getCanvasImagesByUserId(Long userId) {
        return canvasImageRepository.findByUserId(userId);
    }
  
	public List<CanvasImage> getAllDiagrams() {
		
		return canvasImageRepository.findAll();
	}
	
	
	public CanvasImage updateCanvasImage(Long id, CanvasImage updatedCanvasImage) {
        CanvasImage existingCanvasImage = canvasImageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Canvas Image not found with id: " + id));
        existingCanvasImage.setImageData(updatedCanvasImage.getImageData());
        return canvasImageRepository.save(existingCanvasImage);
    }
	
}
