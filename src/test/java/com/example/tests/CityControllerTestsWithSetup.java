package com.example.tests;

import com.example.restservice.Main;
import com.example.restservice.entity.City;
import com.example.restservice.helper.JsonConverter;
import com.example.restservice.model.CitiesResponseModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Main.class)
@AutoConfigureMockMvc
public class CityControllerTestsWithSetup extends BaseClass {

    @Autowired
    private MockMvc mockMvc;

    private JsonConverter converter = new JsonConverter();
    private String body;

    @BeforeEach
    public void setup() throws Exception{

        body = createRequestBody("Krakow");
        this.mockMvc.perform(post(BASE_ENDPOINT + "/cities")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .content(body));
    }

    @Test
    public void postAlreadyExistingCityReturns400() throws Exception{
        String message = this.mockMvc.perform(post(BASE_ENDPOINT + "/cities")
                                                      .contentType(MediaType.APPLICATION_JSON)
                                                      .content(body))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(message).isEqualTo("City is Not Added");
    }

    @Test
    public void deleteCityReturns200() throws Exception{
        this.mockMvc.perform(delete(BASE_ENDPOINT + "/cities/Krakow"))
                .andDo(print()).andExpect(status().isOk());
        String resp = this.mockMvc.perform(get(BASE_ENDPOINT + "/cities"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ArrayList<City> expectedObject = new ArrayList();
        CitiesResponseModel expectedResponse = new CitiesResponseModel(expectedObject);
        String expected = converter.convertObjectToJson(expectedResponse);

        assertThat(resp).isEqualTo(expected);
    }

    @Test
    public void getCitiesReturns200() throws Exception{
        String response = this.mockMvc.perform(get(BASE_ENDPOINT + "/cities"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        City expectedCity = new City("Krakow", 0);
        ArrayList<City> expectedObject = new ArrayList();
        expectedObject.add(expectedCity);

        CitiesResponseModel expectedResponse = new CitiesResponseModel(expectedObject);
        String expected = converter.convertObjectToJson(expectedResponse);
        assertThat(response).isEqualTo(expected);
    }

    @Test
    public void deleteNotExistingCityReturns400() throws Exception{
        String message = this.mockMvc.perform(delete(BASE_ENDPOINT + "/cities/Krakow2yt"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        assertThat(message).isEqualTo("No such city in a list");
    }

    @AfterEach
    public void clear() throws Exception{
        if ( verifyCityIsAdded() ) {
            this.mockMvc.perform(delete(BASE_ENDPOINT + "/cities/Krakow"))
                    .andDo(print()).andExpect(status().isOk());
        }
    }
}
