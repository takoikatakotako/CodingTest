package com.takoikatakotako.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    @Query(value = "select * from chat_message where deleted=false and chat_room_id=:chatRoomID order by created_at;", nativeQuery = true)
    List<ChatMessage> findByChatRoomID(@Param("chatRoomID") Long chatRoomID);
}
