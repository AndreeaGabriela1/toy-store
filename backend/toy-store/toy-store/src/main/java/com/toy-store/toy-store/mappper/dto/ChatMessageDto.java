package com.petshop.petshop.mappper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class ChatMessageDto {
    String message;
    String user;
    String roomId;
}
