package com.foxminded.hotel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.hotel.HotelApplication;
import com.foxminded.hotel.model.User;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import static com.foxminded.hotel.service.test_data.UserTestDataFactory.getUserToRegister;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig(HotelApplication.class)
@AutoConfigureMockMvc
class UserControllerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    @Disabled
    void registerUser_validUser_returnUserResource() throws Exception{
        User user = getUserToRegister();
        // Given


//        // When
        MockHttpServletResponse response = mockMvc.perform(post("/register")
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .content(MAPPER.writeValueAsString(user)))
                .andReturn()
                .getResponse();
        //Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

//        // When
//        ResultActions actions = mockMvc.perform(post("/register")
//                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
//                .content(MAPPER.writeValueAsString(user)))
//                .andDo(MockMvcResultHandlers.print());
        // Then
//        actions.andExpect(status().isCreated())
//                .andExpect(content().contentType("application/hal+json;charset=UTF-8"))
//                .andExpect(jsonPath("$.userName", Matchers.equalTo("torkit")));

//        given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).body(user);
//
//        ResultActions actions = mockMvc.perform(post("/register")
//                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
//                .content(MAPPER.writeValueAsString(user)))
//                .andDo(MockMvcResultHandlers.print());
////        actions.andExpect(status().isCreated())
//                actions.andExpect(content().contentType("application/hal+json;charset=UTF-8"))
//                .andExpect(jsonPath("$.userName", Matchers.equalTo("torkit")));

    }

    @Test
    @Disabled
    void registerUser_passwordEmpty_returnBadRequest() throws Exception{
        User user = new User();
        user.setUserName("torkit");
        user.setFirstName("Roman");
        user.setLastName("Torkit");

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(MAPPER.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Disabled
    @DisplayName("Login with valid credentials")
    void login_validCredentials_returnUserResource() {
        io.restassured.module.mockmvc.RestAssuredMockMvc.given()
                .when().post("/login?userName=tort&password=1234")
                .then().statusCode(HttpStatus.OK.value());
//                .assertThat(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//                .assertThat(jsonPath("userName", Matchers.equalTo("tort")));
    }

    @Test
    @DisplayName("Get user by id")
    void getUserById_validCredentials_returnUserDetails() {
        io.restassured.module.mockmvc.RestAssuredMockMvc.given()
                .when().get("/users/1000")
                .then().statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @Disabled
    void userBookings() {
    }
}