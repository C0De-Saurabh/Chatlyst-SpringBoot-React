package com.example.messenger.repository;

import com.example.messenger.entity.Participant;
import com.example.messenger.entity.Conversation;
import com.example.messenger.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    List<Participant> findByConversation(Conversation conversation);
    Optional<Participant> findByUserAndConversation(User user, Conversation conversation);
}
