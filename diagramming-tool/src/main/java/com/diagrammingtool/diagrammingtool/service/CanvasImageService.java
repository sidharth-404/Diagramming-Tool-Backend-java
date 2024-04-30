package com.diagrammingtool.diagrammingtool.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diagrammingtool.diagrammingtool.model.CanvasImage;
import com.diagrammingtool.diagrammingtool.repository.CanvasImageRepository;

@Service
public class CanvasImageService {
	
	@Autowired
	public CanvasImageRepository canvasRepository;
	
	
	 public CanvasImage saveCanvasImage(CanvasImage canvasImage) {
	      return canvasRepository.save(canvasImage);
	  }
	  
	  public List<CanvasImage> getFileName(Long UserId){
		  List<CanvasImage> images=canvasRepository.findAll();
		  List<CanvasImage> fileName=new ArrayList<>();
		  for(CanvasImage c : images) {
			  if(c.getUser().getUserId()==UserId) {
				  
				  fileName.add(c);
				  
			  }
			 
		  }
		  return fileName;
		  
	  }

}
