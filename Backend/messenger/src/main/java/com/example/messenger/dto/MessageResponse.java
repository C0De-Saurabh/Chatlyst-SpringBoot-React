package com.example.messenger.dto;


import lombok.Data;

@Data
public class MessageResponse {
    private String sender;
    private String content;

    public MessageResponse(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }
}