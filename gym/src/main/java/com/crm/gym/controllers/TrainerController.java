package com.crm.gym.controllers;

import java.util.List;

import com.crm.gym.entities.Trainer;
import com.crm.gym.services.TrainerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trainers")
public class TrainerController
{
    TrainerService trainerService;

    public TrainerController(TrainerService trainerService)
    {
        this.trainerService = trainerService;
    }

    @PostMapping
    public void createTrainer(@RequestBody Trainer trainer)
    {
        trainerService.saveEntity(trainer);
    }

    @GetMapping
    public List<Trainer> getAllTrainers()
    {
        return trainerService.getAllEntities();
    }

    @GetMapping("/{id}")
    public Trainer getTrainerById(@PathVariable Long id)
    {
        return trainerService.getEntityById(id);
    }

    @PutMapping("/{id}")
    public void updateTrainerById(@PathVariable Long id, @RequestBody Trainer trainer)
    {
        trainerService.updateEntity(id, trainer);
    }

    @GetMapping("/username/{username}")
    public Trainer getTrainerByUsername(@PathVariable String username)
    {
        return trainerService.getUserByUsername(username);
    }

    @PutMapping("/username/{username}")
    public Trainer updateTrainerByUsername(@PathVariable String username, @RequestBody Trainer trainer)
    {
        return trainerService.updateUserByUsername(username, trainer);
    }

    @PatchMapping("/username/{username}/activate")
    public Boolean activateTrainee(@PathVariable String username)
    {
        return trainerService.activateUser(username);
    }

    @PatchMapping("/username/{username}/deactivate")
    public Boolean deactivateTrainee(@PathVariable String username)
    {
        return trainerService.deactivateUser(username);
    }

    @PostMapping("auth/login")
    public boolean login(@RequestParam String username, @RequestParam String password)
    {
        return trainerService.login(username, password);
    }

    @PutMapping("/auth/change-password")
    public boolean changePassword(@RequestParam String username, @RequestParam String oldPassword, @RequestParam String newPassword)
    {
        return trainerService.changePassword(username, oldPassword, newPassword);
    }

    @GetMapping("/trainees/username/{traineeUsername}/trainers/unassigned")
    public List<Trainer> getAllUnassignedForTraineeByUsername(@PathVariable String traineeUsername)
    {
        return trainerService.getAllUnassignedForTraineeByUsername(traineeUsername);
    }
}