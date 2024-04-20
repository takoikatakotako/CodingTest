package com.takoikatakotako.app.controller;

import com.takoikatakotako.app.entity.UserSignUpRequestEntity;
import com.takoikatakotako.app.entity.UserResponseEntity;
import com.takoikatakotako.app.repository.User;
import com.takoikatakotako.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    UserResponseEntity signup(UserSignUpRequestEntity entity) throws Exception {
        User user = new User();
        user.setName(entity.getName());
        userRepository.save(user);

        UserResponseEntity response = new UserResponseEntity();
        response.setId(user.getId());
        response.setName(user.getName());
        return response;
    }

    UserResponseEntity getUser(Long userID) throws Exception {
        User user = userRepository.findById(userID).orElseThrow();
        UserResponseEntity response = new UserResponseEntity();
        response.setId(user.getId());
        response.setName(user.getName());
        return response;
    }
}
