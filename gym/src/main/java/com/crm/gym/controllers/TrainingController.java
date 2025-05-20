package com.crm.gym.controllers;

import java.util.List;
import java.time.LocalDate;
import com.crm.gym.entities.Training;
import com.crm.gym.services.TrainingService;
import com.crm.gym.repositories.TrainingQueryCriteria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Trainings", description = "Operations related to training sessions")
public class TrainingController
{
    TrainingService trainingService;

    public TrainingController(TrainingService trainingService)
    {
        this.trainingService = trainingService;
    }

    // 14. Add Training
    @Operation(
            summary = "Add a new training session", security = @SecurityRequirement(name = "user_auth"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Training data", required = true,
                    content = @Content(schema = @Schema(implementation = Training.class))
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Training created successfully")
    })
    @PostMapping("/trainings")
    public void createTraining(@RequestBody Training training)
    {
        trainingService.saveEntity(training);
    }

    // +. Get all Trainings
    @Operation(summary = "Get all trainings", security = @SecurityRequirement(name = "user_auth"))
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Successful operation",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Training.class))
                    )
            )
    })
    @GetMapping("/trainings")
    public List<Training> getAllTrainings()
    {
        return trainingService.getAllEntities();
    }

    // 12. Get Trainee Trainings List
    @Operation(summary = "Get trainings for a trainee", security = @SecurityRequirement(name = "user_auth"),
            description = "Returns a list of trainings for a specific trainee with optional filters")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Successful operation",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Training.class))
                    )
            )
    })
    @GetMapping("/trainees/{traineeUsername}/trainings")
    public List<Training> getTrainingsByTraineeUsernameAndCriteria(
            @Parameter(description = "Filter by trainee's username", required = true)
            @PathVariable String traineeUsername,

            @Parameter(description = "Filter by trainer's username")
            @RequestParam(required = false) String trainerUsername,

            @Parameter(description = "Filter trainings from this start date", example = "2025-01-01")
            @RequestParam(required = false) LocalDate fromDate,

            @Parameter(description = "Filter trainings until this end date", example = "2025-12-31")
            @RequestParam(required = false) LocalDate toDate,

            @Parameter(description = "Filter by training type name", example = "Fitness")
            @RequestParam(required = false) String trainingTypeName)
    {
        TrainingQueryCriteria criteria = TrainingQueryCriteria.builder()
                .traineeUsername(traineeUsername)
                .trainerUsername(trainerUsername)
                .fromDate(fromDate)
                .toDate(toDate)
                .trainingTypeName(trainingTypeName)
                .build();
        return trainingService.getTrainingsByTraineeUsernameAndCriteria(criteria);
    }

    // 13. Get Trainer Trainings List
    @Operation(summary = "Get trainings for a trainer", security = @SecurityRequirement(name = "user_auth"),
            description = "Returns a list of trainings for a specific trainer with optional filters")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Successful operation",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Training.class))
                    )
            )
    })
    @GetMapping("/trainers/{trainerUsername}/trainings")
    public List<Training> getTrainingsByTrainerUsernameAndCriteria(
            @Parameter(description = "Filter by trainer's username", required = true)
            @PathVariable String trainerUsername,

            @Parameter(description = "Filter by trainee's username")
            @RequestParam(required = false) String traineeUsername,

            @Parameter(description = "Filter trainings from this start date", example = "2025-01-01")
            @RequestParam(required = false) LocalDate fromDate,

            @Parameter(description = "Filter trainings until this end date", example = "2025-12-31")
            @RequestParam(required = false) LocalDate toDate)
    {
        TrainingQueryCriteria criteria = TrainingQueryCriteria.builder()
                .trainerUsername(trainerUsername)
                .traineeUsername(traineeUsername)
                .fromDate(fromDate)
                .toDate(toDate)
                .build();
        return trainingService.getTrainingsByTrainerUsernameAndCriteria(criteria);
    }
}
