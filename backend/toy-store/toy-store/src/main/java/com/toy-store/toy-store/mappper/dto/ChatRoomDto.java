package com.petshop.petshop.mappper.dto;

import lombok.*;


@Builder
public record ChatRoomDto(String first_userId, String second_userId) { }
