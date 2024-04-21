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
        String firstUserName = "1st user";
        String secondUserName = "takoikatakotako";

        // ユーザー作成
        UserResponseEntity firstUserSignUpResponse = signupUser(firstUserName);
        Long firstUserID = firstUserSignUpResponse.getId();
        UserResponseEntity secondUserSignUpResponse = signupUser(secondUserName);
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
        ChatRoomMessageListResponseEntity xxx = getChatRoomMessageList(chatRoomID);
        assertEquals(xxx.getMessages().size(), 4);
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
}
