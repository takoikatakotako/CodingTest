package com.takoikatakotako.app.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Data
public class ChatRoomMessageListResponseEntity implements Serializable {
    ArrayList<ChatMessageResponseEntity> messages;
}
