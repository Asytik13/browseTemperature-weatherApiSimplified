package com.example.restservice.service;

import com.example.restservice.entity.City;

import java.util.List;

public interface CityService {
    City addCity(String cityName) throws Exception;
    City updateCity(String cityName);
    void removeCity(String cityName) throws NoSuchFieldException;
    List<City> getAllCities();
}
