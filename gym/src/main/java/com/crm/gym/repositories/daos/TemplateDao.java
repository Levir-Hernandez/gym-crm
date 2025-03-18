package com.crm.gym.repositories.daos;

import com.crm.gym.repositories.interfaces.Identifiable;
import com.crm.gym.repositories.interfaces.TemplateRepository;

import java.util.List;
import java.util.Map;

public abstract class TemplateDao<Id, Entity extends Identifiable<Id>>
        implements TemplateRepository<Id, Entity>
{
    private Map<Id, Entity> entities;

    public TemplateDao(Map<Id, Entity> entities)
    {
        this.entities = entities;
    }

    public void create(Entity entity)
    {
        entities.put(entity.getId(), entity);
    }

    public void update(Entity entity)
    {
        entities.replace(entity.getId(), entity);
    }

    public void delete(Id entityId)
    {
        entities.remove(entityId);
    }

    public Entity findById(Id entityId)
    {
        return entities.get(entityId);
    }

    public List<Entity> findAll()
    {
        return entities.values().stream().toList();
    }
}
