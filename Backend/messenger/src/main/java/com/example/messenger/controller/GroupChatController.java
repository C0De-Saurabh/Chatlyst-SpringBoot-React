package com.example.messenger.controller;

import com.example.messenger.dto.MessageRequest;
import com.example.messenger.dto.MessageResponse;
import com.example.messenger.service.GroupChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/groupchat")
public class GroupChatController {

    @Autowired
    private GroupChatService groupChatService;

    @MessageMapping("/sendGroupMessage")
    @SendTo("/topic/groupMessages")
    public MessageResponse sendGroupMessage(MessageRequest message) {
        String processedMessage = groupChatService.processGroupMessage(message);
        return new MessageResponse(message.getSender(), processedMessage);
    }
}