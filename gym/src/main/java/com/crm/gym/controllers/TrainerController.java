package com.crm.gym.controllers;

import com.crm.gym.entities.Trainer;
import com.crm.gym.services.TrainerService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/new")
    public void createTrainer(@RequestBody Trainer trainer)
    {
        trainerService.saveEntity(trainer);
    }

    @PutMapping("/{id}")
    public void updateTrainerById(@PathVariable Long id, @RequestBody Trainer trainer)
    {
        trainer.setId(id);
        trainerService.updateEntity(trainer);
    }
}