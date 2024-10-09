package com.petshop.petshop.mappper;

import com.petshop.petshop.mappper.dto.MinimalUserDto;
import com.petshop.petshop.mappper.dto.UserDto;
import com.petshop.petshop.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class UserMapper {

    private final RoleMapper roleMapper;
    private final ProductMapper productMapper;

    public UserDto userEntityToDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email_address(user.getEmail_address())
                .roles(roleMapper.roleListEntityToDto(user.getRoles()))
               // .cartProducts(productMapper.productListEntitytoDto(user.getCartProducts()))
                //.favouriteProducts(productMapper.productListEntitytoDto(user.getFavouriteProducts()))
                .token(user.getToken())
                .imageurl(user.getImageurl())
                .loginTime(user.getLoginTime())
                .logoutTime(user.getLogoutTime())
                .build();
    }

    public List<UserDto> userListEntityToDto(List<User> users){
        return users.stream()
                .map(user -> userEntityToDto(user))
                .toList();
    }

    public User userDtoToEntity(UserDto userDto, String password){

        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .password(password)
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email_address(userDto.getEmail_address())
                .roles(roleMapper.roleListDtoToEntity(userDto.getRoles()))
                .cart_products(productMapper.productListDtoToEntity(userDto.getCart_products()))
                .favorite(productMapper.productListDtoToEntity(userDto.getFavorite()))
                .token(userDto.getToken())
                .imageurl(userDto.getImageurl())
                .loginTime(userDto.getLoginTime())
                .logoutTime(userDto.getLogoutTime())
                .build();
    }

    public List<User> userListDtoToEntity(List<UserDto> userDtos, String password){
        return userDtos.stream()
                .map(userDto -> userDtoToEntity(userDto, password))
                .toList();
    }

/*
    public abstract UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    public abstract User signUpToUser(SignUpDto signUpDto);
*/

    public User signUpToUser(MinimalUserDto minimalUserDto) {
        if (minimalUserDto == null) {
            return null;
        }
        User user = new User();
        user.setUsername(minimalUserDto.getUsername());
        user.setPassword(minimalUserDto.getPassword());
        user.setFirstName(minimalUserDto.getFirstName());
        user.setLastName(minimalUserDto.getLastName());
        user.setEmail_address(minimalUserDto.getEmail_address());
        return user;
    }



}