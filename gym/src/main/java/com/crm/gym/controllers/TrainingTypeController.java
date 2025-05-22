package com.crm.gym.controllers;

import java.util.List;
import java.util.stream.Collectors;
import com.crm.gym.dtos.trainingType.TrainingTypeDto;
import com.crm.gym.dtos.mappers.interfaces.TrainingTypeMapper;
import com.crm.gym.repositories.interfaces.TrainingTypeRepository;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trainingTypes")
@Tag(name = "Training Types", description = "Operations related to training types")
public class TrainingTypeController
{
    private TrainingTypeRepository trainingTypeRepository;
    private TrainingTypeMapper trainingTypeMapper;

    public TrainingTypeController(TrainingTypeRepository trainingTypeRepository, TrainingTypeMapper trainingTypeMapper)
    {
        this.trainingTypeRepository = trainingTypeRepository;
        this.trainingTypeMapper = trainingTypeMapper;
    }

    // 17. Get Training types
    @Operation(summary = "Get all training types", security = @SecurityRequirement(name = "user_auth"))
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Successful operation",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TrainingTypeDto.class))
                    )
            )
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TrainingTypeDto> getAllTrainingTypes()
    {
        return trainingTypeRepository.findAll().stream()
                .map(trainingTypeMapper::toDto).collect(Collectors.toList());
    }
}