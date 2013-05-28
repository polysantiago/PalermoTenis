package com.palermotenis.infrastructure.testsupport.base;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value =
    { "classpath:/spring/applicationContext-services.xml", "classpath:/spring/applicationContext-persist.xml",
            "classpath:/spring/test-auxiliary-services.xml" })
public abstract class BaseSpringTestCase extends AbstractTransactionalJUnit4SpringContextTests {

    private EntityManager entityManager;

    protected void clear() {
        entityManager.clear();
    }

    protected void flush() {
        entityManager.flush();
    }

    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
