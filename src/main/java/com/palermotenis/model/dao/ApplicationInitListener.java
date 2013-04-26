package com.palermotenis.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;

public class ApplicationInitListener implements ApplicationListener<ContextRefreshedEvent>, Ordered {

    private EntityManager entityManager;
    private static final Class<?> DAO_IMPL = AbstractHibernateDao.class;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // ConfigurableApplicationContext applicationContext = (ConfigurableApplicationContext) event.getSource();
        // DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getBeanFactory();
        // for (Class<?> dao : daos) {
        // StringBuilder builder = new StringBuilder(dao.getSimpleName());
        // String firstChar = String.valueOf(builder.charAt(0)).toLowerCase();
        // String serviceName = builder.replace(0, 1, firstChar) + "Dao";
        // beanFactory.registerBeanDefinition(serviceName, createBean(dao));
        // }
        //
        // Map<String, Object> beansMap = beanFactory.getBeansWithAnnotation(Service.class);
        // for (Object bean : beansMap.values()) {
        // applicationContext.getAutowireCapableBeanFactory().autowireBean(bean);
        // }
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setDaos(List<Class<?>> daos) {
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}
