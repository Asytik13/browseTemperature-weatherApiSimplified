package com.example.restservice.model;

import com.example.restservice.entity.City;

public class CityResponseModel {

    private Integer id;
    private String cityName;

    public CityResponseModel(City city){
        this.id = city.getId();
        this.cityName = city.getName();
    }

    public String getCityName(){
        return cityName;
    }

    public Integer getId(){
        return id;
    }
}
