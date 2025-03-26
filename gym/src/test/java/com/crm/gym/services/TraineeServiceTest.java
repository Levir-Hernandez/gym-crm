package com.crm.gym.services;

import com.crm.gym.entities.Trainee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class TraineeServiceTest
{
    private TraineeService traineeService;

    @Autowired
    public TraineeServiceTest(TraineeService traineeService)
    {
        this.traineeService = traineeService;
    }

    @Test
    @DisplayName("Should generate (id,username,password) and save Trainee")
    void saveEntity()
    {
        int totalTrainees = traineeService.getAllEntities().size();

        Trainee trainee = new Trainee(null,
                "Larry", "Williams",
                null, null,
                true, LocalDate.parse("1991-03-21"), "123 Harlem St");

        Trainee traineeResult = traineeService.saveEntity(trainee);

        assertNotNull(traineeResult.getId());
        assertNotNull(traineeResult.getUsername());
        assertNotNull(traineeResult.getPassword());

        int actualTrainees = traineeService.getAllEntities().size();
        assertEquals(totalTrainees+1, actualTrainees);
    }

    @Test
    @DisplayName("Should update existing Trainee")
    void updateEntity()
    {
        Long id = 1L;
        Trainee newTraineeInfo = new Trainee(null,
                "Larry", "Williams",
                "Willy", "secret1234",
                false, LocalDate.parse("1991-03-21"), "123 Harlem St");

        Trainee oldTrainee = traineeService.getEntityById(id);

        assertNotEquals(newTraineeInfo, oldTrainee);

        traineeService.updateEntity(id, newTraineeInfo);
        Trainee newTrainee = traineeService.getEntityById(id);

        assertNotEquals(oldTrainee, newTrainee);
        assertEquals(newTraineeInfo, newTrainee);
    }

    @Test
    @DisplayName("Should not update existing Trainee")
    void updateEntity2()
    {
        int totalTrainees = traineeService.getAllEntities().size();
        Long id = 10L + totalTrainees;

        Trainee newTraineeInfo = new Trainee(null,
                "Larry", "Williams",
                "Willy", "secret1234",
                false, LocalDate.parse("1991-03-21"), "123 Harlem St");

        Trainee oldTrainee = traineeService.getEntityById(id);
        assertNull(oldTrainee);

        traineeService.updateEntity(id, newTraineeInfo);

        Trainee newTrainee = traineeService.getEntityById(id);
        assertNull(newTrainee);

        int actualTrainees = traineeService.getAllEntities().size();
        assertEquals(totalTrainees, actualTrainees);
    }

    @Test
    @DisplayName("Should delete existing Trainee")
    void deleteEntity()
    {
        Long id = 1L;

        Trainee trainee = traineeService.getEntityById(id);
        assertNotNull(trainee);

        traineeService.deleteEntity(id);

        trainee = traineeService.getEntityById(id);
        assertNull(trainee);
    }

    @Test
    @DisplayName("Should not delete non existing Trainee")
    void deleteEntity2()
    {
        int totalTrainees = traineeService.getAllEntities().size();
        Long id = 10L + totalTrainees;

        Trainee trainee = traineeService.getEntityById(id);
        assertNull(trainee);

        traineeService.deleteEntity(id);

        trainee = traineeService.getEntityById(id);
        assertNull(trainee);

        int actualTrainees = traineeService.getAllEntities().size();
        assertEquals(totalTrainees, actualTrainees);
    }

    @Test
    @DisplayName("Should retrieve an existent Trainee")
    void getEntityById()
    {
        Trainee trainee = new Trainee(null,
                "Larry", "Williams",
                null, null,
                true, LocalDate.parse("1991-03-21"), "123 Harlem St");

        Trainee traineeExpected = traineeService.saveEntity(trainee);

        Long id = traineeExpected.getId();
        Trainee traineeActual = traineeService.getEntityById(id);

        assertEquals(traineeExpected, traineeActual);
    }

    @Test
    @DisplayName("Should return null for non existent Trainee")
    void getEntityById2()
    {
        int totalTrainees = traineeService.getAllEntities().size();
        Long id = 10L + totalTrainees;

        Trainee trainee = traineeService.getEntityById(id);
        assertNull(trainee);
    }

//    @Test
//    void getAllEntities()
//    {
//
//    }
}