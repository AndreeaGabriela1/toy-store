package com.petshop.petshop.controller;


import com.petshop.petshop.config.UserAuthenticationProvider;
import com.petshop.petshop.controller.validation.GlobalExceptionHandlerController;
import com.petshop.petshop.mappper.dto.CredentialsDto;
import com.petshop.petshop.mappper.dto.MinimalUserDto;
import com.petshop.petshop.mappper.dto.UserDto;
import com.petshop.petshop.model.User;
import com.petshop.petshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class HomeController extends GlobalExceptionHandlerController {

    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;


    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid CredentialsDto credentialsDto) {


        System.out.println("login homeController:" + credentialsDto.getUsername());
        UserDto userDto = userService.login(credentialsDto);
        if(userDto == null){
            return ResponseEntity.badRequest().build();
        }
        userDto.setToken(userAuthenticationProvider.createToken(userDto));

        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/loggedout")
    public ResponseEntity<Void> loggedout(@RequestBody  UserDto userDto) {

        if(userDto == null){
            System.out.println("$$$$$$$$$$$$$$$$$$ NO USER PROVIDED FOR LOGOUT!");
            return null;
        }

        System.out.println("$$$$$$$$$$$$$$$$$$$$$ LOGOUT homeController:" + userDto.getUsername());
        userDto.setToken(null);

        User user = userService.findUserByID(userDto.getId());

        if (user == null) {
            return ResponseEntity.badRequest().build();
        }


        userService.settingLogout(user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid MinimalUserDto user) {
        System.out.println("!!!!!!!!!!!!!! email" + user.getEmail_address());
        System.out.println("!!!!!!!!!!!!!! username" + user.getUsername());
        System.out.println("!!!!!!!!!!!!!! firstname" + user.getFirstName());
        System.out.println("!!!!!!!!!!!!!! lastname" + user.getLastName());
        System.out.println("!!!!!!!!!!!!!! password" + user.getPassword());
        UserDto createdUser = userService.register(user);
        createdUser.setToken(userAuthenticationProvider.createToken(createdUser));
        return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
    }

    @PostMapping("/logged")
    public ResponseEntity<UserDto> logged(@RequestBody(required = false) @Valid String username) {

        //todo -- get username from frontend without quotes
        String usernameTest = username.substring(1, username.length()-1);
        if (username == null) {
            return ResponseEntity.badRequest().build();
        }
        Optional<User> userOpt = userService.findByUsername(usernameTest);
        System.out.println(userOpt.toString());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        UserDto userDto = userService.createUserDto(userOpt.get());
        return ResponseEntity.ok().body(userDto);
    }

    @PostMapping("/login-activity")
    public ResponseEntity<List<UserDto>> loginActivity(@RequestBody @Valid String startString)  {

        //{"startString":"2025-04-04T22:20:00.000Z"}
        String startGood = startString.substring(1, startString.length()-1);

        System.out.println("start:"+startGood);
        System.out.println("aaaa:"+startString);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime dateTime = LocalDateTime.parse(startGood, formatter);

        LocalDateTime end = LocalDateTime.now();
        System.out.println("local dates:\n"+dateTime.toString()+"\n"+end.toString());
        List<UserDto> userDtos = userService.loginBetween(dateTime, end);

        System.out.println(userDtos);

        if(userDtos == null)
            System.out.println("e null");
        else{
            System.out.println("@@@@@@@ login activity");

            for(UserDto u:userDtos){
                System.out.println("@@ "+u.getUsername());
            }
        }

        return ResponseEntity.ok(userDtos);
    }


    @PostMapping("/forgot-password")
    public ResponseEntity<UserDto> forgotPassword(@RequestBody MinimalUserDto minimalUserDto) {

        System.out.println("HELLOO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        UserDto saved = userService.forgotPassword(minimalUserDto);
        return ResponseEntity.ok(saved);
    }

}