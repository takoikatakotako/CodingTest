package com.takoikatakotako.app.controller;

import com.takoikatakotako.app.entity.UserSignUpRequestEntity;
import com.takoikatakotako.app.repository.User;
import com.takoikatakotako.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    String addUser(UserSignUpRequestEntity entity) throws Exception {
        User user = new User();
        user.setName(entity.getName());
        userRepository.save(user);

        return "Success";
    }
}
