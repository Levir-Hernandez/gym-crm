package com.crm.gym.controllers;

import java.util.List;
import com.crm.gym.entities.TrainingType;
import com.crm.gym.repositories.daos.TrainingTypeDao;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trainingType")
public class TrainingTypeController
{
    TrainingTypeDao trainingTypeDao;

    public TrainingTypeController(TrainingTypeDao trainingTypeDao)
    {
        this.trainingTypeDao = trainingTypeDao;
    }

    @GetMapping("/{id}")
    public TrainingType getTrainingById(@PathVariable Long id)
    {
        return trainingTypeDao.findById(id);
    }

    @GetMapping("/all")
    public List<TrainingType> getAllTrainingTypes()
    {
        return trainingTypeDao.findAll();
    }

    @PostMapping("/new")
    public void createTrainingType(@RequestBody TrainingType trainingType)
    {
        trainingTypeDao.create(trainingType);
    }
}