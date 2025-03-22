package com.crm.gym.config;

import com.crm.gym.factories.TemplateFactory;
import com.crm.gym.repositories.interfaces.Identifiable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class TemplateConfig<Id, Entity extends Identifiable<Id>> implements BeanFactoryAware
{
    private DefaultListableBeanFactory beanFactory;

    private ObjectMapper mapper;
    private String traineesPath;
    private TemplateFactory<Id, Entity> entityFactory;

    public TemplateConfig(ObjectMapper mapper, String entitiesPath, TemplateFactory<Id, Entity> templateFactory)
    {
        this.mapper = mapper;
        this.traineesPath = entitiesPath;
        this.entityFactory = templateFactory;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException
    {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    protected abstract Class<Entity> getEntityClass();

    protected void createEntities(String beanPrefix)
    {
        Resource entitiesFile = new ClassPathResource(traineesPath);

        CollectionType collectionType = mapper.getTypeFactory()
                .constructCollectionType(List.class, getEntityClass());

        try
        {
            List<Entity> entities = mapper.readValue(entitiesFile.getInputStream(), collectionType);
            for (Entity entity : entities)
            {
                entity = entityFactory.recreate(entity);
                beanFactory.registerSingleton(beanPrefix+entity.getId(), entity);
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException("Error loading trainees from JSON", e);
        }
    }

    protected Map<Id, Entity> entities(List<Entity> entities)
    {
        return entities.stream().collect(Collectors.toMap(Entity::getId, entity -> entity));
    }
}
