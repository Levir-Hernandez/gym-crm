package com.crm.gym.repositories.interfaces;

public interface Identifiable<Id>
{
    Id getId();
    void setId(Id id);
}
