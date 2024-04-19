package com.takoikatakotako.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthcheck")
public class HealthCheckController {
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String healthcheck() {
        return "success";
    }
}
