package com.example.restservice.controller;

import com.example.restservice.entity.Weather;
import com.example.restservice.model.WeatherResponseModel;
import com.example.restservice.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class WeatherController {

    private WeatherService weatherService;

    @Autowired
    public void setWeatherService(WeatherService weatherService) { this.weatherService = weatherService; }

    @GetMapping("/temperature")
    public ResponseEntity<WeatherResponseModel> getTemperature(@RequestParam("city") String city, Model model){
        try {
            model.addAttribute("city", city);
            Weather weather = weatherService.getWeatherForCity(city);
            WeatherResponseModel result = new WeatherResponseModel(weather);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (NullPointerException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "City is Not Found");
        }
    }
}