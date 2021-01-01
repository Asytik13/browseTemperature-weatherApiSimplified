package com.example.restservice.service;

import com.example.restservice.entity.City;
import com.example.restservice.model.CityResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityServiceImpl implements CityService{

    private static List<City> cities = new ArrayList<>();
    boolean nameExists;

    @Override
    public List<City> getAllCities(){
        return cities;
    }

    @Override
    public City addCity(String cityName) throws Exception{
        // controller?
//        String message = "City " + cityName + " already exists";
        if ( cityName == null ) {
            throw new Exception("Body is empty");
        }

        nameExists = cities.stream().anyMatch(x -> x.getName().equals(cityName));
        City newCity;
        if ( !nameExists ) {
            newCity = new City(cityName, cities.size());
//            newCity.setName(cityName);
//            newCity.setId(cities.size());

            cities.add(newCity);
//
//            message = "City added";
        } else {
            throw new Exception("City already exists");
        }

//        City newCity = cities
//                .stream()
//                .filter(x -> x.getName().equals(cityName))
//                .findFirst()
//                .orElseThrow(Exception::new);
//
//        CityResponseModel response = new CityResponseModel();
//        response.setId(newCity.getId());
//        response.setMessage(message);


        return newCity;
    }

    @Override
    public City updateCity(String cityName){
        return null;
    }

    @Override
    public void removeCity(String cityName) throws NoSuchFieldException{
        boolean nameExists = cities.stream().anyMatch(x -> x.getName().equals(cityName));
//        String message = "City " + cityName + " is removed from list";

//        CityResponseModel response = new CityResponseModel();

        if ( !nameExists ) {
           throw new NoSuchFieldException();
        }
        int ixs = cities.stream()
                .filter(x -> x.getName().equals(cityName))
                .findFirst().get().getId();

        cities.remove(ixs);
    }
}
