package com.crm.gym.controllers;

import java.util.List;
import java.util.Set;

import com.crm.gym.entities.Trainee;
import com.crm.gym.entities.Trainer;
import com.crm.gym.services.TraineeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trainees")
public class TraineeController
{
    TraineeService traineeService;

    public TraineeController(TraineeService traineeService)
    {
        this.traineeService = traineeService;
    }

    @PostMapping
    public Trainee createTrainee(@RequestBody Trainee trainee)
    {
        return traineeService.saveEntity(trainee);
    }

    @GetMapping
    public List<Trainee> getAllTrainees()
    {
        return traineeService.getAllEntities();
    }

    @GetMapping("/{id}")
    public Trainee getTraineeById(@PathVariable Long id)
    {
        return traineeService.getEntityById(id);
    }

    @PutMapping("/{id}")
    public Trainee updateTraineeById(@PathVariable Long id, @RequestBody Trainee trainee)
    {
        return traineeService.updateEntity(id, trainee);
    }

    @DeleteMapping("/{id}")
    public boolean deleteTraineeById(@PathVariable Long id)
    {
        return traineeService.deleteEntity(id);
    }

    @GetMapping("/username/{username}")
    public Trainee getTraineeByUsername(@PathVariable String username)
    {
        return traineeService.getUserByUsername(username);
    }

    @PutMapping("/username/{username}")
    public Trainee updateTraineeByUsername(@PathVariable String username, @RequestBody Trainee trainee)
    {
        return traineeService.updateUserByUsername(username, trainee);
    }

    @PatchMapping("/username/{username}/activate")
    public Boolean activateTrainee(@PathVariable String username)
    {
        return traineeService.activateUser(username);
    }

    @PatchMapping("/username/{username}/deactivate")
    public Boolean deactivateTrainee(@PathVariable String username)
    {
        return traineeService.deactivateUser(username);
    }

    @DeleteMapping("/username/{username}")
    public boolean deleteTraineeByUsername(@PathVariable String username)
    {
        return traineeService.deleteTraineeByUsername(username);
    }

    @PostMapping("auth/login")
    public boolean login(@RequestParam String username, @RequestParam String password)
    {
        return traineeService.login(username, password);
    }

    @PutMapping("/auth/change-password")
    public boolean changePassword(@RequestParam String username, @RequestParam String oldPassword, @RequestParam String newPassword)
    {
        return traineeService.changePassword(username, oldPassword, newPassword);
    }

    @PutMapping("/username/{traineeUsername}/trainers/assigned")
    public int updateAssignedTrainersForTrainee(@PathVariable String traineeUsername, @RequestBody Set<Trainer> trainers)
    {
        return traineeService.updateAssignedTrainersForTrainee(traineeUsername, trainers);
    }
}
