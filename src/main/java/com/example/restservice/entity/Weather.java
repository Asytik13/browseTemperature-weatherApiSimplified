package com.example.restservice.entity;

public class Weather {

    private final long id;
    private final String content;
    private Double temperature;

    public Weather(Double temperature, long id, String content) {
        this.temperature = temperature;
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Double getTemperature() {
        return temperature;
    }
}