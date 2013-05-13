package com.palermotenis.infrastructre.testsupport.base;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value =
    { "classpath:/spring/applicationContext-business.xml", "classpath:/spring/test-auxiliary-services.xml" })
@TransactionConfiguration(defaultRollback = true)
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
