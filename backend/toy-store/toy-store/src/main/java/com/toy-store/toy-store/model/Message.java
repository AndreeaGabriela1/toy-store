package com.petshop.petshop.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "MESSAGE")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message_content")
    private String message_content;

    @ManyToOne
    @JoinColumn(name = "chat_message")
    private ChatMessage chat_message;



}
