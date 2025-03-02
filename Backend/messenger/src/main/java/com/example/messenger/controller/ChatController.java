package com.example.messenger.controller;

import com.example.messenger.entity.Message;
import com.example.messenger.entity.User;
import com.example.messenger.entity.Conversation;
import com.example.messenger.repository.ConversationRepository;
import com.example.messenger.repository.MessageRepository;
import com.example.messenger.repository.UserRepository;
import com.example.messenger.websocket.MessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Controller
public class ChatController {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;

    public ChatController(MessageRepository messageRepository, UserRepository userRepository, ConversationRepository conversationRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.conversationRepository = conversationRepository;
    }

    @MessageMapping("chat.sendMessage") // Route for sending messages
    public Mono<Void> sendMessage(MessageDTO messageDTO, RSocketRequester requester) {
        if (messageDTO == null || messageDTO.getSenderId() == null || messageDTO.getConversationId() == null || messageDTO.getContent() == null) {
            return Mono.error(new IllegalArgumentException("Invalid message data"));
        }

        // Fetch sender and conversation using blocking repositories
        return Mono.fromCallable(() -> userRepository.findById(messageDTO.getSenderId()).orElse(null))
                .zipWith(Mono.fromCallable(() -> conversationRepository.findById(messageDTO.getConversationId()).orElse(null)))
                .flatMap(tuple -> {
                    User sender = tuple.getT1();
                    Conversation conversation = tuple.getT2();

                    if (sender == null || conversation == null) {
                        return Mono.error(new IllegalArgumentException("Sender or conversation not found"));
                    }

                    // Save message to database
                    Message message = new Message();
                    message.setSender(sender);
                    message.setConversation(conversation);
                    message.setContent(messageDTO.getContent());
                    message.setSentAt(LocalDateTime.now());
                    message.setRead(false);

                    return Mono.fromRunnable(() -> messageRepository.save(message))
                            .doOnSuccess(savedMessage -> {
                                // Broadcast message to users in the conversation
                                requester.route("conversation." + messageDTO.getConversationId())
                                        .data(messageDTO)
                                        .send()
                                        .subscribe();
                            })
                            .then();
                });
    }
}