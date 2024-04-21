package com.takoikatakotako.app.entity;

import lombok.Data;

import java.io.Serializable;

@Data
//@Builder
public class UserSettingRequestEntity implements Serializable {
    Long userID;
    String notificationType;
    String email;
    String pushToken;
}