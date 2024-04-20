package com.takoikatakotako.app.controller;

import com.takoikatakotako.app.entity.ChatRoomCreateRequestEntity;
import com.takoikatakotako.app.entity.ChatRoomResponseEntity;
import com.takoikatakotako.app.entity.UserResponseEntity;
import com.takoikatakotako.app.entity.UserSignUpRequestEntity;
import com.takoikatakotako.app.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;

    ChatRoomResponseEntity roomCreate(ChatRoomCreateRequestEntity entity) throws Exception {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setType(entity.getType());
        chatRoomRepository.save(chatRoom);

        entity.getUsers().forEach(userID -> {
            ChatRoomUser chatRoomUser = new ChatRoomUser();
            chatRoomUser.setChatRoomID(chatRoom.getId());
            chatRoomUser.setUserID(userID);
            chatRoomUserRepository.save(chatRoomUser);
        });

        return new ChatRoomResponseEntity();
    }
}
