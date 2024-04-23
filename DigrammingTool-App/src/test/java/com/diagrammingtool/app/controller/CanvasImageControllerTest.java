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
        canvasImage.setImageByte(new byte[]{1, 2, 3});
        canvasImage.setImageJson("nhklhuygyyufdrd");
        canvasImage.setImageName("canvas1");

        when(canvasImageService.saveCanvasImage(any(CanvasImage.class))).thenReturn(canvasImage);

        ResponseEntity<CanvasImage> responseEntity = canvasImageController.saveImage(canvasImage);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(canvasImage, responseEntity.getBody());
    }
    
    
    @Test
    public void testGetAllImages() {
        List<CanvasImage> dummyImageList = new ArrayList<>();
        CanvasImage dummyCanvasImage1 = new CanvasImage();
        dummyCanvasImage1.setId(1L);
        dummyCanvasImage1.setImageName("DummyImage1");
        dummyImageList.add(dummyCanvasImage1);
        CanvasImage dummyCanvasImage2 = new CanvasImage();
        dummyCanvasImage2.setId(2L);
        dummyCanvasImage2.setImageName("DummyImage2");
        dummyImageList.add(dummyCanvasImage2);

        when(canvasImageService.getFileName(anyLong())).thenReturn(dummyImageList);

        ResponseEntity<List<CanvasImage>> response = canvasImageController.getAllImages(123L);

        verify(canvasImageService).getFileName(123L);

        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
        assert response.getBody().size() == dummyImageList.size();
        assert response.getBody().get(0).getId().equals(dummyCanvasImage1.getId());
        assert response.getBody().get(0).getImageName().equals(dummyCanvasImage1.getImageName());
        assert response.getBody().get(1).getId().equals(dummyCanvasImage2.getId());
        assert response.getBody().get(1).getImageName().equals(dummyCanvasImage2.getImageName());
    }

}
