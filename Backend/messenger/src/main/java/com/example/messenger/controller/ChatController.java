package com.example.messenger.controller;

import com.example.messenger.dto.MessageRequest;
import com.example.messenger.dto.MessageResponse;
import com.example.messenger.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public MessageResponse sendMessage(MessageRequest message) {
        String processedMessage = chatService.processMessage(message);
        return new MessageResponse(message.getSender(), processedMessage);
    }
}