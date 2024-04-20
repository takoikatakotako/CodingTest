package com.takoikatakotako.app.entity;

import lombok.Data;

import java.io.Serializable;

@Data
//@Builder
public class UserResponseEntity implements Serializable {
    Long id;
    String name;
}
