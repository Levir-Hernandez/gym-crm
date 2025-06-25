package com.crm.gym.config.entities;

import com.crm.gym.repositories.interfaces.TemplateRepository;
import com.crm.gym.repositories.interfaces.Identifiable;
import com.crm.gym.services.TemplateService;
import com.crm.gym.util.EntityResourceLoader;

import jakarta.annotation.PostConstruct;

import java.util.Objects;
import java.util.Optional;
import java.util.List;

public abstract class TemplateConfig<Id,
        Entity extends Identifiable<Id>,
        Repository extends TemplateRepository<Id,Entity>>
{
    protected String entitiesPath;
    protected TemplateService<Id, Entity, Repository> entityService;
    protected EntityResourceLoader entityResourceLoader;

    public TemplateConfig(String entitiesPath, TemplateService<Id, Entity, Repository> entityService, EntityResourceLoader entityResourceLoader)
    {
        this.entitiesPath = entitiesPath;
        this.entityService = entityService;
        this.entityResourceLoader = entityResourceLoader;
    }

    protected abstract Class<Entity> getEntityClass();

    @PostConstruct
    protected boolean createEntitiesFromJson()
    {
        if(entitiesPath.isEmpty()){return false;}

        List<Entity> entities = entityResourceLoader.loadEntitiesFromJson(entitiesPath, getEntityClass());

        Optional.ofNullable(entities)
                .stream().flatMap(List::stream)
                .forEach(entityService::saveEntity);

        return Objects.nonNull(entities);
    }
}
