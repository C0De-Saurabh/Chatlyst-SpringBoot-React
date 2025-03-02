package com.example.messenger.websocket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {
    private Long senderId;
    private Long conversationId;
    private String content;
}
