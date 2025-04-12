package com.crm.gym.repositories.interfaces;

import java.util.List;
import com.crm.gym.entities.Trainer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TrainerRepository extends UserRepository<Trainer>
{
    @Query(
            "SELECT DISTINCT t1 FROM Trainer t1 " + // Fetch all active trainers
            "LEFT JOIN Training t2 " +
            "ON t2.trainer = t1 " +
            "LEFT JOIN Trainee t3 " +
            "ON t2.trainee = t3 " +
            "WHERE t1.isActive " +
            "AND (t2 IS NULL " + // with no assigned trainees
            "OR t3.username != :username)" // or not assigned to the trainee with such username
    )
    List<Trainer> findAllUnassignedForTraineeByUsername(@Param("username") String username);
}
