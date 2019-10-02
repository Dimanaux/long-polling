package com.example.chats.messages;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
