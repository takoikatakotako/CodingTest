package com.takoikatakotako.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.takoikatakotako.app.entity.ChatRoomCreateRequestEntity;
import com.takoikatakotako.app.entity.ChatRoomResponseEntity;
import com.takoikatakotako.app.entity.UserResponseEntity;
import com.takoikatakotako.app.entity.UserSignUpRequestEntity;
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

        // SignUp Users
        UserResponseEntity firstUserSignUpResponse = signupUser(firstUserName);
        Long firstUserID = firstUserSignUpResponse.getId();
        UserResponseEntity secondUserSignUpResponse = signupUser(secondUserName);
        Long secondUserID = secondUserSignUpResponse.getId();


        // 部屋作成
        ChatRoomCreateRequestEntity chatRoomCreateRequestEntity = new ChatRoomCreateRequestEntity();
        chatRoomCreateRequestEntity.setType("XXXXX");
        ArrayList<Long> userIDList = new ArrayList<>();
        userIDList.add(firstUserID);
        userIDList.add(secondUserID);
        chatRoomCreateRequestEntity.setUsers(userIDList);

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
        ChatRoomResponseEntity chatRoomResponse = mapper.readValue(chatRoomCreateResponse, ChatRoomResponseEntity.class);


//        UserSignUpRequestEntity signupRequest = new UserSignUpRequestEntity();
//        signupRequest.setName(userName);
//
//        ObjectMapper mapper = new ObjectMapper();
//        String signupRequestJSON = mapper.writeValueAsString(signupRequest);
//
//        MvcResult signupRequestResult = mockMvc.perform(
//                        post("/user/signup")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(signupRequestJSON)
//                )
//                .andExpect(status().is2xxSuccessful())
//                .andReturn();
//
//        String signupRequestResponse = signupRequestResult.getResponse().getContentAsString();
//        UserResponseEntity signupResponse = mapper.readValue(signupRequestResponse, UserResponseEntity.class);
//
//        assertEquals(userName, signupResponse.getName());
//
//
//        // Get User
//        Long userID = signupResponse.getId();
//        MvcResult getUserResult = mockMvc.perform(
//                        get("/user/" + userID.toString())
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().is2xxSuccessful())
//                .andReturn();
//
//        String getUserRequestResponse = signupRequestResult.getResponse().getContentAsString();
//        UserResponseEntity getUserResponse = mapper.readValue(getUserRequestResponse, UserResponseEntity.class);
//
//        assertEquals(userID, getUserResponse.getId());
//        assertEquals(userName, getUserResponse.getName());
    }


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
}