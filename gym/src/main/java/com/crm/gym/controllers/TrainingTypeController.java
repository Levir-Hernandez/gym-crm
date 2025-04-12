package com.crm.gym.controllers;

import java.util.List;
import com.crm.gym.entities.TrainingType;
import com.crm.gym.repositories.interfaces.TrainingTypeRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trainingTypes")
public class TrainingTypeController
{
    TrainingTypeRepository trainingTypeRepository;

    public TrainingTypeController(TrainingTypeRepository trainingTypeRepository)
    {
        this.trainingTypeRepository = trainingTypeRepository;
    }

    @PostMapping
    public void createTrainingType(@RequestBody TrainingType trainingType)
    {
        trainingTypeRepository.create(trainingType);
    }

    @GetMapping
    public List<TrainingType> getAllTrainingTypes()
    {
        return trainingTypeRepository.findAll();
    }

    @GetMapping("/{id}")
    public TrainingType getTrainingById(@PathVariable Long id)
    {
        return trainingTypeRepository.findById(id).orElse(null);
    }
}