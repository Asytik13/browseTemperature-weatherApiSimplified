package com.example.tests;

import com.example.restservice.Main;
import com.example.restservice.helper.JsonConverter;
import com.example.restservice.model.WeatherResponseModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Main.class)
@AutoConfigureMockMvc
public class WeatherControllerTests extends BaseClass {
    private JsonConverter converter = new JsonConverter();
    private String body;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception{

        body = createRequestBody("Krakow");
        this.mockMvc.perform(post(BASE_ENDPOINT + "/city")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .content(body));
    }

    @Test
    public void getWeatherReturns200() throws Exception{

        String response = this.mockMvc.perform(get(BASE_ENDPOINT + "/temperature").param("city", "Krakow"))
                .andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        WeatherResponseModel weatherResponseModel = converter.convertJsonToObject(response);

        assertThat(weatherResponseModel.getTemperature()).isNotNull();
    }

    @Test
    public void getWeatherReturns404() throws Exception{

        String response =
                this.mockMvc.perform(get(BASE_ENDPOINT + "/temperature").param("city", "test"))
                        .andDo(print()).andExpect(status().isNotFound())
                        .andReturn().getResponse().getErrorMessage();

        assertThat(response).isEqualTo("City is Not Found");
    }

    @AfterEach
    public void clear() throws Exception{
        this.mockMvc.perform(delete(BASE_ENDPOINT + "/deleteCity/Krakow"))
                .andDo(print()).andExpect(status().isOk());
    }
}
