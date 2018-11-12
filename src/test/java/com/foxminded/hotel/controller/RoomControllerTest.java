package com.foxminded.hotel.controller;

import com.foxminded.hotel.HotelApplication;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig(HotelApplication.class)
@AutoConfigureMockMvc
class RoomControllerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private MockMvc mockMvc;

//    @MockBean
//    private RoomService service;

    @BeforeEach
    void setup(){
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    public void getRooms_repositoryNotEmpty_fourRoomsReturned() throws Exception{
        // Given
        // When
        ResultActions actions = mockMvc.perform(get("/rooms"))
                .andDo(MockMvcResultHandlers.print());
        // Then
        actions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", Matchers.hasSize(4)))
                .andExpect(jsonPath("$[0].roomId", Matchers.equalTo(1)));
    }

//    @Test
//    public void getRooms_repositoryNotEmpty_fourRoomsReturned() throws Exception{
////         Given
//        Optional<User> user = Optional.empty();
//        RoomResource room1 = new RoomResource(new Room(1L, 1, RoomCategory.SINGLE, 1000), user, null, null, new ArrayList<ServiceResource>());
//        RoomResource room2 = new RoomResource(new Room(), user, null, null, new ArrayList<ServiceResource>());
//        List<RoomResource> rooms = Arrays.asList(room1, room2);
//        Optional<String> category = Optional.of("SINGLE");
////        Optional<User> user = Optional.of(new User("tort", "1234", "Roman", "Torkit"));
//        org.mockito.BDDMockito.given(service.findAvailableRooms(user, category, null, null)).willReturn(rooms);
////         When
//        ResultActions actions = mockMvc.perform(get("/rooms"))
//                .andDo(MockMvcResultHandlers.print());
////         Then
//        actions.andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//                .andExpect(jsonPath("$", Matchers.hasSize(2)))
//                .andExpect(jsonPath("$[0].roomId", Matchers.equalTo(1)));
//    }

    @Test
    @DisplayName("Get room by id with valid id")
    void getRoom_validId_returnValue() throws Exception {
        // Given
        long id = 1L;
        // When
        ResultActions actions = mockMvc.perform(get("/rooms/" + id))
                .andDo(MockMvcResultHandlers.print());
        // Then
        actions.andExpect(status().isOk())
               .andExpect(content().contentType("application/hal+json;charset=UTF-8"))
               .andExpect(jsonPath("$.roomId", Matchers.equalTo(1)));
    }

//    @Test
//    void getRoom_invalidId_roomNotFound() throws Exception {
//        given()
//                .when().get("/rooms/1000")
//                .then().statusCode(HttpStatus.NOT_FOUND.value());
//    }

    @Test
    void getRoom_invalidId_roomNotFound() throws Exception {
        // Given
        long id = 1000L;
        // When
        ResultActions actions = mockMvc.perform(get("/rooms/" + id))
                .andDo(MockMvcResultHandlers.print());
        // Then
        actions.andExpect(status().isNotFound());
    }
}