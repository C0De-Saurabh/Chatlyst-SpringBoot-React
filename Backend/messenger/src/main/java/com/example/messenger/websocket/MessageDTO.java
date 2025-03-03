package com.example.messenger.websocket;

import com.example.messenger.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {
    private User sender;
    private Long conversationId;
    private String content;
}
