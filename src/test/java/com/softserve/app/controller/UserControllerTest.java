package com.softserve.app.controller;

import com.softserve.app.dto.UserDTO;
import com.softserve.app.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;


import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerTest {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String SERVER_URL = "http://localhost:8082/";

    @Test
    public void createUserTest() {
        User user = User.builder()
                .email("iamquel")
                .username("QuelTest")
                .password("norter12")
                .build();

        ResponseEntity<UserDTO> postResponse = restTemplate.postForEntity(
                SERVER_URL + "registration",
                user,
                UserDTO.class
        );

        assertNotNull(postResponse.getBody());
        String createdUserId = postResponse.getBody().getId().toString();

        ResponseEntity<UserDTO> geResponse = restTemplate.getForEntity(
                SERVER_URL + "user/" + createdUserId,
                UserDTO.class
        );
        assertNotNull(geResponse.getBody());


        restTemplate.delete(
                SERVER_URL + "user/" + createdUserId
        );
    }
}
