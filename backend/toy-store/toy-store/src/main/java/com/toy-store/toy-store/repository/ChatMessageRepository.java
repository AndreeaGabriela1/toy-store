package com.petshop.petshop.repository;

import com.petshop.petshop.model.ChatMessage;
import com.petshop.petshop.model.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessageRepository extends CrudRepository<ChatMessage, Long> {

    List<ChatMessage> findAll();
    Optional<ChatMessage> findByRoomIdAndSender_Id(String roomId, Long senderId);

}
