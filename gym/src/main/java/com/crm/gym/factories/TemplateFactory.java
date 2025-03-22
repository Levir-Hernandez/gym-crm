package com.crm.gym.factories;

import com.crm.gym.repositories.interfaces.Identifiable;

public interface TemplateFactory<Id, Entity extends Identifiable<Id>>
{
    Entity recreate(Entity entity);
}
