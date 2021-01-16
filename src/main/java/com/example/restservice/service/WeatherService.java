package com.example.restservice.service;

import com.example.restservice.entity.Weather;

public interface WeatherService {
    Weather getWeatherForCity(String city);
}
