package com.foxminded.hotel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.hotel.HotelApplication;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig(HotelApplication.class)
@AutoConfigureMockMvc
class RoomControllerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getRooms_repositoryNotEmpty_fourRoomsReturned() throws Exception{
        // Given
        // When
        ResultActions actions = mockMvc.perform(get("/rooms"))
                .andDo(MockMvcResultHandlers.print());
        // Then
        actions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", Matchers.hasSize(4)));
    }

    @Test
    void getRoom_withIdOne_returnValue() throws Exception {
        // Given
        // When
        ResultActions actions = mockMvc.perform(get("/rooms/1"))
                .andDo(MockMvcResultHandlers.print());
        // Then
        actions.andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$.userName", Matchers.containsString("tort")));
    }
}