package com.diagrammingtool.app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.diagrammingtool.app.model.CanvasImage;
import com.diagrammingtool.app.repository.CanvasImageRepository;
import com.diagrammingtool.app.service.CanvasImageService;

public class CanvasImageServiceTest {

    @Mock
    private CanvasImageRepository canvasImageRepository;

    @InjectMocks
    private CanvasImageService canvasImageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveCanvasImage() {
        // Mock data
        CanvasImage canvasImage = new CanvasImage();
        canvasImage.setId(1L);
        canvasImage.setImageData("mockedImageData".getBytes()); // Convert to byte[]

        // Mock repository behavior
        when(canvasImageRepository.save(canvasImage)).thenReturn(canvasImage);

        // Call the service method
        CanvasImage savedCanvasImage = canvasImageService.saveCanvasImage(canvasImage);

        // Verify
        assertThat(savedCanvasImage).isNotNull();
        assertThat(savedCanvasImage.getId()).isEqualTo(1L);
        assertThat(savedCanvasImage.getImageData()).isEqualTo("mockedImageData".getBytes()); // Convert to byte[]
    }

    @Test
    public void testGetCanvasImagesByUserId() {
        // Mock data
        Long userId = 1L;
        List<CanvasImage> mockedCanvasImages = new ArrayList<>();
        mockedCanvasImages.add(new CanvasImage());
        mockedCanvasImages.add(new CanvasImage());

        // Mock repository behavior
        when(canvasImageRepository.findByUserId(userId)).thenReturn(mockedCanvasImages);

        // Call the service method
        List<CanvasImage> canvasImages = canvasImageService.getCanvasImagesByUserId(userId);

        // Verify
        assertThat(canvasImages).isNotNull();
        assertThat(canvasImages.size()).isEqualTo(2);
    }

    @Test
    public void testGetAllDiagrams() {
        // Mock data
        List<CanvasImage> mockedCanvasImages = new ArrayList<>();
        mockedCanvasImages.add(new CanvasImage());
        mockedCanvasImages.add(new CanvasImage());

        // Mock repository behavior
        when(canvasImageRepository.findAll()).thenReturn(mockedCanvasImages);

        // Call the service method
        List<CanvasImage> canvasImages = canvasImageService.getAllDiagrams();

        // Verify
        assertThat(canvasImages).isNotNull();
        assertThat(canvasImages.size()).isEqualTo(2);
    }

    @Test
    public void testUpdateCanvasImage() {
        // Mock data
        Long id = 1L;
        CanvasImage existingCanvasImage = new CanvasImage();
        existingCanvasImage.setId(id);
        existingCanvasImage.setImageData("existingImageData".getBytes()); // Convert to byte[]

        CanvasImage updatedCanvasImage = new CanvasImage();
        updatedCanvasImage.setId(id);
        updatedCanvasImage.setImageData("updatedImageData".getBytes()); // Convert to byte[]

        // Mock repository behavior
        when(canvasImageRepository.findById(id)).thenReturn(Optional.of(existingCanvasImage));
        when(canvasImageRepository.save(existingCanvasImage)).thenReturn(updatedCanvasImage);

        // Call the service method
        CanvasImage result = canvasImageService.updateCanvasImage(id, updatedCanvasImage);

        // Verify
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getImageData()).isEqualTo("updatedImageData".getBytes()); // Convert to byte[]
    }

    @Test
    public void testUpdateCanvasImageNotFound() {
        // Mock data
        Long id = 1L;
        CanvasImage updatedCanvasImage = new CanvasImage();
        updatedCanvasImage.setId(id);
        updatedCanvasImage.setImageData("updatedImageData".getBytes()); // Convert to byte[]

        // Mock repository behavior (return empty Optional)
        when(canvasImageRepository.findById(id)).thenReturn(Optional.empty());

        // Call the service method and expect an exception
        assertThrows(RuntimeException.class, () -> {
            canvasImageService.updateCanvasImage(id, updatedCanvasImage);
        });
    }
}
