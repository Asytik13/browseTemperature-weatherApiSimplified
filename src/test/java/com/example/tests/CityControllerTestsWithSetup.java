package com.example.tests;

import com.example.restservice.Main;
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
public class CityControllerTestsWithSetup extends BaseClass {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception{
        this.mockMvc.perform(post(BASE_ENDPOINT + "/city")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .content("{\"id\":0,\"city\":\"Krakow\"}"));
    }

    @Test
    public void postAlreadyExistingCityReturns400() throws Exception{
        String message = this.mockMvc.perform(post(BASE_ENDPOINT + "/city")
                                                      .contentType(MediaType.APPLICATION_JSON)
                                                      .content("\"city\":\"Krakow\""))
                .andDo(print()).andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();
        assertThat(message.equals("City is Not Added. City already exists"));
    }

    @Test
    public void deleteCityReturns200() throws Exception{
        this.mockMvc.perform(delete(BASE_ENDPOINT + "/deleteCity/Krakow"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void deleteNotExistigCityReturns400() throws Exception{
        String message = this.mockMvc.perform(delete(BASE_ENDPOINT + "/deleteCity/Krakow2"))
                .andDo(print()).andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();
        assertThat(message.equals("No such city in a list"));
    }
}
