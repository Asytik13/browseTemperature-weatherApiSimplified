package com.example.restservice.service;

import com.example.restservice.entity.City;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    public static List<City> cities = new ArrayList<>();
    boolean nameExists;

    @Override
    public List<City> getAllCities(){
        return cities;
    }

    @Override
    public City addCity(String cityName) throws Exception{

            if ( cityName == null ) {
                throw new Exception("Invalid body");
            }

            nameExists = cities.stream().anyMatch(x -> x.getName().equals(cityName));
            City newCity;
            if ( !nameExists ) {
                newCity = new City(cityName, cities.size());
                cities.add(newCity);
            } else {
                throw new Exception("City already exists");
            }
            return newCity;
    }

    @Override
    public void removeCity(String cityName) throws NoSuchFieldException{
        boolean nameExists = cities.stream().anyMatch(x -> x.getName().equals(cityName));

        if ( !nameExists ) {
            throw new NoSuchFieldException();
        }
        int ixs = cities.stream()
                .filter(x -> x.getName().equals(cityName))
                .findFirst().get().getId();

        cities.remove(ixs);
    }
}