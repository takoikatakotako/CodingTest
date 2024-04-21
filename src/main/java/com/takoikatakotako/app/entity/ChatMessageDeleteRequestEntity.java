package com.takoikatakotako.app.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatMessageDeleteRequestEntity implements Serializable {
    Long userID;
}