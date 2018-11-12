package com.foxminded.hotel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.hotel.HotelApplication;
import com.foxminded.hotel.model.User;
import com.foxminded.hotel.repo.UserRepo;
import com.foxminded.hotel.resources.UserResource;
import com.foxminded.hotel.service.UserService;
import com.foxminded.hotel.service.UserServiceImpl;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static com.foxminded.hotel.service.test_data.UserTestDataFactory.*;
import static com.foxminded.hotel.service.test_data.UserTestDataFactory.getUserResource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@SpringJUnitWebConfig(HotelApplication.class)
@AutoConfigureMockMvc
class UserControllerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private UserService userService;
    @MockBean
    private UserRepo userRepo;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup(){
        RestAssuredMockMvc.mockMvc(mockMvc);
        userService = new UserServiceImpl(userRepo);
    }

    @Test
    void registerUser_validUser_returnUserResource() throws Exception{
        User user = getUser2();
        // Given
        given(userService.create(user)).willReturn(new UserResource(user));
        // When
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
//        // Then
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
    void registerUser_passwordEmpty_returnBadRequest() throws Exception{
        User user = new User();
        user.setUserName("torkit");
        user.setFirstName("Roman");
        user.setLastName("Torkit");

        given(userService.create(user)).willThrow(IllegalArgumentException.class);

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(MAPPER.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Login with valid credentials")
    void login_validCredentials_returnUserResource() {
        io.restassured.module.mockmvc.RestAssuredMockMvc.given()
                .when().post("/login?password=1234&userName=tort")
                .then().statusCode(HttpStatus.OK.value())
                .assertThat(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .assertThat(jsonPath("userName", Matchers.equalTo("tort")));
    }

    @Test
    @DisplayName("Get user by id")
    void getUserById_validCredentials_returnUserDetails() throws Exception {
//        given(userService.getById(any())).willReturn(getUserResource());
        User expected = getUser1();
        ResultActions actions = mockMvc.perform(get("/users/" + expected.getUserId()))
                .andDo(MockMvcResultHandlers.print());
        // Then
        actions.andExpect(status().isOk());
//                .andExpect(content().contentType("application/hal+json;charset=UTF-8"));
//                .andExpect(jsonPath("$.userId", Matchers.equalTo(1)));
    }

    @Test
    @Disabled
    void userBookings() {
    }
}