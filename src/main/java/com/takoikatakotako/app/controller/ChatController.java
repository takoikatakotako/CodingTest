package com.takoikatakotako.app.controller;

import com.takoikatakotako.app.entity.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;


    /**
     * チャットルームを作成する
     */
    @RequestMapping(value = "/room/create", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ChatRoomResponseEntity roomCreate(@RequestBody ChatRoomCreateRequestEntity requestEntity, HttpServletResponse response) {
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            return chatService.roomCreate(requestEntity);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ChatRoomResponseEntity();
        }
    }



    /**
     * チャットルームID から情報を取得する
     */
    @RequestMapping(value = "/room/{chatRoomID}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ChatRoomResponseEntity getChatRoom(@PathVariable Long chatRoomID, HttpServletResponse response) {
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            return chatService.getRoom(chatRoomID);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ChatRoomResponseEntity();
        }
    }



    /**
     * チャットを投稿する
     */
    @RequestMapping(value = "/room/{chatRoomID}/post", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String postMessage(@PathVariable Long chatRoomID, @RequestBody ChatPostMessageRequestEntity requestEntity, HttpServletResponse response) {
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            return chatService.postMessage(chatRoomID, requestEntity);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "";
        }
    }
}
