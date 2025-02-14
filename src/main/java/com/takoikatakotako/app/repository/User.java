package com.takoikatakotako.app.repository;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "notification_type", nullable = false)
    private String notificationType;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "sns_endpoint_arn", nullable = false)
    private String snsEndpointArn;
}
