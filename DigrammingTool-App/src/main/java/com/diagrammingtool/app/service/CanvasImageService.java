package com.diagrammingtool.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diagrammingtool.app.model.CanvasImage;
import com.diagrammingtool.app.repository.CanvasImageRepository;

@Service
public class CanvasImageService {
  @Autowired 
  CanvasImageRepository repo;
  
  public CanvasImage saveCanvasImage(CanvasImage canvasImage) {
      return repo.save(canvasImage);
  }
  
  public List<CanvasImage> getFileName(Long UserId){
	  List<CanvasImage> dummy=repo.findAll();
	  List<CanvasImage> fileName=new ArrayList<>();
	  for(CanvasImage c : dummy) {
		  if(c.getUser().getUserId()==UserId) {
			  
			  fileName.add(c);
			  
		  }
		 
	  }
	  return fileName;
	  
  }
  

}
