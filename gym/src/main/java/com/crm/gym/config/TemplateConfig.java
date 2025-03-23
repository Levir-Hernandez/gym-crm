package com.crm.gym.config;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.crm.gym.repositories.interfaces.TemplateRepository;
import com.crm.gym.repositories.interfaces.Identifiable;
import org.springframework.beans.factory.BeanFactoryAware;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.beans.factory.BeanFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeansException;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.util.List;

public abstract class TemplateConfig<Id, Entity extends Identifiable<Id>> implements BeanFactoryAware
{
    private DefaultListableBeanFactory beanFactory;

    private ObjectMapper mapper;
    private String traineesPath;
    private TemplateRepository<Id, Entity> entityRepository;

    public TemplateConfig(ObjectMapper mapper, String traineesPath, TemplateRepository<Id, Entity> entityRepository)
    {
        this.mapper = mapper;
        this.traineesPath = traineesPath;
        this.entityRepository = entityRepository;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException
    {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    protected abstract Class<Entity> getEntityClass();

    protected void createEntitiesFromJson(String beanPrefix)
    {
        Resource entitiesFile = new ClassPathResource(traineesPath);

        CollectionType collectionType = mapper.getTypeFactory()
                .constructCollectionType(List.class, getEntityClass());

        try
        {
            List<Entity> entities = mapper.readValue(entitiesFile.getInputStream(), collectionType);
            for (Entity entity : entities)
            {
                entityRepository.create(entity);
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException("Error loading objects of class (" + getEntityClass().getName() + ") from JSON", e);
        }
    }
}
