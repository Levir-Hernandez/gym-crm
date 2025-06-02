package com.crm.gym.controllers;

import com.crm.gym.dtos.trainee.TraineeModificationRequest;
import com.crm.gym.dtos.trainee.TraineeRegistrationRequest;
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

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class TraineeControllerTest
{
    private final static String CREDENTIALS = "user.test:1234";
    private static String TOKEN;

    @Autowired
    public TraineeControllerTest(TraineeService traineeService)
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
    @DisplayName("Tests HTTP 200 & 400 on POST /trainees")
    void createTrainee()
    {
        String traineeFirstname = "Larry";
        String traineeLastname = "Williams";

        TraineeRegistrationRequest traineeDto = new TraineeRegistrationRequest();
        traineeDto.setFirstname(traineeFirstname);
        traineeDto.setLastname(traineeLastname);
        traineeDto.setBirthdate(LocalDate.parse("1991-03-21"));
        traineeDto.setAddress("123 Harlem St");

        // 200 OK

        given()
            .accept(ContentType.JSON)
            .body(traineeDto)
            .contentType(ContentType.JSON)
        .when()
            .post("/trainees")
        .then()
            .statusCode(201)
            .body("username", equalTo(traineeFirstname+"."+traineeLastname));

        // 400 BAD_REQUEST

        traineeDto.setFirstname(null);

        given()
            .accept(ContentType.JSON)
            .body(traineeDto)
            .contentType(ContentType.JSON)
        .when()
            .post("/trainees")
        .then()
            .statusCode(400);
    }

    @Test
    @DisplayName("Tests HTTP 200 on GET /trainees")
    void getAllTrainees()
    {
        given()
            .header("Authorization", "Basic " + TOKEN)
            .accept(ContentType.JSON)
        .when()
            .get("/trainees")
        .then()
            .statusCode(200);
    }

    @Test
    @DisplayName("Tests HTTP 200 & 404 on GET /trainees/{username}")
    void getTraineeByUsername()
    {
        // 200 OK

        String firstname = "Alice";
        String lastname = "Smith";
        String username = firstname+"."+lastname;

        given()
            .header("Authorization", "Basic " + TOKEN)
            .accept(ContentType.JSON)
        .when()
            .get("/trainees/{username}", username)
        .then()
            .statusCode(200)
            .body("firstname", equalTo(firstname))
            .body("lastname", equalTo(lastname));

        // 400 NOT_FOUND

        username = "Unknown.Unknown";

        given()
            .header("Authorization", "Basic " + TOKEN)
            .accept(ContentType.JSON)
        .when()
            .get("/trainees/{username}", username)
        .then()
            .statusCode(404);
    }

    @Test
    @DisplayName("Tests HTTP 200 & 400 on PUT /trainees/{username}")
    void updateTraineeByUsername()
    {
        String username = "Diana.Miller";
        String newTraineeFirstname = "Dina";
        String newTraineeLastname = "Merrill";
        TraineeModificationRequest traineeDto = new TraineeModificationRequest();
        traineeDto.setFirstname(newTraineeFirstname);
        traineeDto.setLastname(newTraineeLastname);
        traineeDto.setBirthdate(LocalDate.parse("1995-04-19"));
        traineeDto.setAddress("123 Magnolia St");
        traineeDto.setIsActive(false);

        // 200 OK

        given()
            .header("Authorization", "Basic " + TOKEN)
            .accept(ContentType.JSON)
            .body(traineeDto)
            .contentType(ContentType.JSON)
        .when()
            .put("/trainees/{username}", username)
        .then()
            .statusCode(200)
            .body("firstname", equalTo(newTraineeFirstname))
            .body("lastname", equalTo(newTraineeLastname));

        // 400 BAD_REQUEST

        traineeDto.setFirstname(null);

        given()
            .header("Authorization", "Basic " + TOKEN)
            .accept(ContentType.JSON)
            .body(traineeDto)
            .contentType(ContentType.JSON)
        .when()
            .put("/trainees/{username}", username)
        .then()
            .statusCode(400);
    }

    @Test
    @DisplayName("Tests HTTP 200 & 404 on PATCH /trainees/{username}/activate")
    void activateTrainee()
    {
        // 200 OK

        String username = "Alice.Smith";
        given()
            .header("Authorization", "Basic " + TOKEN)
            .accept(ContentType.JSON)
        .when()
            .patch("/trainees/{username}/activate", username)
        .then()
            .statusCode(200);

        // 404 NOT_FOUND

        username = "Unknown.Unknown";
        given()
            .header("Authorization", "Basic " + TOKEN)
            .accept(ContentType.JSON)
        .when()
            .patch("/trainees/{username}/activate", username)
        .then()
            .statusCode(404);
    }

    @Test
    @DisplayName("Tests HTTP 200 & 404 on PATCH /trainees/{username}/deactivate")
    void deactivateTrainee()
    {
        // 200 OK

        String username = "Alice.Smith";
        given()
            .header("Authorization", "Basic " + TOKEN)
            .accept(ContentType.JSON)
        .when()
            .patch("/trainees/{username}/deactivate", username)
        .then()
            .statusCode(200);

        // 404 NOT_FOUND

        username = "Unknown.Unknown";
        given()
            .header("Authorization", "Basic " + TOKEN)
            .accept(ContentType.JSON)
        .when()
            .patch("/trainees/{username}/activate", username)
        .then()
            .statusCode(404);
    }

    @Test
    @DisplayName("Tests HTTP 204 & 404 on DELETE /trainees/{username}")
    void deleteTraineeByUsername()
    {
        // 204 NO_CONTENT

        String username = "Ethan.Davis";
        given()
            .header("Authorization", "Basic " + TOKEN)
            .accept(ContentType.JSON)
        .when()
            .delete("/trainees/{username}", username)
        .then()
            .statusCode(204);

        // 404 NOT_FOUND

        username = "Unknown.Unknown";
        given()
            .header("Authorization", "Basic " + TOKEN)
            .accept(ContentType.JSON)
        .when()
            .delete("/trainees/{username}", username)
        .then()
            .statusCode(404);
    }

    @Test
    @DisplayName("Tests HTTP 200 & 401 on POST trainees/login")
    void login()
    {
        // 200 OK

        String username = "user.test";
        String password = "1234";

        given()
            .accept(ContentType.JSON)
            .queryParam("username", username)
            .queryParam("password", password)
        .when()
            .post("/trainees/login")
        .then()
            .statusCode(200);

        // 401 UNAUTHORIZED

        username = "invalid.invalid";
        password = "invalid";

        given()
                .accept(ContentType.JSON)
                .queryParam("username", username)
                .queryParam("password", password)
                .when()
                .post("/trainees/login")
                .then()
                .statusCode(401);
    }

    @Test
    @DisplayName("Tests HTTP 200 & 401 on PUT trainees/change-password")
    void changePassword()
    {
        // 200 OK

        String username = "user.test";
        String oldPassword = "1234";
        String newPassword = "1234";

        given()
            .accept(ContentType.JSON)
            .queryParam("username", username)
            .queryParam("oldPassword", oldPassword)
            .queryParam("newPassword", newPassword)
        .when()
            .put("/trainees/change-password")
        .then()
            .statusCode(200);

        // 401 UNAUTHORIZED

        username = "invalid.invalid";

        given()
            .accept(ContentType.JSON)
            .queryParam("username", username)
            .queryParam("oldPassword", oldPassword)
            .queryParam("newPassword", newPassword)
        .when()
            .put("/trainees/change-password")
        .then()
            .statusCode(401);
    }
}