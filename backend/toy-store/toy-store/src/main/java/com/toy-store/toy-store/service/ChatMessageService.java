package com.petshop.petshop.service;

import com.petshop.petshop.model.ChatMessage;
import com.petshop.petshop.model.Message;
import com.petshop.petshop.model.User;

import java.util.List;

public interface ChatMessageService {
    List<Message> getMessagesByUser(User user);

    void save(ChatMessage chatMessage);

    ChatMessage findChatByRoomId(String roomId, Long senderId);

    void saveMessage(ChatMessage chatMessage, String newMessage);

}
