package com.example.restservice.model;

import com.example.restservice.entity.City;

import java.util.ArrayList;

public class CitiesResponseModel {
    ArrayList<City> cities;

    public CitiesResponseModel(ArrayList<City> cities){
        this.cities = cities;
    }

    public void setCities(ArrayList<City> cities){
        this.cities = cities;
    }

    public ArrayList<City> getCities(){
        return cities;
    }
}
