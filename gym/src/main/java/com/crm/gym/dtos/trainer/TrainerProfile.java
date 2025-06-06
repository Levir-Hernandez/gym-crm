package com.crm.gym.dtos.trainer;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import com.crm.gym.dtos.trainee.TraineeBriefProfile;
import org.springframework.hateoas.server.core.Relation;

@Getter @Setter
@Relation(collectionRelation = "trainers")
public class TrainerProfile extends TrainerBriefProfile
{
    private Boolean isActive;
    private List<TraineeBriefProfile> trainees;
}
