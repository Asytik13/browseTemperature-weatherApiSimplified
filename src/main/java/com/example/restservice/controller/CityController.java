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
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CityController {

   private CityService cityService;

   @Autowired
   public void setCityService (CityService cityService){ this.cityService = cityService;}

    @GetMapping(path = "/citiesList")
    public ResponseEntity<CityResponseModelGetCities> getCities(){
        List<City> allCities = cityService.getAllCities();
        CityResponseModelGetCities cityResponseModel = new CityResponseModelGetCities();
        cityResponseModel.setCities(allCities);
        return new ResponseEntity<>(cityResponseModel, HttpStatus.OK);
    }

    @PostMapping(path = "/city", consumes = "application/json") //, produces = MediaType.APPLICATION_JSON_VALUE

    public ResponseEntity<CityResponseModel> createCity(@RequestBody CityRequestModel city) throws Exception{
        try {
            String name = city.getCity();
            City newCity = cityService.addCity(name);
//
//        String message = "City " + name + " already exists";
//
//        nameExists = cities.stream().anyMatch(x -> x.getName().equals(name));
//
//        if ( !nameExists && name != null ) {
//            City newCity = new City();
//            newCity.setName(name);
//            newCity.setId(cities.size());
//
//            cities.add(newCity);
//            message = "City added";
//        }
//
//        City newCity = cities
//                .stream()
//                .filter(x -> x.getName().equals(name))
//                .findFirst()
//                .orElseThrow(Exception::new);
//

            CityResponseModel response = new CityResponseModel(newCity);
//        response.setId(newCity.getId());
//        response.setMessage(message);

            //return ResponseEntity.ok(response);
            //ResponseEntity.created(response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "City is Not Added");
        }
    }

    @DeleteMapping(value = "/deleteCity/{name}")
    public ResponseEntity deletePost(@PathVariable String name){

       try{
           cityService.removeCity(name);
           return (ResponseEntity) ResponseEntity.ok("City is removed");
       }
       catch (NoSuchFieldException e) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No such city in a list");
       }
//        boolean nameExists = cities.stream().anyMatch(x -> x.getName().equals(name));
//        String message = "City " + name + " is removed from list";
//
//        CityResponseModel response = new CityResponseModel();
//
//        if ( !nameExists ) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        int ixs = cities.stream()
//                .filter(x -> x.getName().equals(name))
//                .findFirst().get().getId();
//
//        cities.remove(ixs);
//        response.setMessage(message);
//        return ResponseEntity.ok(response);
    }

}
