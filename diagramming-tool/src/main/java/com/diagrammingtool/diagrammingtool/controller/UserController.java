package com.diagrammingtool.diagrammingtool.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diagrammingtool.diagrammingtool.model.CanvasImage;
import com.diagrammingtool.diagrammingtool.model.UserRegistration;
import com.diagrammingtool.diagrammingtool.service.CanvasImageService;
import com.diagrammingtool.diagrammingtool.service.UserRegistrationService;

@RequestMapping("/user")
@RestController
@CrossOrigin("*")
public class UserController {
	
    @Autowired
	private UserRegistrationService userService;
    
    @Autowired
	private CanvasImageService canvasImageService;

	

	@PatchMapping("/changePassword")
	public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> requestBody)
	{
		try {
		 String userEmail = requestBody.get("userEmail");
         String newPassword = requestBody.get("newPassword");
         String currentPassword=requestBody.get("currentPassword");         

         if (!userService.verifyUserPassword(userEmail, currentPassword)) {
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect current password");
         }
        
         userService.changeUserPassword(userEmail, newPassword);
         return ResponseEntity.status(HttpStatus.OK).body("updated successfully");
		}
		catch(IllegalArgumentException e)
		{
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	@PostMapping("/saveimage")
	public ResponseEntity<CanvasImage> saveImage(@RequestBody CanvasImage canvasImage){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserRegistration currentUser = (UserRegistration) authentication.getPrincipal();
		canvasImage.setUser(currentUser);
		CanvasImage image=canvasImageService.saveCanvasImage(canvasImage);
		return new ResponseEntity<>(image,HttpStatus.CREATED);
	}
	
	@GetMapping("/getimages")
    public ResponseEntity<List<CanvasImage>> getAllImages(){
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserRegistration currentUser = (UserRegistration) authentication.getPrincipal();
    	return  ResponseEntity.ok(canvasImageService.getFileName(currentUser.getUserId()));
    	
    }


	
	


}
