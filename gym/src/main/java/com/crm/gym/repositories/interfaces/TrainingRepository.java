package com.crm.gym.repositories.interfaces;

import com.crm.gym.entities.Training;
import java.util.UUID;

public interface TrainingRepository extends DynamicQueryRepository, TemplateRepository<UUID, Training>
{

}
