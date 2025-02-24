package com.example.messenger.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "participants")
@Getter @Setter
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    private User user;

    @JoinColumn(name = "conversation_id", nullable = false)
    @ManyToOne
    private Conversation conversation;

    private LocalDateTime joinedAt;

    private boolean isAdmin;


}
