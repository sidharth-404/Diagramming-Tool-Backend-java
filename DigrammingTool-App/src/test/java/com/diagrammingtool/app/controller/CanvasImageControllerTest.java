package com.diagrammingtool.app.controller;

import com.diagrammingtool.app.model.CanvasImage;
import com.diagrammingtool.app.service.CanvasImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CanvasImageControllerTest {

    @Mock
    private CanvasImageService canvasImageService;

    @InjectMocks
    private CanvasImageController canvasImageController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveCanvasImage() {
        CanvasImage canvasImage = new CanvasImage();
        canvasImage.setId(1L);
        canvasImage.setImageData(new byte[]{1, 2, 3});

        when(canvasImageService.saveCanvasImage(any(CanvasImage.class))).thenReturn(canvasImage);

        ResponseEntity<CanvasImage> responseEntity = canvasImageController.saveCanvasImage(canvasImage);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(canvasImage, responseEntity.getBody());
    }

    @Test
    public void testGetCanvasImagesByUserId() {
        Long userId = 1L;
        List<CanvasImage> canvasImages = new ArrayList<>();
        canvasImages.add(new CanvasImage(1L, new byte[]{1, 2, 3}, null));
        canvasImages.add(new CanvasImage(2L, new byte[]{4, 5, 6}, null));

        when(canvasImageService.getCanvasImagesByUserId(userId)).thenReturn(canvasImages);

        ResponseEntity<List<CanvasImage>> responseEntity = canvasImageController.getCanvasImagesByUserId(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(canvasImages, responseEntity.getBody());
    }

    @Test
    public void testGetAllUser() {
        List<CanvasImage> canvasImages = new ArrayList<>();
        canvasImages.add(new CanvasImage(1L, new byte[]{1, 2, 3}, null));
        canvasImages.add(new CanvasImage(2L, new byte[]{4, 5, 6}, null));

        when(canvasImageService.getAllDiagrams()).thenReturn(canvasImages);

        ResponseEntity<List<CanvasImage>> responseEntity = canvasImageController.getAllUser();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(canvasImages, responseEntity.getBody());
    }

    @Test
    public void testUpdateCanvasImage() {
        Long id = 1L;
        CanvasImage updatedCanvasImage = new CanvasImage(1L, new byte[]{7, 8, 9}, null);
        CanvasImage originalCanvasImage = new CanvasImage(1L, new byte[]{1, 2, 3}, null);

        when(canvasImageService.updateCanvasImage(id, updatedCanvasImage)).thenReturn(updatedCanvasImage);

        ResponseEntity<CanvasImage> responseEntity = canvasImageController.updateCanvasImage(id, updatedCanvasImage);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedCanvasImage, responseEntity.getBody());
    }
}
