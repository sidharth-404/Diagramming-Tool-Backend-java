package com.diagrammingtool.app.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.diagrammingtool.app.model.CanvasImage;
import com.diagrammingtool.app.model.UserRegistration;
import com.diagrammingtool.app.repository.CanvasImageRepository;

public class CanvasImageServiceTest {

    private CanvasImageService canvasImageService;
    private CanvasImageRepository canvasImageRepository;

    @BeforeEach
    public void setUp() {
        canvasImageRepository = mock(CanvasImageRepository.class);
        canvasImageService = new CanvasImageService();
        canvasImageService.repo = canvasImageRepository;
    }

    @Test
    public void testSaveCanvasImage() {
        // Arrange
        CanvasImage canvasImageToSave = new CanvasImage();
        CanvasImage savedCanvasImage = new CanvasImage();
        when(canvasImageRepository.save(canvasImageToSave)).thenReturn(savedCanvasImage);

        // Act
        CanvasImage result = canvasImageService.saveCanvasImage(canvasImageToSave);

        // Assert
        assertEquals(savedCanvasImage, result);
        verify(canvasImageRepository, times(1)).save(canvasImageToSave);
    }

    @Test
    public void testGetFileName() {
   
    	UserRegistration userWithId=new UserRegistration();
        
        CanvasImage canvasImage1 = new CanvasImage();
        userWithId.setUserId(1L);
      canvasImage1.setUser(userWithId);
        CanvasImage canvasImage2 = new CanvasImage();
        canvasImage2.setUser(new UserRegistration("sidharth","pk","sidharth@gmail.com","Sidhu@25"));
        List<CanvasImage> dummyData = new ArrayList<>();
        dummyData.add(canvasImage1);
        dummyData.add(canvasImage2);
        when(canvasImageRepository.findAll()).thenReturn(dummyData);

        List<CanvasImage> result = canvasImageService.getFileName(1L);

        assertEquals(1, result.size());
        assertEquals(canvasImage1, result.get(0));
    }
}
