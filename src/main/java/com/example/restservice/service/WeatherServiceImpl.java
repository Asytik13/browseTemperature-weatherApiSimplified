package com.example.restservice.service;

import com.example.restservice.entity.City;
import com.example.restservice.entity.Weather;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class WeatherServiceImpl implements WeatherService {

   // CityServiceImpl.
    private List<City> cities = CityServiceImpl.cities;
    private static final Pattern tempPattern = Pattern.compile("^.*\"temp_c\":\\s*(.+?)\\s*,.*");
    private boolean nameExists;

    private Double getTemperatureForCity(String city){
        Double temperature;

        nameExists = cities.stream().
                anyMatch(x -> x.getName().
                        equals(city));

        if ( nameExists ) {
            final String url = "http://api.weatherapi.com/v1/current.json?key=972630bcf041404ba70190228200711&q=" + city;

            try (java.util.Scanner s = new java.util.Scanner(new java.net.URL(url).openStream())) {
                String resp = s.useDelimiter("\\A").next();
                Matcher m = tempPattern.matcher(resp);
                if ( m.find() ) {
                    temperature = Double.valueOf(m.group(1));
                    return temperature;
                }

                throw new RuntimeException("Cannot extract temp from: " + resp);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }

    public Weather getWeatherForCity(String city){
        String content = "Request returned temperature in " + city;
        Double temperature = getTemperatureForCity(city);
        if ( temperature != null ) {
            long id = cities.stream().filter(x -> x.getName().equals(city))
                    .findFirst().get().getId();
            return new Weather(temperature, id, content);
        } else {
            throw new NullPointerException();
        }
    }
}
