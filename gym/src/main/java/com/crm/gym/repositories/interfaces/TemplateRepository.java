package com.crm.gym.repositories.interfaces;

import java.util.List;

public interface TemplateRepository<Id, Entity extends Identifiable<Id>>
{
    Entity create(Entity entity);
    Entity update(Id entityId, Entity entity);
    Entity delete(Id entityId);

    Entity findById(Id entityId);
    List<Entity> findAll();

    boolean existsById(Id entityId);
}
