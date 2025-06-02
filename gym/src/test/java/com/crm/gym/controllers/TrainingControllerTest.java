package com.crm.gym.controllers;

import com.crm.gym.dtos.training.TrainingScheduleRequest;
import com.crm.gym.entities.Trainee;
import com.crm.gym.services.TraineeService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Base64;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class TrainingControllerTest
{
    private final static String CREDENTIALS = "user.test:1234";
    private static String TOKEN;

    @Autowired
    public TrainingControllerTest(TraineeService traineeService)
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
    @DisplayName("Tests HTTP 200 & 400 on POST /trainings")
    void createTraining()
    {
        TrainingScheduleRequest trainingDto = new TrainingScheduleRequest();
        trainingDto.setName("Morning Fitness Blast");
        trainingDto.setTrainingType("Fitness");
        trainingDto.setDate(LocalDate.parse("2025-06-21"));
        trainingDto.setDuration(30);
        trainingDto.setTrainerUsername("John.Doe");
        trainingDto.setTraineeUsername("Alice.Smith");

        // 200 OK

        given()
            .header("Authorization", "Basic " + TOKEN)
            .accept(ContentType.JSON)
            .body(trainingDto)
            .contentType(ContentType.JSON)
        .when()
            .post("/trainings")
        .then()
            .statusCode(201);

        // 400 BAD_REQUEST

        trainingDto.setTraineeUsername(null);

        given()
            .header("Authorization", "Basic " + TOKEN)
            .accept(ContentType.JSON)
            .body(trainingDto)
            .contentType(ContentType.JSON)
        .when()
            .post("/trainings")
        .then()
            .statusCode(400);
    }

    @Test
    @DisplayName("Tests HTTP 200 on GET /trainings")
    void getAllTrainings()
    {
        given()
            .header("Authorization", "Basic " + TOKEN)
            .accept(ContentType.JSON)
        .when()
            .get("/trainings")
        .then()
            .statusCode(200);
    }

    @Test
    @DisplayName("Tests HTTP 200 on GET /trainees/{traineeUsername}/trainings")
    void getTrainingsByTraineeUsernameAndCriteria()
    {
        String trainerUsername = "John.Doe";
        String traineeUsername = "Alice.Smith";
        String trainingTypeName = "Fitness";

        given()
            .header("Authorization", "Basic " + TOKEN)
            .accept(ContentType.JSON)
            .queryParam("trainerUsername", trainerUsername)
            .queryParam("fromDate", "2025-01-01")
            .queryParam("toDate", "2025-12-31")
            .queryParam("trainingTypeName", trainingTypeName)
        .when()
            .get("/trainees/{traineeUsername}/trainings", traineeUsername)
        .then()
            .statusCode(200)
            .rootPath("_embedded.trainings")
            .body("trainerUsername", everyItem(equalTo(trainerUsername)))
            .body("traineeUsername", everyItem(equalTo(traineeUsername)))
            .body("trainingType", everyItem(equalTo(trainingTypeName)));
    }

    @Test
    @DisplayName("Tests HTTP 200 on GET /trainers/{trainerUsername}/trainings")
    void getTrainingsByTrainerUsernameAndCriteria()
    {
        String traineeUsername = "Alice.Smith";
        String trainerUsername = "John.Doe";
        String trainingTypeName = "Fitness";

        given()
            .header("Authorization", "Basic " + TOKEN)
            .accept(ContentType.JSON)
            .queryParam("traineeUsername", traineeUsername)
            .queryParam("fromDate", "2025-01-01")
            .queryParam("toDate", "2025-12-31")
            .queryParam("trainingTypeName", trainingTypeName)
        .when()
            .get("/trainers/{trainerUsername}/trainings", trainerUsername)
        .then()
            .statusCode(200)
            .rootPath("_embedded.trainings")
            .body("trainerUsername", everyItem(equalTo(trainerUsername)))
            .body("traineeUsername", everyItem(equalTo(traineeUsername)))
            .body("trainingType", everyItem(equalTo(trainingTypeName)));
    }
}