package com.crm.gym.config.repositories;

import com.crm.gym.repositories.interfaces.TemplateRepository;
import com.crm.gym.repositories.interfaces.Identifiable;
import com.crm.gym.util.EntityResourceLoader;

import jakarta.annotation.PostConstruct;

import java.util.Objects;
import java.util.Optional;
import java.util.List;

public abstract class TemplateConfig<Id, Entity extends Identifiable<Id>>
{
    protected String entitiesPath;
    protected TemplateRepository<Id, Entity> entityRepository;
    protected EntityResourceLoader entityResourceLoader;

    public TemplateConfig(String entitiesPath, TemplateRepository<Id, Entity> entityRepository, EntityResourceLoader entityResourceLoader)
    {
        this.entitiesPath = entitiesPath;
        this.entityRepository = entityRepository;
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
                .forEach(entityRepository::create);

        return Objects.nonNull(entities);
    }
}
