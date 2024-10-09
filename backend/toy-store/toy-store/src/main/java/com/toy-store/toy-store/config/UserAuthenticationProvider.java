package com.petshop.petshop.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.petshop.petshop.mappper.RoleMapper;
import com.petshop.petshop.mappper.dto.UserDto;
import com.petshop.petshop.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class UserAuthenticationProvider {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    private final UserService userService;

    private final RoleMapper roleMapper;

    @PostConstruct
    protected void init() {
        // this is to avoid having the raw secret key available in the JVM
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserDto user) {

        System.out.println("CREATE_TOKEN: I AM HERE!");

        System.out.println(user.getUsername());
        System.out.println("roles--> " + user.getRoles());
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1 hour



        System.out.println("testing roles in authentication provider - token:creation :: " + user.getRoles().get(0));


        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("firstName", user.getFirstName())
                .withClaim("lastName", user.getLastName())
                .withClaim("roles",user.getRoles())
                .sign(algorithm);
    }

    public Authentication validateToken(String token) {

        if(token == null){
            System.out.println("validate Token: NOT AUTHENTICATED!");
            return null;
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm)
                .build();

        DecodedJWT decoded = verifier.verify(token);

        System.out.println("User authentication provider: VALIDATE_TOKEN: -- I AM HERE!!!!!!!!!!!!!!!" + token);

        List<String> roles = decoded.getClaim("roles").asList(String.class);

        String firstName =  decoded.getClaim("firstName").asString();
        System.out.println("first name: " + firstName);

        System.out.println("my roles:");
        for(String s : roles){
            System.out.println("role: " + s);
        }
        System.out.println("end roles");

        UserDto user = UserDto.builder()
                .username(decoded.getSubject())
                .firstName(decoded.getClaim("firstName").asString())
                .lastName(decoded.getClaim("lastName").asString())
                .roles(roles)
                .build();

        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());


        System.out.println("(userAuthenticationProvider-validate token: Authorities:");
        for (SimpleGrantedAuthority authority : authorities) {
            System.out.println(authority.getAuthority());
        }
        System.out.println("end");

        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }

}