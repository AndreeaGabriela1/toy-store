package com.petshop.petshop.service.impl;

import com.petshop.petshop.model.ChatMessage;
import com.petshop.petshop.model.Message;
import com.petshop.petshop.model.User;
import com.petshop.petshop.repository.ChatMessageRepository;
import com.petshop.petshop.service.ChatMessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    ChatMessageRepository chatMessageRepository;

    @Override
    public List<Message> getMessagesByUser(User user) {
        if(user == null){
            return null;
        }

        ChatMessage chatMessage = user.getSenderChatMessage();

        if(chatMessage == null){
            return null;
        }

        List<Message> messages = chatMessage.getMessages();

        return  messages;
    }

    @Override
    public void save(ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);
    }

    @Override
    public ChatMessage findChatByRoomId(String roomId, Long senderId) {
        Optional<ChatMessage> chatMessageOpt = chatMessageRepository.findByRoomIdAndSender_Id(roomId, senderId);
        if(chatMessageOpt.isEmpty()){
            return null;
        }
        return chatMessageOpt.get();
    }

    @Override
    public void saveMessage(ChatMessage chatMessage, String newMessage) {

        List<Message> messages = chatMessage.getMessages();
        if(messages == null){
            messages = new ArrayList<>();
        }

        Message message = new Message();
        message.setChat_message(chatMessage);
        message.setMessage_content(newMessage);
        messages.add(message);

        chatMessage.setMessages(messages);
        chatMessageRepository.save(chatMessage);
        System.out.println("chatService: SAVED! NEW MESSAGE IS IN CHAT:" + newMessage);
    }
}
