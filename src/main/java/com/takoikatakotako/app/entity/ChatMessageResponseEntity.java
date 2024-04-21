package com.takoikatakotako.app.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatMessageResponseEntity implements Serializable {
    Long chatMessageID;
    String message;
}