package com.petshop.petshop.service;

import com.petshop.petshop.mappper.dto.*;
import com.petshop.petshop.model.RegistrationRequest;
import com.petshop.petshop.model.Role;
import com.petshop.petshop.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDto registerUser(RegistrationRequest registrationRequest);

    UserDto getUserDtoById(Long id);

    User findUserByID(Long id);

    List<UserDto> getAllUserDtos();

    Optional<User> findByUsername(String username);

    UserDto createUserDto(User user);

    UserDto updateUserDto(User user);
    UserDto updateFromUserDto(UserDto userDto, String password, List<Role> roles);

    void deleteUser(User user);

    UserDto login(CredentialsDto credentialsDto);

    UserDto register(MinimalUserDto user);

    void assignRoleToUser(User user, Long roleId);

    UserDto addCartProducts(User user, Long productId);

    UserDto addFavProducts(User user, Long productId);
    UserDto removeFavProducts(User user, Long productId);

    List<ProductDto> fetchCartProducts(String username);
    List<ProductDto> fetchFavProducts(String username);

    ReceiptDto buyProducts(String username);

    List<UserDto> loginBetween(LocalDateTime start, LocalDateTime stop);

    void settingLogout(User user);

    UserDto forgotPassword(MinimalUserDto minimalUserDto);


}