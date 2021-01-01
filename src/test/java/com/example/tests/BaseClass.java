package com.example.tests;

import org.springframework.boot.web.server.LocalServerPort;

public class BaseClass {

    @LocalServerPort
    private int port;

    protected String BASE_ENDPOINT = "http://localhost:" + port;
}