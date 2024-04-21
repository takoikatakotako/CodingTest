package com.takoikatakotako.app.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;


import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Data
public class ChatPostMessageRequestEntity implements Serializable {
    Long userID;
    String message;
}
