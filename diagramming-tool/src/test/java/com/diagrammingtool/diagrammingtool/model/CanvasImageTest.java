package com.diagrammingtool.diagrammingtool.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CanvasImageTest {

	 @Test
	    public void testConstructorAndGetters() {
	        
	        UserRegistration user = new UserRegistration();
	        user.setUserId(1L);
	        user.setUserEmail("testUser");

	        
	        CanvasImage canvasImage = new CanvasImage(1L, "testImage", "{\"key\":\"value\"}", new byte[]{}, user);

	        
	        assertEquals(1L, canvasImage.getId());
	        assertEquals("testImage", canvasImage.getImageName());
	        assertEquals("{\"key\":\"value\"}", canvasImage.getImageJson());
	        assertArrayEquals(new byte[]{}, canvasImage.getImageByte());
	        assertEquals(user, canvasImage.getUser());
	    }

	    @Test
	    public void testSetters() {
	        
	        UserRegistration user = new UserRegistration();
	        user.setUserId(1L);
	        user.setUserEmail("testUser");

	      
	        CanvasImage canvasImage = new CanvasImage();


	        canvasImage.setId(1L);
	        canvasImage.setImageName("testImage");
	        canvasImage.setImageJson("{\"key\":\"value\"}");
	        canvasImage.setImageByte(new byte[]{});
	        canvasImage.setUser(user);

	        
	        assertEquals(1L, canvasImage.getId());
	        assertEquals("testImage", canvasImage.getImageName());
	        assertEquals("{\"key\":\"value\"}", canvasImage.getImageJson());
	        assertArrayEquals(new byte[]{}, canvasImage.getImageByte());
	        assertEquals(user, canvasImage.getUser());
	    }

}
