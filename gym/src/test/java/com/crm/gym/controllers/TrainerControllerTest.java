package com.crm.gym.controllers;

import com.crm.gym.dtos.trainer.TrainerModificationEmbeddedRequest;
import com.crm.gym.dtos.trainer.TrainerModificationRequest;
import com.crm.gym.dtos.trainer.TrainerRegistrationRequest;
import com.crm.gym.entities.Trainer;
import com.crm.gym.services.TrainerService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Base64;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class TrainerControllerTest
{
    private final static String CREDENTIALS = "user.test:1234";
    private static String TOKEN;

    @Autowired
    public TrainerControllerTest(TrainerService trainerService)
    {
        Trainer trainer = new Trainer(null, "user", "test", null, null, null, null);
        trainer = trainerService.saveEntity(trainer);
        trainerService.changePassword(trainer.getUsername(), trainer.getPassword(), "1234");
    }

    @BeforeAll
    static void beforeAll()
    {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;

        TOKEN = Base64.getEncoder().encodeToString(CREDENTIALS.getBytes());
    }

    @Test
    @DisplayName("Tests HTTP 200 & 400 on POST /trainers")
    void createTrainer()
    {
        String traineeFirstname = "Larry";
        String traineeLastname = "Williams";

        TrainerRegistrationRequest trainerDto = new TrainerRegistrationRequest();
        trainerDto.setFirstname(traineeFirstname);
        trainerDto.setLastname(traineeLastname);
        trainerDto.setSpecialization("Yoga");

        // 200 OK

        given()
            .accept(ContentType.JSON)
            .body(trainerDto)
            .contentType(ContentType.JSON)
        .when()
            .post("/trainers")
        .then()
            .statusCode(201)
            .body("username", equalTo(traineeFirstname+"."+traineeLastname));

        // 400 BAD_REQUEST

        trainerDto.setFirstname(null);

        given()
            .accept(ContentType.JSON)
            .body(trainerDto)
            .contentType(ContentType.JSON)
        .when()
            .post("/trainers")
        .then()
            .statusCode(400);
    }

    @Test
    @DisplayName("Tests HTTP 200 on GET /trainers")
    void getAllTrainers()
    {
        given()
            .header("Authorization", "Basic " + TOKEN)
            .accept(ContentType.JSON)
        .when()
            .get("/trainers")
        .then()
            .statusCode(200);
    }

    @Test
    @DisplayName("Tests HTTP 200 & 404 on GET /trainers/{username}")
    void getTrainerByUsername()
    {
        // 200 OK

        String firstname = "Tom";
        String lastname = "Anderson";
        String username = firstname+"."+lastname;

        given()
            .header("Authorization", "Basic " + TOKEN)
            .accept(ContentType.JSON)
        .when()
            .get("/trainers/{username}", username)
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
            .get("/trainers/{username}", username)
        .then()
            .statusCode(404);
    }

    @Test
    @DisplayName("Tests HTTP 200 & 400 on PUT /trainers/{username}")
    void updateTrainerByUsername()
    {
        String username = "Laura.Williams";
        String newTraineeFirstname = "Mary";
        String newTraineeLastname = "Rosebud";
        TrainerModificationRequest trainerDto = new TrainerModificationRequest();
        trainerDto.setFirstname(newTraineeFirstname);
        trainerDto.setLastname(newTraineeLastname);
        trainerDto.setSpecialization("Zumba");
        trainerDto.setIsActive(true);

        // 200 OK

        given()
            .header("Authorization", "Basic " + TOKEN)
            .accept(ContentType.JSON)
            .body(trainerDto)
            .contentType(ContentType.JSON)
        .when()
            .put("/trainers/{username}", username)
        .then()
            .statusCode(200)
            .body("firstname", equalTo(newTraineeFirstname))
            .body("lastname", equalTo(newTraineeLastname));

        // 400 BAD_REQUEST

        trainerDto.setFirstname(null);

        given()
            .header("Authorization", "Basic " + TOKEN)
            .accept(ContentType.JSON)
            .body(trainerDto)
            .contentType(ContentType.JSON)
        .when()
            .put("/trainers/{username}", username)
        .then()
            .statusCode(400);
    }

    @Test
    @DisplayName("Tests HTTP 200 & 404 on PATCH /trainers/{username}/activate")
    void activateTrainer()
    {
        // 200 OK

        String username = "Laura.Williams";
        given()
            .header("Authorization", "Basic " + TOKEN)
            .accept(ContentType.JSON)
        .when()
            .patch("/trainers/{username}/activate", username)
        .then()
            .statusCode(200);

        // 404 NOT_FOUND

        username = "Unknown.Unknown";
        given()
            .header("Authorization", "Basic " + TOKEN)
            .accept(ContentType.JSON)
        .when()
            .patch("/trainers/{username}/activate", username)
        .then()
            .statusCode(404);
    }

    @Test
    @DisplayName("Tests HTTP 200 & 404 on PATCH /trainers/{username}/deactivate")
    void deactivateTrainer()
    {
        // 200 OK

        String username = "Tom.Anderson";
        given()
            .header("Authorization", "Basic " + TOKEN)
            .accept(ContentType.JSON)
        .when()
            .patch("/trainers/{username}/deactivate", username)
        .then()
            .statusCode(200);

        // 404 NOT_FOUND

        username = "Unknown.Unknown";
        given()
            .header("Authorization", "Basic " + TOKEN)
            .accept(ContentType.JSON)
        .when()
            .patch("/trainers/{username}/activate", username)
        .then()
            .statusCode(404);
    }

    @Test
    @DisplayName("Tests HTTP 200 & 401 on POST trainers/login")
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
                .post("/trainers/login")
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
                .post("/trainers/login")
                .then()
                .statusCode(401);
    }

    @Test
    @DisplayName("Tests HTTP 200 & 401 on PUT trainers/change-password")
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
                .put("/trainers/change-password")
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
                .put("/trainers/change-password")
                .then()
                .statusCode(401);
    }

    @Test
    @DisplayName("Tests HTTP 200 & 404 on GET /trainees/{traineeUsername}/trainers/unassigned")
    void getAllUnassignedForTraineeByUsername()
    {
        // 200 OK

        String traineeUsername = "Alice.Smith";
        given()
            .header("Authorization", "Basic " + TOKEN)
            .accept(ContentType.JSON)
        .when()
            .get("/trainees/{traineeUsername}/trainers/unassigned", traineeUsername)
        .then()
            .statusCode(200)
            .body("username", containsInAnyOrder("Mike.Johnson", "Laura.Williams", "Larry.Williams"));

        // 404 NOT_FOUND

        traineeUsername = "Unknown.Unknown";
        given()
            .header("Authorization", "Basic " + TOKEN)
            .accept(ContentType.JSON)
        .when()
            .get("/trainees/{traineeUsername}/trainers/unassigned", traineeUsername)
        .then()
            .statusCode(404);
    }

    @Test
    @DisplayName("Tests HTTP 200 & 400 & 404 on PUT /trainees/{traineeUsername}/trainers/assigned")
    void updateAssignedTrainersForTrainee()
    {
        String traineeUsername = "Alice.Smith";

        TrainerModificationEmbeddedRequest trainerDto = new TrainerModificationEmbeddedRequest();
        trainerDto.setUsername("Jane.Smith");
        trainerDto.setFirstname("Jennifer");
        trainerDto.setLastname("Brown");
        trainerDto.setSpecialization("Fitness");
        trainerDto.setIsActive(false);

        Set<TrainerModificationEmbeddedRequest> trainerDtos = Set.of(trainerDto);

        given()
            .header("Authorization", "Basic " + TOKEN)
            .accept(ContentType.JSON)
            .body(trainerDtos)
            .contentType(ContentType.JSON)
        .when()
            .put("/trainees/{traineeUsername}/trainers/assigned", traineeUsername)
        .then()
            .statusCode(200);

        // 400 BAD_REQUEST

        trainerDto.setUsername(null);

        given()
            .header("Authorization", "Basic " + TOKEN)
            .accept(ContentType.JSON)
            .body(trainerDtos)
            .contentType(ContentType.JSON)
        .when()
            .put("/trainees/{traineeUsername}/trainers/assigned", traineeUsername)
        .then()
            .statusCode(400);

        // 404 NOT_FOUND

        traineeUsername = "Unknown.Unknown";
        trainerDto.setUsername("Jane.Smith");

        given()
            .header("Authorization", "Basic " + TOKEN)
            .accept(ContentType.JSON)
            .body(trainerDtos)
            .contentType(ContentType.JSON)
        .when()
            .put("/trainees/{traineeUsername}/trainers/assigned", traineeUsername)
        .then()
            .statusCode(404);
    }
}