package com.example.messenger.dto;

import lombok.Data;

@Data
public class MessageRequest {
    private String sender;
    private String content;
}