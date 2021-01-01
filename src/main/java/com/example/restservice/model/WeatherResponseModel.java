package com.example.restservice.model;

import com.example.restservice.entity.Weather;

public class WeatherResponseModel {

    private final long id;
    private final String content;
    private Double temperature;

    public WeatherResponseModel(Weather weather){
        this.temperature = weather.getTemperature();
        this.id = weather.getId();
        this.content = weather.getContent();
    }

    public long getId(){
        return id;
    }

    public String getContent(){
        return content;
    }

    public Double getTemperature(){
        return temperature;
    }
}