package com.crm.gym.services;

import com.crm.gym.entities.Training;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class TrainingServiceTest
{
    private TrainingService trainingService;

    @Autowired
    public TrainingServiceTest(TrainingService trainingService)
    {
        this.trainingService = trainingService;
    }

    @Test
    @DisplayName("Should generate (id) and save Training")
    void saveEntity()
    {
        int totalTrainings = trainingService.getAllEntities().size();

        Training training = new Training(null,
                "Some Training", LocalDate.parse("2021-06-07"), 30,
                null, null, null);

        Training trainingResult = trainingService.saveEntity(training);

        assertNotNull(trainingResult.getId());

        int actualTrainings = trainingService.getAllEntities().size();
        assertEquals(totalTrainings+1, actualTrainings);
    }

    @Test
    @DisplayName("Should retrieve an existent Training")
    void getEntityById()
    {
        Training training = new Training(null,
                "Some Training", LocalDate.parse("2021-06-07"), 30,
                null, null, null);

        Training trainingExpected = trainingService.saveEntity(training);

        Long id = trainingExpected.getId();
        Training trainingActual = trainingService.getEntityById(id);

        assertEquals(trainingExpected, trainingActual);
    }

    @Test
    @DisplayName("Should return null for non existent Training")
    void getEntityById2()
    {
        int totalTrainings = trainingService.getAllEntities().size();
        Long id = 10L + totalTrainings;

        Training training = trainingService.getEntityById(id);
        assertNull(training);
    }

//    @Test
//    void getAllEntities()
//    {
//
//    }
}