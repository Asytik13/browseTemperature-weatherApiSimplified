package com.example.restservice.helper;

import com.example.restservice.model.WeatherResponseModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

public class JsonConverter {
    public String convertObjectToJson(Object o) throws JsonProcessingException{
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(o)
                .replaceAll(" ", "")
                .replaceAll("\n", "");

        return json;
    }

    public WeatherResponseModel convertJsonToObject(String json) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        WeatherResponseModel object = mapper.readValue(json, WeatherResponseModel.class);
        return object;
    }
}
