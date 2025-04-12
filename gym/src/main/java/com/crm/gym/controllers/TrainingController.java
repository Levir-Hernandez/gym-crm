package com.crm.gym.controllers;

import java.util.List;
import java.time.LocalDate;
import com.crm.gym.entities.Training;
import com.crm.gym.services.TrainingService;
import com.crm.gym.repositories.TrainingQueryCriteria;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trainings")
public class TrainingController
{
    TrainingService trainingService;

    public TrainingController(TrainingService trainingService)
    {
        this.trainingService = trainingService;
    }

    @PostMapping
    public void createTraining(@RequestBody Training training)
    {
        trainingService.saveEntity(training);
    }

    @GetMapping
    public List<Training> getAllTrainings()
    {
        return trainingService.getAllEntities();
    }

    @GetMapping("/{id}")
    public Training getTrainingById(@PathVariable Long id)
    {
        return trainingService.getEntityById(id);
    }

    @GetMapping("/trainees/username/{traineeUsername}/trainings")
    public List<Training> getTrainingsByTraineeUsernameAndCriteria(@PathVariable String traineeUsername,
                                                                   @RequestParam(required = false) String trainerUsername,
                                                                   @RequestParam(required = false) LocalDate fromDate,
                                                                   @RequestParam(required = false) LocalDate toDate,
                                                                   @RequestParam(required = false) String trainingTypeName)
    {
        TrainingQueryCriteria criteria = TrainingQueryCriteria.builder()
                .traineeUsername(traineeUsername)
                .trainerUsername(trainerUsername)
                .fromDate(fromDate)
                .toDate(toDate)
                .trainingTypeName(trainingTypeName)
                .build();
        return trainingService.getTrainingsByTraineeUsernameAndCriteria(criteria);
    }

    @GetMapping("/trainers/username/{trainerUsername}/trainings")
    public List<Training> getTrainingsByTrainerUsernameAndCriteria(@PathVariable String trainerUsername,
                                                                   @RequestParam(required = false) String traineeUsername,
                                                                   @RequestParam(required = false) LocalDate fromDate,
                                                                   @RequestParam(required = false) LocalDate toDate)
    {
        TrainingQueryCriteria criteria = TrainingQueryCriteria.builder()
                .trainerUsername(trainerUsername)
                .traineeUsername(traineeUsername)
                .fromDate(fromDate)
                .toDate(toDate)
                .build();
        return trainingService.getTrainingsByTrainerUsernameAndCriteria(criteria);
    }
}
