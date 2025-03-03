package com.example.messenger.service;


import com.example.messenger.dto.MessageRequest;
import com.example.messenger.model.GroupChat;
import com.example.messenger.repository.GroupChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class GroupChatService {

    @Autowired
    private GroupChatRepository groupChatRepository;

    public String processGroupMessage(MessageRequest message) {
        // Save the group chat message to the database
        GroupChat groupChat = new GroupChat();
        groupChat.setGroupName("Group-Chat"+message.getSender());
        groupChat.setSender(message.getSender());
        groupChat.setContent(message.getContent());
        groupChat.setTimestamp(LocalDateTime.now());

        groupChatRepository.save(groupChat);

        return "Processed group message: " + message.getContent();
    }
}