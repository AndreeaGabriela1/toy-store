package com.petshop.petshop.model;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "CHAT_MESSAGES")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) // Ensure roomId is unique
    private String roomId;


    @OneToOne
    @JoinColumn(name = "sender_id")
    User sender;


    @OneToMany(mappedBy = "chat_message", cascade = CascadeType.ALL)
    private List<Message> messages;



}
