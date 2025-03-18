package com.crm.gym.controllers;

import com.crm.gym.entities.Trainer;
import com.crm.gym.services.TrainerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/trainer")
public class TrainerController
{
    TrainerService trainerService;

    public TrainerController(TrainerService trainerService)
    {
        this.trainerService = trainerService;
    }

    @GetMapping("/{id}")
    public Trainer getTrainerById(@PathVariable Long id)
    {
        return trainerService.getEntityById(id);
    }

    @GetMapping("/all")
    public List<Trainer> getAllTrainers()
    {
        return trainerService.getAllEntities();
    }
}