package com.crm.gym.config.classbased;

import com.crm.gym.entities.Trainee;
import com.crm.gym.entities.Trainer;
import com.crm.gym.entities.Training;
import com.crm.gym.entities.TrainingType;
import com.crm.gym.util.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class TrainingConfig
{
    private IdGenerator<Long> idGenerator;
    private List<Trainee> trainees;
    private List<Trainer> trainers;
    private List<TrainingType> trainingTypes;

    public TrainingConfig(IdGenerator<Long> idGenerator, List<Trainee> trainees, List<Trainer> trainers, List<TrainingType> trainingTypes)
    {
        this.idGenerator = idGenerator;
        this.trainees = trainees;
        this.trainers = trainers;
        this.trainingTypes = trainingTypes;
    }

    @Bean
    public Training training1()
    {
        return createTraining("Strength Basics",
                new Date(125, 5, 7), 60,
                trainees.get(0), trainers.get(0), trainingTypes.get(0));
    }

    @Bean
    public Training training2()
    {
        return createTraining("Cardio Blast",
                new Date(125, 5, 15), 45,
                trainees.get(1), trainers.get(1), trainingTypes.get(1));
    }

    @Bean
    public Training training3()
    {
        return createTraining("Flexibility Flow",
                new Date(125, 5, 21), 30,
                trainees.get(2), trainers.get(2), trainingTypes.get(2));
    }

    @Bean
    public Map<Long, Training> trainings(List<Training> trainings)
    {
        return trainings.stream().collect(Collectors.toMap(Training::getId, training -> training));
    }

    private Training createTraining(String name, Date date, Integer duration,
                                    Trainee trainee, Trainer trainer, TrainingType type)
    {
        Training training = new Training(null, name, date, duration, trainee, trainer, type);
        training.setId(idGenerator.generateId());
        return training;
    }
}
