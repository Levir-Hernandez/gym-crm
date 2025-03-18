package com.crm.gym.controllers;

import com.crm.gym.entities.TrainingType;
import com.crm.gym.repositories.daos.TrainingTypeDao;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}