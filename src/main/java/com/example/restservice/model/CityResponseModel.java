package com.example.restservice.model;

import com.example.restservice.entity.City;

import java.util.List;

public class CityResponseModel {

//    private String message;
    private Integer id;
    private String cityName;


    public CityResponseModel(City city){
        this.id = city.getId();
        this.cityName = city.getName();
    }

    public String getCityName(){
        return cityName;
    }
//
//    public void setCityName(String cityName){
//        this.cityName = cityName;
//    }

    public Integer getId(){
        return id;
    }
//
//    public void setId(Integer id){
//        this.id = id;
//    }

//    public String getMessage(){
//        return message;
//    }
//
//    public void setMessage(String message){
//        this.message = message;
//    }



}
