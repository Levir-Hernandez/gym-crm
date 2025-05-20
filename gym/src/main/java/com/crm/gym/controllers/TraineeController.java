package com.crm.gym.controllers;

import java.util.List;
import java.util.Set;

import com.crm.gym.entities.Trainee;
import com.crm.gym.entities.Trainer;
import com.crm.gym.services.TraineeService;
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
@RequestMapping("/trainees")
@Tag(name = "Trainees", description = "Operations related to trainees")
public class TraineeController
{
    TraineeService traineeService;

    public TraineeController(TraineeService traineeService)
    {
        this.traineeService = traineeService;
    }

    // 1. Trainee Registration
    @Operation(
            summary = "Register a new trainee",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "Trainee data", required = true,
                            content = @Content(schema = @Schema(implementation = Trainee.class))
                    )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201", description = "Trainee registered successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Trainee.class)
                    )
            )
    })
    @PostMapping
    public Trainee createTrainee(@RequestBody Trainee trainee)
    {
        return traineeService.saveEntity(trainee);
    }

    // +. Get all Trainees
    @Operation(summary = "Get all trainees", security = @SecurityRequirement(name = "user_auth"))
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Successful operation",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Trainee.class))
                    )
            )
    })
    @GetMapping
    public List<Trainee> getAllTrainees()
    {
        return traineeService.getAllEntities();
    }

    // 5. Get Trainee Profile
    @Operation(summary = "Get trainee profile", security = @SecurityRequirement(name = "user_auth"))
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Trainee profile retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Trainee.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Trainee not found", content = @Content)
    })
    @GetMapping("/{username}")
    public Trainee getTraineeByUsername(
            @Parameter(description = "Trainee's username", required = true)
            @PathVariable String username)
    {
        return traineeService.getUserByUsername(username);
    }

    // 6. Update Trainee Profile
    @Operation(
            summary = "Update trainee profile", security = @SecurityRequirement(name = "user_auth"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true, description = "Updated trainee data",
                    content = @Content(schema = @Schema(implementation = Trainee.class))
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Trainee profile updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Trainee.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Trainee not found", content = @Content)
    })
    @PutMapping("/{username}")
    public Trainee updateTraineeByUsername(
            @Parameter(description = "Trainee's username", required = true)
            @PathVariable String username,

            @RequestBody Trainee trainee)
    {
        return traineeService.updateUserByUsername(username, trainee);
    }

    // 15. Activate Trainee
    @Operation(summary = "Activate trainee profile", security = @SecurityRequirement(name = "user_auth"))
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Trainee activated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))
            ),
            @ApiResponse(responseCode = "404", description = "Trainee not found", content = @Content)
    })
    @PatchMapping("/{username}/activate")
    public Boolean activateTrainee(
            @Parameter(description = "Trainee's username", required = true)
            @PathVariable String username
    )
    {
        return traineeService.activateUser(username);
    }

    // 15. De-Activate Trainee
    @Operation(summary = "Deactivate trainee profile", security = @SecurityRequirement(name = "user_auth"))
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Trainee deactivated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))
            ),
            @ApiResponse(responseCode = "404", description = "Trainee not found", content = @Content)
    })
    @PatchMapping("/{username}/deactivate")
    public Boolean deactivateTrainee(
            @Parameter(description = "Trainee's username", required = true)
            @PathVariable String username
    )
    {
        return traineeService.deactivateUser(username);
    }

    // 7. Delete Trainee Profile
    @Operation(summary = "Delete trainee profile", security = @SecurityRequirement(name = "user_auth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Trainee deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Trainee not found", content = @Content)
    })
    @DeleteMapping("/{username}")
    public boolean deleteTraineeByUsername(
            @Parameter(description = "Trainee's username", required = true)
            @PathVariable String username
    )
    {
        return traineeService.deleteTraineeByUsername(username);
    }

    // 3. Login
    @Operation(summary = "Log in as a trainee")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Credentials verified successfully", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content)
    })
    @PostMapping("/login")
    public boolean login(
            @Parameter(description = "Trainee's username", required = true)
            @RequestParam String username,

            @Parameter(description = "Trainee's password", required = true)
            @RequestParam String password)
    {
        return traineeService.login(username, password);
    }

    // 4. Change Login
    @Operation(summary = "Change trainee password")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password changed successfully", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content)
    })
    @PutMapping("/change-password")
    public boolean changePassword(
            @Parameter(description = "Trainee's username", required = true)
            @RequestParam String username,

            @Parameter(description = "Current password", required = true)
            @RequestParam String oldPassword,

            @Parameter(description = "New password", required = true)
            @RequestParam String newPassword)
    {
        return traineeService.changePassword(username, oldPassword, newPassword);
    }

    // 11. Update Trainee's Trainer List
    @Operation(
            summary = "Update assigned trainers for a trainee", security = @SecurityRequirement(name = "user_auth"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated set of trainers data", required = true,
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Trainer.class)))
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Assigned trainers updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class))),
            @ApiResponse(responseCode = "404", description = "Trainee not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid trainer data provided", content = @Content)
    })
    @PutMapping("/{traineeUsername}/trainers/assigned")
    public int updateAssignedTrainersForTrainee(
            @Parameter(description = "Trainee's username", required = true)
            @PathVariable String traineeUsername,
            @RequestBody Set<Trainer> trainers)
    {
        return traineeService.updateAssignedTrainersForTrainee(traineeUsername, trainers);
    }
}
