package com.crm.gym.controllers;

import com.crm.gym.entities.Training;
import com.crm.gym.services.TrainingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/training")
public class TrainingController
{
    TrainingService trainingService;

    public TrainingController(TrainingService trainingService)
    {
        this.trainingService = trainingService;
    }

    @GetMapping("/{id}")
    public Training getTrainingById(@PathVariable Long id)
    {
        return trainingService.getEntityById(id);
    }

    @GetMapping("/all")
    public List<Training> getAllTrainings()
    {
        return trainingService.getAllEntities();
    }
}
