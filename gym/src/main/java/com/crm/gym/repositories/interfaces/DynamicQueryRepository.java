package com.crm.gym.repositories.interfaces;

import com.crm.gym.entities.Training;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.crm.gym.repositories.TrainingQueryCriteria;

import java.util.List;

public interface DynamicQueryRepository
{
    List<Training> findByCriteria(TrainingQueryCriteria criteria);
    Page<Training> findByCriteria(TrainingQueryCriteria criteria, Pageable pageable);
}
