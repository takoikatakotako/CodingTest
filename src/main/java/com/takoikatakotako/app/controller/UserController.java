package com.takoikatakotako.app.controller;

import com.takoikatakotako.app.entity.UserSignUpRequestEntity;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String signUp(@RequestBody UserSignUpRequestEntity xxx, HttpServletResponse response) {
        try {
            return userService.addUser(xxx);
        } catch (Exception e) {
            return "error";
        }
    }
}
