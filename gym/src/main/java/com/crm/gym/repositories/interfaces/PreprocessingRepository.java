package com.crm.gym.repositories.interfaces;

public interface PreprocessingRepository<Id, Entity extends Identifiable<Id>>
{
    Entity create(Entity entity);
    Entity update(Id entityId, Entity entity);
}
