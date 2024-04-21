package com.takoikatakotako.app.repository;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "chat_room_user")
@Getter
@Setter
public class ChatRoomUser {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_room_id", nullable = false)
    private Long chatRoomID;

    @Column(name = "user_id", nullable = false)
    private Long userID;
}
