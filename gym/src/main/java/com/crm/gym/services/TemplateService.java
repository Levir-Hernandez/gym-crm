package com.crm.gym.services;

import java.util.List;
import com.crm.gym.repositories.interfaces.Identifiable;
import com.crm.gym.repositories.interfaces.TemplateRepository;

public abstract class TemplateService<Id,
        Entity extends Identifiable<Id>,
        Repository extends TemplateRepository<Id,Entity>>
{
    private Repository repository;

    public TemplateService(Repository repository)
    {
        this.repository = repository;
    }

    public Entity saveEntity(Entity entity)
    {
        return repository.create(entity);
    }

    protected Entity updateEntity(Id entityId, Entity entity)
    {
        return repository.update(entityId, entity);
    }

    protected Entity deleteEntity(Id entityId)
    {
        return repository.delete(entityId);
    }

    public Entity getEntityById(Id entityId)
    {
        return repository.findById(entityId);
    }

    public List<Entity> getAllEntities()
    {
        return repository.findAll();
    }
}
