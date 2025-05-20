package com.crm.gym.controllers;

import java.util.List;
import com.crm.gym.entities.TrainingType;
import com.crm.gym.repositories.interfaces.TrainingTypeRepository;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trainingTypes")
@Tag(name = "Training Types", description = "Operations related to training types")
public class TrainingTypeController
{
    TrainingTypeRepository trainingTypeRepository;

    public TrainingTypeController(TrainingTypeRepository trainingTypeRepository)
    {
        this.trainingTypeRepository = trainingTypeRepository;
    }

    // 17. Get Training types
    @Operation(summary = "Get all training types", security = @SecurityRequirement(name = "user_auth"))
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Successful operation",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TrainingType.class))
                    )
            )
    })
    @GetMapping
    public List<TrainingType> getAllTrainingTypes()
    {
        return trainingTypeRepository.findAll();
    }
}