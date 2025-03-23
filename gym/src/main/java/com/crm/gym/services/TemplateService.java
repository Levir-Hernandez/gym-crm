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

    public void saveEntity(Entity entity)
    {
        repository.create(entity);
    }

    protected void updateEntity(Id entityId, Entity entity)
    {
        repository.update(entityId, entity);
    }

    protected void deleteEntity(Id entityId)
    {
        repository.delete(entityId);
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
