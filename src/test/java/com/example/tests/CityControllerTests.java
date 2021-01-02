package com.example.tests;

import com.example.restservice.Main;
import com.example.restservice.entity.City;
import com.example.restservice.helper.JsonConverter;
import com.example.restservice.model.CityResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Main.class)
@AutoConfigureMockMvc
public class CityControllerTests extends BaseClass {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getCitiesReturns404() throws Exception{
        this.mockMvc.perform(get(BASE_ENDPOINT + "/citiefsList")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void postCityReturns201() throws Exception{

        JsonConverter converter = new JsonConverter();
        String body = createRequestBody("Krakow");

        if ( verifyCityIsAdded() ) {
            this.mockMvc.perform(delete(BASE_ENDPOINT + "/deleteCity/Krakow"))
                    .andDo(print()).andExpect(status().isOk());
        }

        String actualResponseBody = this.mockMvc.perform(post(BASE_ENDPOINT + "/city")
                                                                 .contentType(MediaType.APPLICATION_JSON)
                                                                 .content(body))
                .andDo(print()).andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        CityResponseModel response = new CityResponseModel(new City("Krakow", 0));
        String expectedResponseBody = converter.convertObjectToJson(response);

        assertThat(actualResponseBody).isEqualTo(expectedResponseBody);
    }

    @Test
    public void postCityReturns400() throws Exception{

        String body = createRequestBody(null);
        String message = this.mockMvc.perform(post(BASE_ENDPOINT + "/city")
                                                      .contentType(MediaType.APPLICATION_JSON)
                                                      .content(body))
                .andDo(print()).andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();

        assertThat(message).isEqualTo("400 BAD_REQUEST \"City is Not Added. Invalid body\"");
    }
}
