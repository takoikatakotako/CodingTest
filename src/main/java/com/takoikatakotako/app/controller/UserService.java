package com.takoikatakotako.app.controller;

import com.takoikatakotako.app.entity.UserSettingRequestEntity;
import com.takoikatakotako.app.entity.UserSignUpRequestEntity;
import com.takoikatakotako.app.entity.UserResponseEntity;
import com.takoikatakotako.app.repository.AmazonSNSRepository;
import com.takoikatakotako.app.repository.User;
import com.takoikatakotako.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AmazonSNSRepository snsRepository;

    /**
     * ユーザーを登録する
     */
    UserResponseEntity signup(UserSignUpRequestEntity entity) throws Exception {
        User user = new User();
        user.setName(entity.getName());
        user.setNotificationType("XXX");
        user.setEmail("");
        user.setSnsEndpointArn("");
        userRepository.save(user);

        UserResponseEntity response = new UserResponseEntity();
        response.setId(user.getId());
        response.setName(user.getName());
        return response;
    }

    /**
     * ユーザーを取得する
     */
    UserResponseEntity getUser(Long userID) throws Exception {
        User user = userRepository.findById(userID).orElseThrow();
        UserResponseEntity response = new UserResponseEntity();
        response.setId(user.getId());
        response.setName(user.getName());
        return response;
    }

    /**
     * ユーザーの設定を更新する
     */
    String setting(UserSettingRequestEntity requestEntity) throws Exception {
        User user = userRepository.findById(requestEntity.getUserID()).orElseThrow();
        user.setNotificationType(requestEntity.getNotificationType());
        user.setEmail(requestEntity.getEmail());
        String snsEndpointArn = snsRepository.createSNSEndpoint(requestEntity.getPushToken());
        user.setSnsEndpointArn(snsEndpointArn);
        userRepository.save(user);
        return "Notification Setting Update!!";
    }
}
