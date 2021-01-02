package com.example.tests;

import com.example.restservice.helper.JsonConverter;
import com.example.restservice.model.CityRequestModel;
import com.example.restservice.service.CityServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.web.server.LocalServerPort;

public class BaseClass {

    @LocalServerPort
    private int port;

    protected String BASE_ENDPOINT = "http://localhost:" + port;

    public String createRequestBody(String cityName) throws Exception{
        CityRequestModel request = new CityRequestModel();
        request.setCity(cityName);

        JsonConverter converter = new JsonConverter();
        String body = converter.convertObjectToJson(request);
        return body;
    }

    public boolean verifyCityIsAdded(){
        int citiesLength = CityServiceImpl.cities.size();
        if ( citiesLength < 1 ) {
            return false;
        }
        return true;
    }
}