package com.example.tests;

import com.example.restservice.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Main.class)
@AutoConfigureMockMvc
public class CityCotrollerTests extends BaseClass {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getCitiesReturns200() throws Exception{
        this.mockMvc.perform(get(BASE_ENDPOINT + "/citiesList")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getCitiesReturns404() throws Exception{
        this.mockMvc.perform(get(BASE_ENDPOINT + "/citiefsList")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void postCityReturns201() throws Exception{
        this.mockMvc.perform(post(BASE_ENDPOINT + "/city")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .content("{\"id\":0,\"city\":\"Krakow\"}"))
                .andDo(print()).andExpect(status().isCreated());
    }

    @Test
    public void postCityReturns400() throws Exception{
        String message = this.mockMvc.perform(post(BASE_ENDPOINT + "/city")
                                                      .contentType(MediaType.APPLICATION_JSON)
                                                      .content("\"j\":\"j\""))
                .andDo(print()).andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();

        assertThat(message.equals("City is Not Added. Invalid body"));
    }
}
