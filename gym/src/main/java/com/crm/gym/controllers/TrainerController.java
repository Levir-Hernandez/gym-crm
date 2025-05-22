package com.crm.gym.controllers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.crm.gym.controllers.exceptions.InvalidCredentialsException;
import com.crm.gym.controllers.exceptions.ResourceNotFoundException;
import com.crm.gym.dtos.mappers.interfaces.TrainerMapper;
import com.crm.gym.dtos.trainer.*;
import com.crm.gym.entities.Trainer;
import com.crm.gym.services.TrainerService;
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
@Tag(name = "Trainers", description = "Operations related to trainers")
public class TrainerController
{
    private TrainerService trainerService;
    private TrainerMapper trainerMapper;

    public TrainerController(TrainerService trainerService, TrainerMapper trainerMapper)
    {
        this.trainerService = trainerService;
        this.trainerMapper = trainerMapper;
    }

    // 2. Trainer Registration
    @Operation(
            summary = "Register a new trainer",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Trainer data", required = true,
                    content = @Content(schema = @Schema(implementation = TrainerRegistrationRequest.class))
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201", description = "Trainer registered successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TrainerCredentials.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid trainer data provided", content = @Content)
    })
    @PostMapping("/trainers")
    @ResponseStatus(HttpStatus.CREATED)
    public TrainerCredentials createTrainer(@RequestBody @Valid TrainerRegistrationRequest trainerDto)
    {
        Trainer trainer = trainerMapper.toEntity(trainerDto);
        trainer = trainerService.saveEntity(trainer);
        return trainerMapper.toCredentialsDto(trainer);
    }

    // +. Get all Trainers
    @Operation(summary = "Get all trainers", security = @SecurityRequirement(name = "user_auth"))
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Successful operation",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TrainerProfile.class))
                    )
            )
    })
    @GetMapping("/trainers")
    @ResponseStatus(HttpStatus.OK)
    public List<TrainerProfile> getAllTrainers()
    {
        return trainerService.getAllEntities().stream()
                .map(trainerMapper::toProfileDto)
                .collect(Collectors.toList());
    }

    // 8. Get Trainer Profile
    @Operation(summary = "Get trainer profile", security = @SecurityRequirement(name = "user_auth"))
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Trainer profile retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TrainerProfile.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Trainer not found", content = @Content)
    })
    @GetMapping("/trainers/{username}")
    @ResponseStatus(HttpStatus.OK)
    public TrainerProfile getTrainerByUsername(
            @Parameter(description = "Trainer's username", required = true)
            @PathVariable String username)
    {
        return Optional.ofNullable(trainerService.getUserByUsername(username))
                .map(trainerMapper::toProfileDto)
                .orElseThrow(ResourceNotFoundException::new);
    }

    // 9. Update Trainer Profile
    @Operation(
            summary = "Update trainer profile", security = @SecurityRequirement(name = "user_auth"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true, description = "Updated trainer data",
                    content = @Content(schema = @Schema(implementation = TrainerModificationRequest.class))
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Trainer profile updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TrainerProfile.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Trainer not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid trainer data provided", content = @Content)
    })
    @PutMapping("/trainers/{username}")
    @ResponseStatus(HttpStatus.OK)
    public TrainerProfile updateTrainerByUsername(
            @Parameter(description = "Trainer's username", required = true)
            @PathVariable String username,

            @RequestBody @Valid TrainerModificationRequest trainerDto)
    {
        Trainer trainer = trainerMapper.toEntity(trainerDto);

        return Optional.ofNullable(trainerService.updateUserByUsername(username, trainer))
                .map(trainerMapper::toProfileDto)
                .orElseThrow(ResourceNotFoundException::new);
    }

    // 16. Activate Trainer
    @Operation(summary = "Activate trainer profile", security = @SecurityRequirement(name = "user_auth"))
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Trainer activated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))
            ),
            @ApiResponse(responseCode = "404", description = "Trainer not found", content = @Content)
    })
    @PatchMapping("/trainers/{username}/activate")
    @ResponseStatus(HttpStatus.OK)
    public Boolean activateTrainer(
            @Parameter(description = "Trainer's username", required = true)
            @PathVariable String username)
    {
        return Optional.ofNullable(trainerService.activateUser(username))
                .orElseThrow(ResourceNotFoundException::new);
    }

    // 16. De-Activate Trainer
    @Operation(summary = "Deactivate trainer profile", security = @SecurityRequirement(name = "user_auth"))
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Trainer deactivated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))
            ),
            @ApiResponse(responseCode = "404", description = "Trainer not found", content = @Content)
    })
    @PatchMapping("/trainers/{username}/deactivate")
    @ResponseStatus(HttpStatus.OK)
    public Boolean deactivateTrainer(
            @Parameter(description = "Trainer's username", required = true)
            @PathVariable String username
    )
    {
        return Optional.ofNullable(trainerService.deactivateUser(username))
                .orElseThrow(ResourceNotFoundException::new);
    }

    // 3. Login
    @Operation(summary = "Log in as a trainer")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Credentials verified successfully", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content)
    })
    @PostMapping("/trainers/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(
            @Parameter(description = "Trainer's username", required = true)
            @RequestParam String username,

            @Parameter(description = "Trainer's password", required = true)
            @RequestParam String password)
    {
        boolean logged = trainerService.login(username, password);
        if(!logged) {throw new InvalidCredentialsException();}
    }

    // 4. Change Login
    @Operation(summary = "Change trainer password")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password changed successfully", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content)
    })
    @PutMapping("/trainers/change-password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(
            @Parameter(description = "Trainer's username", required = true)
            @RequestParam String username,

            @Parameter(description = "Current password", required = true)
            @RequestParam String oldPassword,

            @Parameter(description = "New password", required = true)
            @RequestParam String newPassword)
    {
        boolean passwordChanged = trainerService.changePassword(username, oldPassword, newPassword);
        if(!passwordChanged){throw new InvalidCredentialsException();}
    }

    // 10. Get not assigned on trainee active trainers
    @Operation(summary = "Get active trainers not assigned to a specific trainee", security = @SecurityRequirement(name = "user_auth"))
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "List of unassigned active trainers retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TrainerBriefProfile.class))
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Trainee not found", content = @Content)
    })
    @GetMapping("/trainees/{traineeUsername}/trainers/unassigned")
    @ResponseStatus(HttpStatus.OK)
    public List<TrainerBriefProfile> getAllUnassignedForTraineeByUsername(
            @Parameter(description = "Trainee's username", required = true)
            @PathVariable String traineeUsername
    )
    {
        List<Trainer> trainers = trainerService.getAllUnassignedForTraineeByUsername(traineeUsername);
        if(Objects.isNull(trainers)) {throw new ResourceNotFoundException();}

        return trainers.stream().map(trainerMapper::toBriefProfileDto).collect(Collectors.toList());
    }

    // 11. Update Trainee's Trainer List
    @Operation(
            summary = "Update assigned trainers for a trainee", security = @SecurityRequirement(name = "user_auth"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated set of trainers data", required = true,
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TrainerModificationEmbeddedRequest.class)))
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Assigned trainers updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class))),
            @ApiResponse(responseCode = "404", description = "Trainee not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid trainer data provided", content = @Content)
    })
    @PutMapping("/trainees/{traineeUsername}/trainers/assigned")
    @ResponseStatus(HttpStatus.OK)
    public Integer updateAssignedTrainersForTrainee(
            @Parameter(description = "Trainee's username", required = true)
            @PathVariable String traineeUsername,
            @RequestBody Set<@Valid TrainerModificationEmbeddedRequest> trainerDtos)
    {
        Set<Trainer> trainers = trainerDtos.stream().map(trainerMapper::toEntity).collect(Collectors.toSet());

        Integer updatedTrainers = trainerService.updateAssignedTrainersForTrainee(traineeUsername, trainers);
        if(Objects.isNull(updatedTrainers)) {throw new ResourceNotFoundException();}

        return updatedTrainers;
    }
}