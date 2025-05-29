package com.crm.gym.dtos.assemblers;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.EntityModel;
import com.crm.gym.dtos.training.TrainingRespDto;
import com.crm.gym.controllers.TrainingController;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TrainingModelAssembler implements RepresentationModelAssembler<TrainingRespDto, EntityModel<TrainingRespDto>>
{
    @Override
    public EntityModel<TrainingRespDto> toModel(TrainingRespDto trainingRespDto)
    {
        return EntityModel.of(trainingRespDto, buildLinks());
    }

    public Link[] buildLinks()
    {
        return new Link[]{
                linkTo(methodOn(TrainingController.class).createTraining(null))
                        .withRel("create-training"),
                linkTo(methodOn(TrainingController.class).getAllTrainings(null, null))
                        .withRel("all-trainings"),
                linkTo(methodOn(TrainingController.class).getTrainingsByTraineeUsernameAndCriteria(null, null, null, null, null, null, null))
                        .withRel("trainee-trainings"),
                linkTo(methodOn(TrainingController.class).getTrainingsByTrainerUsernameAndCriteria(null, null, null, null, null, null))
                        .withRel("trainer-trainings")
        };
    }
}
