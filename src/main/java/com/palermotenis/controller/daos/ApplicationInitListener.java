package com.palermotenis.controller.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;

public class ApplicationInitListener implements ApplicationListener<ContextRefreshedEvent>, Ordered {

    private EntityManager entityManager;
    private static final Class<?> DAO_IMPL = GenericDaoHibernateImpl.class;
    private List<Class<?>> daos;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ConfigurableApplicationContext applicationContext = (ConfigurableApplicationContext) event.getSource();
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getBeanFactory();
        for (Class<?> dao : daos) {
            StringBuilder builder = new StringBuilder(dao.getSimpleName());
            String firstChar = String.valueOf(builder.charAt(0)).toLowerCase();
            String serviceName = builder.replace(0, 1, firstChar) + "Dao";
            beanFactory.registerBeanDefinition(serviceName, createBean(dao));
        }
    }

    private AbstractBeanDefinition createBean(Class<?> clazz) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(DAO_IMPL);
        builder.addConstructorArgValue(clazz);
        builder.addPropertyValue("entityManager", entityManager);
        return builder.getBeanDefinition();
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setDaos(List<Class<?>> daos) {
        this.daos = daos;
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}
