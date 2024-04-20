package com.takoikatakotako.app.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Data
public class ChatRoomCreateRequestEntity implements Serializable {
    String type;
    ArrayList<Long> users;
}

