package com.crm.gym.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Trainers", description = "Operations related to trainers")
public class TrainerController
{
    TrainerService trainerService;

    public TrainerController(TrainerService trainerService)
    {
        this.trainerService = trainerService;
    }

    // 2. Trainer Registration
    @Operation(
            summary = "Register a new trainer",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Trainer data", required = true,
                    content = @Content(schema = @Schema(implementation = Trainer.class))
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201", description = "Trainer registered successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Trainer.class)
                    )
            )
    })
    @PostMapping("/trainers")
    public void createTrainer(@RequestBody Trainer trainer)
    {
        trainerService.saveEntity(trainer);
    }

    // +. Get all Trainers
    @Operation(summary = "Get all trainers", security = @SecurityRequirement(name = "user_auth"))
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Successful operation",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Trainer.class))
                    )
            )
    })
    @GetMapping("/trainers")
    public List<Trainer> getAllTrainers()
    {
        return trainerService.getAllEntities();
    }

    // 8. Get Trainer Profile
    @Operation(summary = "Get trainer profile", security = @SecurityRequirement(name = "user_auth"))
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Trainer profile retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Trainer.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Trainer not found", content = @Content)
    })
    @GetMapping("/trainers/{username}")
    public Trainer getTrainerByUsername(
            @Parameter(description = "Trainer's username", required = true)
            @PathVariable String username)
    {
        return trainerService.getUserByUsername(username);
    }

    // 9. Update Trainer Profile
    @Operation(
            summary = "Update trainer profile", security = @SecurityRequirement(name = "user_auth"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true, description = "Updated trainer data",
                    content = @Content(schema = @Schema(implementation = Trainer.class))
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Trainer profile updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Trainer.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Trainer not found", content = @Content)
    })
    @PutMapping("/trainers/{username}")
    public Trainer updateTrainerByUsername(
            @Parameter(description = "Trainer's username", required = true)
            @PathVariable String username,

            @RequestBody Trainer trainer)
    {
        return trainerService.updateUserByUsername(username, trainer);
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
    public Boolean activateTrainee(
            @Parameter(description = "Trainer's username", required = true)
            @PathVariable String username)
    {
        return trainerService.activateUser(username);
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
    public Boolean deactivateTrainee(
            @Parameter(description = "Trainer's username", required = true)
            @PathVariable String username
    )
    {
        return trainerService.deactivateUser(username);
    }

    // 3. Login
    @Operation(summary = "Log in as a trainer")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Credentials verified successfully", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content)
    })
    @PostMapping("/trainers/login")
    public boolean login(
            @Parameter(description = "Trainer's username", required = true)
            @RequestParam String username,

            @Parameter(description = "Trainer's password", required = true)
            @RequestParam String password)
    {
        return trainerService.login(username, password);
    }

    // 4. Change Login
    @Operation(summary = "Change trainer password")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password changed successfully", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content)
    })
    @PutMapping("/trainers/change-password")
    public boolean changePassword(
            @Parameter(description = "Trainer's username", required = true)
            @RequestParam String username,

            @Parameter(description = "Current password", required = true)
            @RequestParam String oldPassword,

            @Parameter(description = "New password", required = true)
            @RequestParam String newPassword)
    {
        return trainerService.changePassword(username, oldPassword, newPassword);
    }

    // 10. Get not assigned on trainee active trainers
    @Operation(summary = "Get active trainers not assigned to a specific trainee", security = @SecurityRequirement(name = "user_auth"))
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "List of unassigned active trainers retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Trainer.class))
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Trainee not found", content = @Content)
    })
    @GetMapping("/trainees/{traineeUsername}/trainers/unassigned")
    public List<Trainer> getAllUnassignedForTraineeByUsername(
            @Parameter(description = "Trainee's username", required = true)
            @PathVariable String traineeUsername
    )
    {
        return trainerService.getAllUnassignedForTraineeByUsername(traineeUsername);
    }
}