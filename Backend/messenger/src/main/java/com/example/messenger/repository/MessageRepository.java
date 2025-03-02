package com.example.messenger.repository;

import com.example.messenger.entity.Message;
import com.example.messenger.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByConversationOrderBySentAtAsc(Conversation conversation);
}
