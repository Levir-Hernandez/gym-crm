package com.crm.gym.controllers;

import com.crm.gym.entities.Trainee;
import com.crm.gym.services.TraineeService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Base64;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class TrainingTypeControllerTest
{
    private final static String CREDENTIALS = "user.test:1234";
    private static String TOKEN;

    @Autowired
    public TrainingTypeControllerTest(TraineeService traineeService)
    {
        Trainee trainee = new Trainee(null, "user", "test", null, null, null, null, null);
        trainee = traineeService.saveEntity(trainee);
        traineeService.changePassword(trainee.getUsername(), trainee.getPassword(), "1234");
    }

    @BeforeAll
    static void beforeAll()
    {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;

        TOKEN = Base64.getEncoder().encodeToString(CREDENTIALS.getBytes());
    }

    @Test
    @DisplayName("Tests HTTP 200 on GET /trainingTypes")
    void getAllTrainingTypes()
    {
        given()
            .header("Authorization", "Basic " + TOKEN)
            .accept(ContentType.JSON)
        .when()
            .get("/trainingTypes")
        .then()
            .statusCode(200);
    }
}