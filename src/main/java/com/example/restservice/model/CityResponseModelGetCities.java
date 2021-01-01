package com.example.restservice.model;

import com.example.restservice.entity.City;
import java.util.List;

public class CityResponseModelGetCities {
    private List<City> cities;

    public void setCities(List<City> cities){
        this.cities = cities;
    }

    public List<City> getCities(){
        return cities;
    }
}
