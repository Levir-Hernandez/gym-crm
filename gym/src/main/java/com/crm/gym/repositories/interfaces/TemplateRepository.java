package com.crm.gym.repositories.interfaces;

import java.util.List;

public interface TemplateRepository<Id, Entity extends Identifiable<Id>>
{
    void create(Entity entity);
    void update(Id entityId, Entity entity);
    void delete(Id entityId);

    Entity findById(Id entityId);
    List<Entity> findAll();

    boolean existsById(Id entityId);
}
