package com.takoikatakotako.app.controller;

import com.takoikatakotako.app.entity.ChatRoomCreateRequestEntity;
import com.takoikatakotako.app.entity.ChatRoomResponseEntity;
import com.takoikatakotako.app.entity.UserResponseEntity;
import com.takoikatakotako.app.entity.UserSignUpRequestEntity;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;


    /**
     * チャットルームを作成する
     */
    @RequestMapping(value = "/room/create", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ChatRoomResponseEntity signup(@RequestBody ChatRoomCreateRequestEntity requestEntity, HttpServletResponse response) {
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            return chatService.roomCreate(requestEntity);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ChatRoomResponseEntity();
        }
    }
}
