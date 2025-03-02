package com.example.messenger.repository;

import com.example.messenger.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    // No need to declare findById(Long id) because it's already provided by JpaRepository
}