package com.takoikatakotako.app.controller;

import com.takoikatakotako.app.entity.*;
import com.takoikatakotako.app.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final ChatMessageRepository chatMessageRepository;

    /**
     * チャットルームを作成
     */
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

    /**
     * チャットルームを取得
     */
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


    /**
     * チャットルームのメッセージを取得
     */
    ChatRoomMessageListResponseEntity getChatRoomMessage(Long chatRoomID) throws Exception {

        // validation

        List<ChatMessage> chatMessages = chatMessageRepository.findByChatRoomID(chatRoomID);

        ArrayList<ChatMessageResponseEntity> chatMessageResponseEntities = new ArrayList<>();
        chatMessages.forEach( chatMessage -> {
            ChatMessageResponseEntity chatMessageResponseEntity = new ChatMessageResponseEntity();
            chatMessageResponseEntity.setChatMessageID(chatMessage.getId());
            chatMessageResponseEntity.setMessage(chatMessage.getMessage());
            chatMessageResponseEntities.add(chatMessageResponseEntity);
        });


        ChatRoomMessageListResponseEntity chatRoomMessageListResponseEntity = new ChatRoomMessageListResponseEntity();
        chatRoomMessageListResponseEntity.setMessages(chatMessageResponseEntities);
        return chatRoomMessageListResponseEntity;
    }


    /**
     * チャットルームのメッセージを投稿
     */
    String postMessage(Long chatRoomID, ChatPostMessageRequestEntity requestEntity) throws Exception {

        // validation


        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setDeleted(false);
        chatMessage.setChatRoomID(chatRoomID);
        chatMessage.setUserID(requestEntity.getUserID());
        chatMessage.setMessage(requestEntity.getMessage());
        LocalDateTime currentDateTime = LocalDateTime.now();
        chatMessage.setCreatedAt(currentDateTime);
        chatMessage.setUpdatedAt(currentDateTime);
        chatMessageRepository.save(chatMessage);


        return "xxxx";
    }



    /**
     * チャットルームのメッセージを削除
     */
    String deleteMessage(Long chatMessageID, ChatMessageDeleteRequestEntity requestEntity) throws Exception {

        // validation
        ChatMessage chatMessage = chatMessageRepository.findById(chatMessageID).orElseThrow();
        chatMessage.setDeleted(true);
        chatMessage.setUpdatedAt(LocalDateTime.now());
        chatMessageRepository.save(chatMessage);

        return "xxxx";
    }
}
