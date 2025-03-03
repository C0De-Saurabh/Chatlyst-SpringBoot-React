package com.example.messenger.service;

import com.example.messenger.dto.MessageRequest;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    public String processMessage(MessageRequest message) {
        return "Processed private message: " + message.getContent();
    }
}