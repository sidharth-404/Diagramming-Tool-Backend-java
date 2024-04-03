package com.diagrammingtool.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.diagrammingtool.app.model.CanvasImage;
import com.diagrammingtool.app.model.UserRegistration;
import com.diagrammingtool.app.service.CanvasImageService;

@RestController
@RequestMapping("/api/diagrammingtool")
@CrossOrigin(origins = "*")
public class CanvasImageController {

    private final CanvasImageService canvasImageService;

    @Autowired
    public CanvasImageController(CanvasImageService canvasImageService) {
        this.canvasImageService = canvasImageService;
    }

    @PostMapping("/images")
    public ResponseEntity<CanvasImage> saveCanvasImage(@RequestBody CanvasImage canvasImage) {
        CanvasImage savedCanvasImage = canvasImageService.saveCanvasImage(canvasImage);
        return new ResponseEntity<>(savedCanvasImage, HttpStatus.CREATED);
    }
    
    @GetMapping("/images/{userId}")
    public ResponseEntity<List<CanvasImage>> getCanvasImagesByUserId(@PathVariable Long userId) {
        List<CanvasImage> canvasImages = canvasImageService.getCanvasImagesByUserId(userId);
        return new ResponseEntity<>(canvasImages, HttpStatus.OK);
    }
    
    @GetMapping("/getDiagrams")
	public ResponseEntity<List<CanvasImage>> getAllUser(){
		return ResponseEntity.ok(canvasImageService.getAllDiagrams());
				}
    
    
    @PatchMapping("/updateImages/{id}")
    public ResponseEntity<CanvasImage> updateCanvasImage(
            @PathVariable Long id,
            @RequestBody CanvasImage updatedCanvasImage) {
        CanvasImage updatedImage = canvasImageService.updateCanvasImage(id, updatedCanvasImage);
        return new ResponseEntity<>(updatedImage, HttpStatus.OK);
    }
	
}
