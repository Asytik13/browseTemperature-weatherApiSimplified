package com.example.restservice;

public class Weather {

    private final long id;
    private final String content;
    private Double temperature;

    public Weather(long id, String content) {
        this.id = id;
        this.content = content;
    }

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