package com.takoikatakotako.app.controller;

import com.takoikatakotako.app.entity.UserSettingRequestEntity;
import com.takoikatakotako.app.entity.UserSignUpRequestEntity;
import com.takoikatakotako.app.entity.UserResponseEntity;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    /**
     * ユーザーを登録する
     */
    @RequestMapping(value = "/signup", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public UserResponseEntity signup(@RequestBody UserSignUpRequestEntity xxx, HttpServletResponse response) {
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            return userService.signup(xxx);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new UserResponseEntity();
        }
    }

    /**
     * UserID からユーザーを取得する
     */
    @RequestMapping(value = "/{userID}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public UserResponseEntity getUser(@PathVariable Long userID, HttpServletResponse response) {
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            return userService.getUser(userID);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new UserResponseEntity();
        }
    }

    /**
     * ユーザーの通知を設定流する
     */
    @RequestMapping(value = "/setting", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String setting(@RequestBody UserSettingRequestEntity xxx, HttpServletResponse response) {
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            return userService.setting(xxx);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "Fail";
        }
    }
}
