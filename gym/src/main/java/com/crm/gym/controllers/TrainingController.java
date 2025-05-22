package com.crm.gym.controllers;

import java.util.List;
import java.time.LocalDate;
import java.util.stream.Collectors;

import com.crm.gym.dtos.mappers.interfaces.TrainingMapper;
import com.crm.gym.dtos.training.TrainingDetails;
import com.crm.gym.dtos.training.TrainingScheduleRequest;
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
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Trainings", description = "Operations related to training sessions")
public class TrainingController
{
    private TrainingService trainingService;
    private TrainingMapper trainingMapper;

    public TrainingController(TrainingService trainingService, TrainingMapper trainingMapper)
    {
        this.trainingService = trainingService;
        this.trainingMapper = trainingMapper;
    }

    // 14. Add Training
    @Operation(
            summary = "Add a new training session", security = @SecurityRequirement(name = "user_auth"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Training data", required = true,
                    content = @Content(schema = @Schema(implementation = TrainingScheduleRequest.class))
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Training created successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid training data provided", content = @Content)
    })
    @PostMapping("/trainings")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTraining(@RequestBody @Valid TrainingScheduleRequest trainingDto)
    {
        Training training = trainingMapper.toEntity(trainingDto);
        trainingService.saveEntity(training);
    }

    // +. Get all Trainings
    @Operation(summary = "Get all trainings", security = @SecurityRequirement(name = "user_auth"))
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Successful operation",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TrainingDetails.class))
                    )
            )
    })
    @GetMapping("/trainings")
    @ResponseStatus(HttpStatus.OK)
    public List<TrainingDetails> getAllTrainings()
    {
        return trainingService.getAllEntities().stream()
                .map(trainingMapper::toDetailsDto).collect(Collectors.toList());
    }

    // 12. Get Trainee Trainings List
    @Operation(summary = "Get trainings for a trainee", security = @SecurityRequirement(name = "user_auth"),
            description = "Returns a list of trainings for a specific trainee with optional filters")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Successful operation",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TrainingDetails.class))
                    )
            )
    })
    @GetMapping("/trainees/{traineeUsername}/trainings")
    @ResponseStatus(HttpStatus.OK)
    public List<TrainingDetails> getTrainingsByTraineeUsernameAndCriteria(
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
        return trainingService.getTrainingsByTraineeUsernameAndCriteria(criteria).stream()
                .map(trainingMapper::toDetailsDto).collect(Collectors.toList());
    }

    // 13. Get Trainer Trainings List
    @Operation(summary = "Get trainings for a trainer", security = @SecurityRequirement(name = "user_auth"),
            description = "Returns a list of trainings for a specific trainer with optional filters")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Successful operation",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TrainingDetails.class))
                    )
            )
    })
    @GetMapping("/trainers/{trainerUsername}/trainings")
    @ResponseStatus(HttpStatus.OK)
    public List<TrainingDetails> getTrainingsByTrainerUsernameAndCriteria(
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
        return trainingService.getTrainingsByTrainerUsernameAndCriteria(criteria).stream()
                .map(trainingMapper::toDetailsDto).collect(Collectors.toList());
    }
}
