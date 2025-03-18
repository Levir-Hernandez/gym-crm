package com.crm.gym.util;

public class IdGeneratorImpl implements IdGenerator<Long>
{
    private Long id;
    public IdGeneratorImpl(Long id) {this.id = id;}
    @Override public Long generateId() {return id++;}
}
