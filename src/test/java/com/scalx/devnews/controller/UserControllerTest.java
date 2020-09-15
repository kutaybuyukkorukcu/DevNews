package com.scalx.devnews.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalx.devnews.dto.user.UserRequest;
import com.scalx.devnews.entity.User;
import com.scalx.devnews.exception.ResourceNotFoundException;
import com.scalx.devnews.helper.FieldSetter;
import com.scalx.devnews.security.JwtUtil;
import com.scalx.devnews.security.UserDetailsServiceImpl;
import com.scalx.devnews.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UrlController.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserController userController;

    @Mock
    UserDetailsServiceImpl userDetailsService;

    @Mock
    JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    FieldSetter<UserRequest, User> fieldSetter;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_signin_whenAuthenticateIsNotValid() {
//        throws BadCredentialsException

        UserRequest userRequest = new UserRequest("Scalx", "Kutay",
                "123654", "kutay@gmail.com");

        doThrow(BadCredentialsException.class)
                .when(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword())
        );

        assertThatExceptionOfType(BadCredentialsException.class)
                .isThrownBy(() -> {
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword())
                    );
                });

        // handle api response

        verify(authenticationManager).authenticate(isA(UsernamePasswordAuthenticationToken.class));
        verifyNoMoreInteractions(userController);
    }

    @Test
    public void test_signin_whenLoadUserByUsernameIsNotPresent() {

        UserRequest userRequest = new UserRequest("Scalx", "Kutay",
                "123654", "kutay@gmail.com");

        doNothing().when(authenticationManager).authenticate(isA(UsernamePasswordAuthenticationToken.class));

        when(userDetailsService.loadUserByUsername(userRequest.getUsername()))
                .thenThrow(new UsernameNotFoundException("No user found with username " + userRequest.getUsername()));

        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> {
                    userDetailsService.loadUserByUsername(userRequest.getUsername());
                });

        // handle api response

        verify(authenticationManager).authenticate(isA(UsernamePasswordAuthenticationToken.class));
        verify(userDetailsService).loadUserByUsername(isA(String.class));
        verifyNoMoreInteractions(userController);
    }

    @Test
    public void test_signin_whenAuthenticateIsValid() throws Exception {

        UserRequest userRequest = new UserRequest("Scalx", "Kutay",
                "123654", "kutay@gmail.com");

        doNothing().when(authenticationManager).authenticate(isA(UsernamePasswordAuthenticationToken.class));

        when(userDetailsService.loadUserByUsername(userRequest.getUsername())).thenReturn(
                new org.springframework.security.core.userdetails.User(
                        userRequest.getUsername(), userRequest.getPassword(), new ArrayList<>()
                )
        );

        String expectedToken = jwtUtil.generateToken(userDetailsService.loadUserByUsername(userRequest.getUsername()));

        var response = mockMvc.perform(post("/api/users/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andReturn().getResponse();

        String responseInString = response.getContentAsString();

        JsonNode jsonNode = objectMapper.readValue(responseInString, JsonNode.class);

        assertThat(jsonNode.get("jwt").asText()).isEqualTo(expectedToken);

        verify(authenticationManager).authenticate(isA(UsernamePasswordAuthenticationToken.class));
        verify(userDetailsService).loadUserByUsername(isA(String.class));
        verify(jwtUtil).generateToken(isA(UserDetails.class));
        verifyNoMoreInteractions(userController);
    }

    // TODO : if works apply the same pattern for other post entity endpoints
    @Test
    public void test_signup_whenUserIsNotPresent() throws Exception {

        doThrow(new NullPointerException())
                .when(userService).addUser(null);

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> {
                    userService.addUser(null);
                });

        var response = mockMvc.perform(post("api/users/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(null)))
                .andReturn().getResponse();

        String responseInString = response.getContentAsString();

        JsonNode jsonNode = objectMapper.readValue(responseInString, JsonNode.class);

        assertThat(jsonNode.get("statusCode").asInt()).isEqualTo(404);
        assertThat(jsonNode.get("message").asText()).isEqualTo("Not found!");
        assertThat(jsonNode.get("date").asText()).isEqualTo(Date.valueOf(LocalDate.now()).toString());

        verify(userService).addUser(isA(User.class));
        verifyNoMoreInteractions(userController);
    }

    @Test
    public void test_signup_whenUserIsPresent() throws Exception {

        UserRequest userRequest = new UserRequest("Scalx", "Kutay",
                "123654", "kutay@gmail.com");

        var response = mockMvc.perform(post("/api/users/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andReturn().getResponse();

        String responseInString = response.getContentAsString();

        JsonNode jsonNode = objectMapper.readValue(responseInString, JsonNode.class);

        assertThat(jsonNode.get("statusCode").asInt()).isEqualTo(200);
        assertThat(jsonNode.get("message").asText()).isEqualTo("Success - Mock for now!");
        assertThat(jsonNode.get("date").asText()).isEqualTo(Date.valueOf(LocalDate.now()).toString());

        User user = fieldSetter.setFieldsWhenCreateWithGivenId(userRequest,
                modelMapper.map(userRequest, User.class), 1);

        verify(userService).addUser(isA(User.class));
        verifyNoMoreInteractions(userController);
    }

    @Test
    public void test_getUserById_whenGetUserByIdIsNotPresent() throws Exception {

        int id = 1;

        when(userService.getUserById(id)).thenReturn(Optional.empty());

        var response = mockMvc.perform(get("/api/likes/{id}", id))
                .andReturn().getResponse();

        String responseInString = response.getContentAsString();

        JsonNode jsonNode = objectMapper.readValue(responseInString, JsonNode.class);

        assertThat(jsonNode.get("statusCode").asInt()).isEqualTo(404);
        assertThat(jsonNode.get("message").asText()).isEqualTo("Not found!");
        assertThat(jsonNode.get("date").asText()).isEqualTo(Date.valueOf(LocalDate.now()).toString());

        verify(userService).getUserById(isA(Integer.class));
        verifyNoMoreInteractions(userController);
    }

    @Test
    public void test_getUserById_whenGetUserByIdIsPresent() throws Exception {

        int id = 1;

        UserRequest userRequest = new UserRequest("Scalx", "Josh", "JoshAndHisPassword",
                "josh@email.com", false, false);

        User user = fieldSetter.setFieldsWhenCreateWithGivenId(userRequest,
                modelMapper.map(userRequest, User.class), 1);

        when(userService.getUserById(id).get()).thenReturn(user);

        var response = mockMvc.perform(get("/api/users/{id}", id))
                .andReturn().getResponse();

        String responseInString = response.getContentAsString();

        JsonNode jsonNode = objectMapper.convertValue(user, JsonNode.class);

        assertThat(jsonNode.get("statusCode").asInt()).isEqualTo(200);
        assertThat(jsonNode.get("message").asText()).isEqualTo("Success - Mock for now!");
        assertThat(jsonNode.get("date").asText()).isEqualTo(Date.valueOf(LocalDate.now()).toString());

        String data = objectMapper.writeValueAsString(user);

        assertThat(jsonNode.get("data").asText()).isEqualTo(data);

        verify(userService).getUserById(isA(Integer.class));
        verifyNoMoreInteractions(userController);
    }
}
