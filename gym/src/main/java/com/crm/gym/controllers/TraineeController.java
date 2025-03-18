package com.crm.gym.controllers;

import java.util.List;
import com.crm.gym.entities.Trainee;
import com.crm.gym.services.TraineeService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trainee")
public class TraineeController
{
    TraineeService traineeService;

    public TraineeController(TraineeService traineeService)
    {
        this.traineeService = traineeService;
    }

    @GetMapping("/{id}")
    public Trainee getTraineeById(@PathVariable Long id)
    {
        return traineeService.getEntityById(id);
    }

    @GetMapping("/all")
    public List<Trainee> getAllTrainees()
    {
        return traineeService.getAllEntities();
    }
}
