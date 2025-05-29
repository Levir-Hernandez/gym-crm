package com.crm.gym.dtos.assemblers;

import com.crm.gym.dtos.trainee.TraineeRespDto;
import org.springframework.hateoas.EntityModel;
import com.crm.gym.controllers.TraineeController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TraineeModelAssembler implements RepresentationModelAssembler<TraineeRespDto, EntityModel<TraineeRespDto>>
{
    @Override
    public EntityModel<TraineeRespDto> toModel(TraineeRespDto traineeRespDto)
    {
        return EntityModel.of(traineeRespDto, buildLinks(traineeRespDto.getUsername()));
    }

    public Link[] buildLinks(String username)
    {
        return new Link[]{
                linkTo(methodOn(TraineeController.class).createTrainee(null))
                        .withRel("create-trainee"),
                linkTo(methodOn(TraineeController.class).getAllTrainees(null, null))
                        .withRel("all-trainees"),
                linkTo(methodOn(TraineeController.class).getTraineeByUsername(username))
                        .withRel("trainee-by-username"),
                linkTo(methodOn(TraineeController.class).updateTraineeByUsername(username, null))
                        .withRel("update-trainee"),
                linkTo(methodOn(TraineeController.class).activateTrainee(username))
                        .withRel("activate-trainee"),
                linkTo(methodOn(TraineeController.class).deactivateTrainee(username))
                        .withRel("deactivate-trainee"),
                linkTo(methodOn(TraineeController.class).deleteTraineeByUsername(username))
                        .withRel("delete-trainee"),
                linkTo(methodOn(TraineeController.class).login(username, null))
                        .withRel("authenticate-trainee"),
                linkTo(methodOn(TraineeController.class).changePassword(username, null, null))
                        .withRel("update-trainee-password")
        };
    }
}
