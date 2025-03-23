package com.crm.gym.controllers;

import java.util.List;
import com.crm.gym.entities.Trainee;
import com.crm.gym.services.TraineeService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/new")
    public void createTrainee(@RequestBody Trainee trainee)
    {
        traineeService.saveEntity(trainee);
    }

    @PutMapping("/{id}")
    public void updateTraineeById(@PathVariable Long id, @RequestBody Trainee trainee)
    {
        traineeService.updateEntity(id, trainee);
    }

    @DeleteMapping("/{id}")
    public void deleteTraineeById(@PathVariable Long id)
    {
        traineeService.deleteEntity(id);
    }
}
