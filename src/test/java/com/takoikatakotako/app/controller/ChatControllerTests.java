package com.takoikatakotako.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.takoikatakotako.app.entity.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class ChatControllerTests {
    @Autowired
    private MockMvc mockMvc;

    /**
     * 1対1でチャットができる
     */
    @Test
    @Transactional
    void oneToOneChat() throws Exception {
        // ユーザー作成
        UserResponseEntity firstUserSignUpResponse = signupUser("1st User");
        Long firstUserID = firstUserSignUpResponse.getId();
        UserResponseEntity secondUserSignUpResponse = signupUser("2nd User");
        Long secondUserID = secondUserSignUpResponse.getId();

        // チャットルーム作成
        ArrayList<Long> userIDList = new ArrayList<>();
        userIDList.add(firstUserID);
        userIDList.add(secondUserID);
        ChatRoomResponseEntity chatRoomResponse = createChatRoom("DIRECT", userIDList);
        Long chatRoomID = chatRoomResponse.getChatRoomID();

        // メッセージ送信
        postMessage(chatRoomID, firstUserID, "こんにちは");
        postMessage(chatRoomID, secondUserID, "こんにちは!");
        postMessage(chatRoomID, firstUserID, "いい天気ですね");
        postMessage(chatRoomID, secondUserID, "そうですね〜");

        //
        ChatRoomMessageListResponseEntity messageListResponse = getChatRoomMessageList(chatRoomID);
        ArrayList<ChatMessageResponseEntity> messages = messageListResponse.getMessages();
        assertEquals(messages.size(), 4);
        assertEquals(messages.get(0).getMessage(), "こんにちは");
        assertEquals(messages.get(1).getMessage(), "こんにちは!");
        assertEquals(messages.get(2).getMessage(), "いい天気ですね");
        assertEquals(messages.get(3).getMessage(), "そうですね〜");
    }



    /**
     * グループチャットができる
     */
    @Test
    @Transactional
    void groupChat() throws Exception {
        // ユーザー作成
        UserResponseEntity firstUserSignUpResponse = signupUser("1st User");
        Long firstUserID = firstUserSignUpResponse.getId();
        UserResponseEntity secondUserSignUpResponse = signupUser("2nd User");
        Long secondUserID = secondUserSignUpResponse.getId();
        UserResponseEntity thirdUserSignUpResponse = signupUser("3rd User");
        Long thirdUserID = thirdUserSignUpResponse.getId();
        UserResponseEntity fourthUserSignUpResponse = signupUser("4th User");
        Long fourthID = fourthUserSignUpResponse.getId();

        // チャットルーム作成
        ArrayList<Long> userIDList = new ArrayList<>();
        userIDList.add(firstUserID);
        userIDList.add(secondUserID);
        userIDList.add(thirdUserID);
        userIDList.add(fourthID);
        ChatRoomResponseEntity chatRoomResponse = createChatRoom("GROUP", userIDList);
        Long chatRoomID = chatRoomResponse.getChatRoomID();

        // メッセージ送信
        postMessage(chatRoomID, firstUserID, "好きなプログラミング言語は？");
        postMessage(chatRoomID, secondUserID, "Swiftが好きです!");
        postMessage(chatRoomID, thirdUserID, "Go言語が好きです。");
        postMessage(chatRoomID, fourthID, "Pythonも好きです");
        postMessage(chatRoomID, thirdUserID, "書き慣れたJavaが好きです。");
        postMessage(chatRoomID, fourthID, "Javaのバージョンが21で驚きました");

        //　
        ChatRoomMessageListResponseEntity messageListResponse = getChatRoomMessageList(chatRoomID);
        ArrayList<ChatMessageResponseEntity> messages = messageListResponse.getMessages();
        assertEquals(messages.size(), 6);
        assertEquals(messages.get(0).getMessage(), "好きなプログラミング言語は？");
        assertEquals(messages.get(1).getMessage(), "Swiftが好きです!");
        assertEquals(messages.get(2).getMessage(), "Go言語が好きです。");
        assertEquals(messages.get(3).getMessage(), "Pythonも好きです");
        assertEquals(messages.get(4).getMessage(), "書き慣れたJavaが好きです。");
        assertEquals(messages.get(5).getMessage(), "Javaのバージョンが21で驚きました");
    }





    /**
     * 投稿したメッセージの削除ができる
     */
    @Test
    @Transactional
    void deleteMessage() throws Exception {
        // ユーザー作成
        UserResponseEntity firstUserSignUpResponse = signupUser("1st User");
        Long firstUserID = firstUserSignUpResponse.getId();
        UserResponseEntity secondUserSignUpResponse = signupUser("2nd User");
        Long secondUserID = secondUserSignUpResponse.getId();

        // チャットルーム作成
        ArrayList<Long> userIDList = new ArrayList<>();
        userIDList.add(firstUserID);
        userIDList.add(secondUserID);
        ChatRoomResponseEntity chatRoomResponse = createChatRoom("DIRECT", userIDList);
        Long chatRoomID = chatRoomResponse.getChatRoomID();

        // メッセージ送信
        postMessage(chatRoomID, firstUserID, "AWSではどのサービスが好き？");
        postMessage(chatRoomID, secondUserID, "S3が好きです");
        postMessage(chatRoomID, firstUserID, "S3いいですね。私もAtenaと一緒に使います。");
        postMessage(chatRoomID, firstUserID, "S3いいですね。私もAthenaと一緒に使います。");
        postMessage(chatRoomID, secondUserID, "イニシャルコストが安くていいですよね。");

        // メッセージ取得
        ChatRoomMessageListResponseEntity messageListBeforeDelete = getChatRoomMessageList(chatRoomID);
        Long willDeleteMessageID = messageListBeforeDelete.getMessages()
                .stream()
                .filter(message -> message.getMessage().equals("S3いいですね。私もAtenaと一緒に使います。"))
                .findFirst().orElseThrow().getChatMessageID();


        // メッセージ削除
        deleteMessage(willDeleteMessageID, firstUserID);

        //
        ChatRoomMessageListResponseEntity messageListResponse = getChatRoomMessageList(chatRoomID);
        ArrayList<ChatMessageResponseEntity> messages = messageListResponse.getMessages();
        assertEquals(messages.size(), 4);
//
//        assertEquals(messages.get(0).getMessage(), "好きなプログラミング言語は？");
//        assertEquals(messages.get(1).getMessage(), "Swiftが好きです!");
//        assertEquals(messages.get(2).getMessage(), "Go言語が好きです。");
//        assertEquals(messages.get(3).getMessage(), "Pythonも好きです");
//        assertEquals(messages.get(4).getMessage(), "書き慣れたJavaが好きです。");
//        assertEquals(messages.get(5).getMessage(), "Javaのバージョンが21で驚きました");
    }





    /**
     * ユーザー作成
     */
    private UserResponseEntity signupUser(String userName) throws Exception {
        UserSignUpRequestEntity signupRequest = new UserSignUpRequestEntity();
        signupRequest.setName(userName);

        ObjectMapper mapper = new ObjectMapper();
        String signupRequestJSON = mapper.writeValueAsString(signupRequest);

        MvcResult signupRequestResult = mockMvc.perform(
                        post("/user/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(signupRequestJSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String signupRequestResponse = signupRequestResult.getResponse().getContentAsString();
        return mapper.readValue(signupRequestResponse, UserResponseEntity.class);
    }


    /**
     * チャットルーム作成
     */
    private ChatRoomResponseEntity createChatRoom(String type, ArrayList<Long> users) throws Exception {
        ChatRoomCreateRequestEntity chatRoomCreateRequestEntity = new ChatRoomCreateRequestEntity();
        chatRoomCreateRequestEntity.setType(type);
        chatRoomCreateRequestEntity.setUsers(users);

        ObjectMapper mapper = new ObjectMapper();
        String chatRoomCreateRequestJSON = mapper.writeValueAsString(chatRoomCreateRequestEntity);

        MvcResult chatRoomCreateRequestResult = mockMvc.perform(
                        post("/chat/room/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(chatRoomCreateRequestJSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String chatRoomCreateResponse = chatRoomCreateRequestResult.getResponse().getContentAsString();
        return mapper.readValue(chatRoomCreateResponse, ChatRoomResponseEntity.class);
    }




    /**
     * チャット投稿
     */
    private String postMessage(Long chatRoomID, Long userID, String message) throws Exception {
        ChatPostMessageRequestEntity requestEntity = new ChatPostMessageRequestEntity();
        requestEntity.setUserID(userID);
        requestEntity.setMessage(message);

        ObjectMapper mapper = new ObjectMapper();
        String requestEntityJSON = mapper.writeValueAsString(requestEntity);

        MvcResult requestResult = mockMvc.perform(
                        post("/chat/room/" + chatRoomID.toString() + "/post")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestEntityJSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        return requestResult.getResponse().getContentAsString();
        // String response = requestResult.getResponse().getContentAsString();
        // return mapper.readValue(response, String.class);
    }






    /**
     * チャット取得
     */
    private ChatRoomMessageListResponseEntity getChatRoomMessageList(Long chatRoomID) throws Exception {
        MvcResult requestResult = mockMvc.perform(
                        get("/chat/room/" + chatRoomID.toString() + "/message")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        // return requestResult.getResponse().getContentAsString();
         String response = requestResult.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response, ChatRoomMessageListResponseEntity.class);
    }





    /**
     * メッセージを削除
     */
    private String deleteMessage(Long chatMessageID, Long userID) throws Exception {
        ChatMessageDeleteRequestEntity requestEntity = new ChatMessageDeleteRequestEntity();
        requestEntity.setUserID(userID);

        ObjectMapper mapper = new ObjectMapper();
        String requestEntityJSON = mapper.writeValueAsString(requestEntity);

        MvcResult requestResult = mockMvc.perform(
                        post("/chat/message/" + chatMessageID.toString() + "/delete")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestEntityJSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        return requestResult.getResponse().getContentAsString();
    }
}
