package com.takoikatakotako.app.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.takoikatakotako.app.entity.UserResponseEntity;
import com.takoikatakotako.app.entity.UserSignUpRequestEntity;
import com.takoikatakotako.app.repository.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;

    /**
     * ユーザーの新規登録、ユーザー取得
     */
    @Test
    @Transactional
    void signupAndGetUser() throws Exception {
        String userName = "takoikatakotako";

        // SignUp
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
        UserResponseEntity signupResponse = mapper.readValue(signupRequestResponse, UserResponseEntity.class);

        assertEquals(userName, signupResponse.getName());


        // Get User
        Long userID = signupResponse.getId();
        MvcResult getUserResult = mockMvc.perform(
                        get("/user/" + userID.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String getUserRequestResponse = signupRequestResult.getResponse().getContentAsString();
        UserResponseEntity getUserResponse = mapper.readValue(getUserRequestResponse, UserResponseEntity.class);

        assertEquals(userID, getUserResponse.getId());
        assertEquals(userName, getUserResponse.getName());
    }
}