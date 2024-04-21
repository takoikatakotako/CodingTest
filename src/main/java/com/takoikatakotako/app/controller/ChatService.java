package com.takoikatakotako.app.controller;

import com.takoikatakotako.app.entity.ChatRoomCreateRequestEntity;
import com.takoikatakotako.app.entity.ChatRoomResponseEntity;
import com.takoikatakotako.app.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        ChatRoomResponseEntity response = new ChatRoomResponseEntity();
        response.setChatRoomID(chatRoom.getId());
        return response;
    }


    ChatRoomResponseEntity getRoom(Long chatRoomID) throws Exception {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomID).orElseThrow();
        List<ChatRoomUser> chatRoomUsers = chatRoomUserRepository.findByChatRoomID(chatRoom.getId());

        ArrayList<Long> users = new ArrayList<>();
        chatRoomUsers.forEach(chatRoomUser -> {
                    users.add(chatRoomUser.getUserID());
                }
        );

        ChatRoomResponseEntity chatRoomResponse = new ChatRoomResponseEntity();
        chatRoomResponse.setChatRoomID(chatRoom.getId());
        chatRoomResponse.setUsers(users);
        return chatRoomResponse;
    }
}
