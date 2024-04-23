package com.diagrammingtool.app.controller;

import java.util.List;

import org.junit.runners.Parameterized.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diagrammingtool.app.model.CanvasImage;
import com.diagrammingtool.app.service.CanvasImageService;

@RestController
@RequestMapping("/api/diagrammingtool")
@CrossOrigin("*")
public class CanvasImageController {
	
	@Autowired
	private CanvasImageService service;
	
	@PostMapping("/saveDummyImage")
	public ResponseEntity<CanvasImage> SaveImage(@RequestBody CanvasImage canvasImageDummy){
		CanvasImage dummy=service.saveCanvasImage(canvasImageDummy);
		return new ResponseEntity<>(dummy,HttpStatus.CREATED);
	}
    @GetMapping("/getimages/{userId}")
    public ResponseEntity<List<CanvasImage>> getAllImages(@PathVariable Long userId){
    	
    	return  ResponseEntity.ok(service.getFileName(userId));
    	
    }
}
