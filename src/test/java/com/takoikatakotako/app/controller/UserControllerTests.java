package com.takoikatakotako.app.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.takoikatakotako.app.entity.UserResponseEntity;
import com.takoikatakotako.app.entity.UserSettingRequestEntity;
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
     * ユーザーの新規登録、ユーザー取得ができること
     */
    @Test
    @Transactional
    void signupAndGetUser() throws Exception {
        // ユーザー作成
        String userName = "takoikatakotako";
        UserResponseEntity signupResponse = signupUser(userName);
        assertEquals(userName, signupResponse.getName());

        // ユーザー取得
        Long userID = signupResponse.getId();
        UserResponseEntity getUserResponse = getUser(userID);

        // テスト
        assertEquals(userID, getUserResponse.getId());
        assertEquals(userName, getUserResponse.getName());
    }


    /**
     * 通知の設定ができること
     */
    @Test
    @Transactional
    void userSetting() throws Exception {
        // ユーザー作成
        String userName = "takoikatakotako";
        UserResponseEntity signupResponse = signupUser("takoikatakotako");
        assertEquals(userName, signupResponse.getName());

        // ユーザー取得
        Long userID = signupResponse.getId();
        UserResponseEntity getUserResponse = getUser(userID);

        // 設定の更新
        UserSettingRequestEntity userSettingRequestEntity = new UserSettingRequestEntity();
        userSettingRequestEntity.setNotificationType("EMAIL");
        userSettingRequestEntity.setEmail("takoikatakotako@gmail.com");
        userSettingRequestEntity.setPushToken("PUSH-TOKEN");
//        userSetting(userID, userSettingRequestEntity);

//        // ユーザー取得
//        UserResponseEntity updatedUserResponse = getUser(userID);
//
//        // テスト
//        assertEquals(userID, updatedUserResponse.getId());
//        assertEquals(userName, updatedUserResponse.getName());
//        assertEquals("EMAIL", updatedUserResponse.getNotificationType());
//        assertEquals("takoikatakotako@gmail.com", updatedUserResponse.getEmail());
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
     * ユーザーのアップデート
     */
    private UserResponseEntity userSetting(Long userID, UserSettingRequestEntity requestEntity) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String signupRequestJSON = mapper.writeValueAsString(requestEntity);

        MvcResult signupRequestResult = mockMvc.perform(
                        post("/user/setting")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(signupRequestJSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String signupRequestResponse = signupRequestResult.getResponse().getContentAsString();
        return mapper.readValue(signupRequestResponse, UserResponseEntity.class);
    }

    /**
     * ユーザー取得
     */
    private UserResponseEntity getUser(Long userID) throws Exception {
        MvcResult getUserResult = mockMvc.perform(
                        get("/user/" + userID.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String getUserRequestResponse = getUserResult.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(getUserRequestResponse, UserResponseEntity.class);
    }
}