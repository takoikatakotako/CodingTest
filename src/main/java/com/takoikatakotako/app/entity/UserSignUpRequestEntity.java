package com.takoikatakotako.app.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
//@Builder
public class UserSignUpRequestEntity implements Serializable {
    String name;
}