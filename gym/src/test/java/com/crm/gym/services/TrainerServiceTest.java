package com.crm.gym.services;

import com.crm.gym.entities.Trainer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class TrainerServiceTest
{
    private TrainerService trainerService;

    @Autowired
    public TrainerServiceTest(TrainerService trainerService)
    {
        this.trainerService = trainerService;
    }

    @Test
    @DisplayName("Should generate (id,username,password) and save Trainer")
    void saveEntity()
    {
        int totalTrainers = trainerService.getAllEntities().size();

        Trainer trainer = new Trainer(null,
                "Larry", "Williams",
                null, null,
                true, null);

        Trainer trainerResult = trainerService.saveEntity(trainer);

        assertNotNull(trainerResult.getId());
        assertNotNull(trainerResult.getUsername());
        assertNotNull(trainerResult.getPassword());

        int actualTrainers = trainerService.getAllEntities().size();
        assertEquals(totalTrainers+1, actualTrainers);
    }

    @Test
    @DisplayName("Should update existing Trainer")
    void updateEntity()
    {
        Long id = 1L;
        Trainer newTrainerInfo = new Trainer(null,
                "Larry", "Williams",
                "Willy", "secret1234",
                false, null);

        Trainer oldTrainer = trainerService.getEntityById(id);

        assertNotEquals(newTrainerInfo, oldTrainer);

        trainerService.updateEntity(id, newTrainerInfo);
        Trainer newTrainer = trainerService.getEntityById(id);

        assertNotEquals(oldTrainer, newTrainer);
        assertEquals(newTrainerInfo, newTrainer);
    }

    @Test
    @DisplayName("Should not update existing Trainer")
    void updateEntity2()
    {
        int totalTrainers = trainerService.getAllEntities().size();
        Long id = 10L + totalTrainers;

        Trainer newTrainerInfo = new Trainer(null,
                "Larry", "Williams",
                "Willy", "secret1234",
                false, null);

        Trainer oldTrainer = trainerService.getEntityById(id);
        assertNull(oldTrainer);

        trainerService.updateEntity(id, newTrainerInfo);

        Trainer newTrainer = trainerService.getEntityById(id);
        assertNull(newTrainer);

        int actualTrainers = trainerService.getAllEntities().size();
        assertEquals(totalTrainers, actualTrainers);
    }

    @Test
    @DisplayName("Should retrieve an existent Trainer")
    void getEntityById()
    {
        Trainer trainer = new Trainer(null,
                "Larry", "Williams",
                null, null,
                true, null);

        Trainer trainerExpected = trainerService.saveEntity(trainer);

        Long id = trainerExpected.getId();
        Trainer trainerActual = trainerService.getEntityById(id);

        assertEquals(trainerExpected, trainerActual);
    }

    @Test
    @DisplayName("Should return null for non existent Trainer")
    void getEntityById2()
    {
        int totalTrainers = trainerService.getAllEntities().size();
        Long id = 10L + totalTrainers;

        Trainer trainer = trainerService.getEntityById(id);
        assertNull(trainer);
    }

//    @Test
//    void getAllEntities()
//    {
//
//    }
}