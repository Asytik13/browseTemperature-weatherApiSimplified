package com.example.restservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@RestController
public class WeatherController {

    private List<City> cities = new ArrayList<>();

    private static final Pattern tempPattern = Pattern.compile("^.*\"temp_c\":\\s*(.+?)\\s*,.*");

    @GetMapping("/temperature")
    public Weather greeting(@RequestParam(value = "city", defaultValue = "Krakow") String city){
        Double temperature;
        String content = "Request returned temperature in "+ city;

        final String url = "http://api.weatherapi.com/v1/current.json?key=972630bcf041404ba70190228200711&q=" + city;

        try (java.util.Scanner s = new java.util.Scanner(new java.net.URL(url).openStream())) {
            String resp = s.useDelimiter("\\A").next();
            Matcher m = tempPattern.matcher(resp);
            if ( m.find()) {
                temperature = Double.valueOf(m.group(1));

                long id = cities.stream().filter(x -> x.getName().equals(city))
                        .findFirst().get().getId();
                return new Weather(temperature, id, content);
            }

            throw new RuntimeException("Cannot extract temp from: " + resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(path = "/citiesList", produces = "application/json")
    public @ResponseBody List<City> getCities() {

       return cities;
    }

    @PostMapping(path = "/city", consumes = "application/json", produces = "application/json")

    public ResponseEntity<CityResponseModel> createCity(@RequestBody CityRequestModel city) throws Exception{
        String name = city.getCity();

        String message ="City "+ name + " already exists";

        boolean nameExists = cities.stream().anyMatch(x -> x.getName().equals(name));

        if(!nameExists) {
            City newCity = new City();
            newCity.setName(name);
            newCity.setId(cities.size());

            cities.add(newCity);
            message = "City added";
        }

       City newCity = cities
                .stream()
                .filter(x -> x.getName().equals(name))
                .findFirst()
                .orElseThrow(Exception::new);

        CityResponseModel response = new CityResponseModel();
        response.setId(newCity.getId());
        response.setMessage(message);

        return ResponseEntity.ok(response);
    }
}