package com.crm.gym.repositories.daos;

import java.util.Map;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import com.crm.gym.factories.TemplateFactory;
import com.crm.gym.repositories.interfaces.Identifiable;
import com.crm.gym.repositories.interfaces.TemplateRepository;

import lombok.Getter;

public abstract class TemplateDao<Id, Entity extends Identifiable<Id>>
        implements TemplateRepository<Id, Entity>
{
    private TemplateFactory<Id, Entity> templateFactory;
    @Getter(AccessLevel.PROTECTED) private Map<Id, Entity> entities;

    public TemplateDao(TemplateFactory<Id, Entity> templateFactory, Map<Id, Entity> entities)
    {
        this.templateFactory = templateFactory;
        this.entities = entities;
    }

    @Override
    public Entity create(Entity entity)
    {
        entity = templateFactory.recreate(entity);
        entities.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Entity update(Id entityId, Entity entity)
    {
        if(existsById(entityId))
        {
            entity.setId(entityId);
            entities.put(entityId, entity);
        }
        else {entity = null;}

        return entity;
    }

    @Override
    public Entity delete(Id entityId)
    {
        return entities.remove(entityId);
    }

    @Override
    public Entity findById(Id entityId)
    {
        return entities.get(entityId);
    }

    @Override
    public boolean existsById(Id entityId)
    {
        return Objects.nonNull(findById(entityId));
    }

    @Override
    public List<Entity> findAll()
    {
        return entities.values().stream().toList();
    }
}
