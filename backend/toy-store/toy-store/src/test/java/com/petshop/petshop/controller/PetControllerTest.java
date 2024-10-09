package com.petshop.petshop.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class PetControllerTest {

    @Mock
    private PetService petService;

    @InjectMocks
    private PetController petController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        List<PetDto> petDtos = new ArrayList<>();
        PetDto petDto = new PetDto(1L, "testPet", 2, "image-url", 5.5f, null, null);
        petDtos.add(petDto);

        when(petService.getAllPetDtos()).thenReturn(petDtos);

        ResponseEntity<List<PetDto>> responseEntity = petController.getAllProducts();

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals("testPet", responseEntity.getBody().get(0).name());
    }

    @Test
    void testCreateProduct() {
        PetDto petDto = new PetDto(1L, "testPet", 2, "image-url", 5.5f, null, null);

        when(petService.createPet(petDto)).thenReturn(petDto);

        ResponseEntity<PetDto> responseEntity = petController.createProduct(petDto);

        assertNotNull(responseEntity);
        assertEquals(201, responseEntity.getStatusCodeValue());
        assertEquals("testPet", responseEntity.getBody().name());
    }

    @Test
    void testDeleteProduct() {
        PetDto petDto = new PetDto(1L, "testPet", 2, "image-url", 5.5f, null, null);

        when(petService.getPetById(1L)).thenReturn(new Pet());

        ResponseEntity<PetDto> responseEntity = petController.deleteProduct(petDto);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void testEditProduct() {
        PetDto petDto = new PetDto(1L, "testPet", 2, "image-url", 5.5f, null, null);

        when(petService.updatePetDto(petDto)).thenReturn(petDto);

        ResponseEntity<PetDto> responseEntity = petController.editProduct(petDto);

        assertNotNull(responseEntity);
        assertEquals(201, responseEntity.getStatusCodeValue());
    }
}
