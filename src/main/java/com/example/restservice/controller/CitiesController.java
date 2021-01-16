package com.example.restservice.controller;

import com.example.restservice.entity.City;
import com.example.restservice.model.CityRequestModel;
import com.example.restservice.model.CityResponseModel;
import com.example.restservice.model.CityResponseModelGetCities;
import com.example.restservice.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cities")
public class CitiesController {

    private CityService cityService;

    @Autowired
    public void setCityService(CityService cityService){
        this.cityService = cityService;
    }

    @GetMapping()
    public ResponseEntity<?> getCities(){
        try {
            List<City> allCities = cityService.getAllCities();
            CityResponseModelGetCities cityResponseModel = new CityResponseModelGetCities();
            cityResponseModel.setCities(allCities);
            return new ResponseEntity<>(cityResponseModel, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request");
        }
    }

    @PostMapping(consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCity(@RequestBody CityRequestModel city) throws Exception{
        try {
            String name = city.getCity();
            City newCity = cityService.addCity(name);

            CityResponseModel response = new CityResponseModel(newCity);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("City is Not Added" );
        }
    }

    @DeleteMapping(value = "/{name}")
    public ResponseEntity deletePost(@PathVariable String name){

        try {
            cityService.removeCity(name);
            return (ResponseEntity) ResponseEntity.ok("City is removed");
        } catch (NoSuchFieldException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No such city in a list");
        }
    }
}
