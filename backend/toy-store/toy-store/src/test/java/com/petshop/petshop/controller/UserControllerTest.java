package com.petshop.petshop.controller;

import com.petshop.petshop.mappper.dto.*;
import com.petshop.petshop.model.*;
import com.petshop.petshop.service.ChatMessageService;
import com.petshop.petshop.service.ProductService;
import com.petshop.petshop.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testGetAllUsers() {
        List<UserDto> userDtos = new ArrayList<>();
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUsername("testUser");
        userDtos.add(userDto);

        when(userService.getAllUserDtos()).thenReturn(userDtos);

        ResponseEntity<List<UserDto>> responseEntity = userController.getAllUsers();

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals("testUser", responseEntity.getBody().get(0).getUsername());
    }

    @Test
    void testDeleteUser() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);

        when(userService.findUserByID(1L)).thenReturn(new User());

        ResponseEntity<UserDto> responseEntity = userController.deleteUser(userDto);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void testEditUser() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);

        when(userService.findUserByID(1L)).thenReturn(new User());
        when(userService.updateFromUserDto(userDto, null, null)).thenReturn(userDto);

        ResponseEntity<UserDto> responseEntity = userController.editUser(userDto);

        assertNotNull(responseEntity);
        assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
    void testAddToCart() {
        CartDto cart = new CartDto(1L, "testUser");

        when(productService.getProductById(1L)).thenReturn(new Product());
        when(userService.findByUsername("testUser")).thenReturn(Optional.of(new User()));
        when(userService.addCartProducts(any(User.class), anyLong())).thenReturn(new UserDto());

        ResponseEntity<UserDto> responseEntity = userController.addToCart(cart);

        assertNotNull(responseEntity);
        assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
    void testAddToFavs() {
        CartDto fav = new CartDto(1L, "testUser");


        when(productService.getProductById(1L)).thenReturn(new Product());
        when(userService.findByUsername("testUser")).thenReturn(Optional.of(new User()));
        when(userService.addFavProducts(any(User.class), anyLong())).thenReturn(new UserDto());

        ResponseEntity<UserDto> responseEntity = userController.addToFavs(fav);

        assertNotNull(responseEntity);
        assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
    void testRemovedFromFavs() {
        CartDto fav = new CartDto(1L, "testUser");

        when(productService.getProductById(1L)).thenReturn(new Product());
        when(userService.findByUsername("testUser")).thenReturn(Optional.of(new User()));
        when(userService.removeFavProducts(any(User.class), anyLong())).thenReturn(new UserDto());

        ResponseEntity<UserDto> responseEntity = userController.removedFromFavs(fav);

        assertNotNull(responseEntity);
        assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
    void testGetCartProducts() {
        String username = "\"testUser\"";

        List<ProductDto> productDtos = new ArrayList<>();
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("testProduct");
        productDtos.add(productDto);

        when(userService.fetchCartProducts("testUser")).thenReturn(productDtos);

        ResponseEntity<List<ProductDto>> responseEntity = userController.getCartProducts(username);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals("testProduct", responseEntity.getBody().get(0).getName());
    }

}