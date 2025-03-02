package com.example.messenger.repository;

import com.example.messenger.entity.CallLog;
import com.example.messenger.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CallLogRepository extends JpaRepository<CallLog, Long> {
    List<CallLog> findByCallerOrReceiver(User caller, User receiver);
}
