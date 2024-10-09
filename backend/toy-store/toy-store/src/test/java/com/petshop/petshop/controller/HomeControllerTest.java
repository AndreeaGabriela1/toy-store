package com.petshop.petshop.controller;

import com.petshop.petshop.config.UserAuthenticationProvider;
import com.petshop.petshop.controller.HomeController;
import com.petshop.petshop.mappper.dto.CredentialsDto;
import com.petshop.petshop.mappper.dto.MinimalUserDto;
import com.petshop.petshop.mappper.dto.UserDto;
import com.petshop.petshop.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class HomeControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserAuthenticationProvider userAuthenticationProvider;

    private HomeController homeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        homeController = new HomeController(userService, userAuthenticationProvider);
    }


    @Test
    void register_ValidUserData_ReturnsUserDtoWithToken() {

        MinimalUserDto minimalUserDto = new MinimalUserDto();
        minimalUserDto.setUsername("testUser");
        minimalUserDto.setPassword("testPassword");
        minimalUserDto.setFirstName("John");
        minimalUserDto.setLastName("Doe");

        UserDto expectedUserDto = new UserDto();
        expectedUserDto.setUsername("testUser");
        expectedUserDto.setToken("testToken");

        when(userService.register(any())).thenReturn(expectedUserDto);
        when(userAuthenticationProvider.createToken(any())).thenReturn("testToken");

        ResponseEntity<UserDto> responseEntity = homeController.register(minimalUserDto);

        assertNotNull(responseEntity);
        assertEquals(expectedUserDto, responseEntity.getBody());
        assertEquals(201, responseEntity.getStatusCodeValue());
        assertEquals(URI.create("/users/" + expectedUserDto.getId()), responseEntity.getHeaders().getLocation());
    }

    @Test
    public void testLogin() {

        CredentialsDto credentialsDto = new CredentialsDto();
        credentialsDto.setUsername("testUser");
        credentialsDto.setPassword("testPassword1.".toCharArray());

        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setToken("testToken");

        ResponseEntity<UserDto> expectedResponse = ResponseEntity.ok(userDto);

        when(userService.login(credentialsDto)).thenReturn(userDto);
        when(userAuthenticationProvider.createToken(userDto)).thenReturn("testToken");

        ResponseEntity<UserDto> actualResponse = homeController.login(credentialsDto);
        verify(userService).login(credentialsDto);

        verify(userAuthenticationProvider).createToken(userDto);
        assertEquals(expectedResponse, actualResponse);
    }
}
