package com.palermotenis.model.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableMap;

@Transactional
public abstract class AbstractHibernateDao<T, PK extends Serializable> implements Dao<T, PK> {

    private final Class<T> type;
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public AbstractHibernateDao() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public AbstractHibernateDao(Class<T> type) {
        this.type = type;
    }

    @Override
    public void create(T o) {
        entityManager.persist(o);
    }

    @Override
    public void edit(T o) {
        entityManager.merge(o);
    }

    @Override
    public void destroy(T o) {
        entityManager.remove(o);
    }

    @Override
    public T find(PK id) {
        return entityManager.find(type, id);
    }

    @Override
    public T load(PK id) throws EntityNotFoundException {
        T t = find(id);
        if (t == null) {
            throw new EntityNotFoundException("Entity " + type.getName() + "[" + id + "] not found");
        }
        return t;
    }

    @Override
    public boolean exists(T o) {
        return entityManager.contains(o);
    }

    @Override
    public List<T> findAll() {
        return query("All", null, true, -1, -1);
    }

    @Override
    public List<T> findAll(int maxResults, int firstResult) {
        return query("All", null, false, maxResults, firstResult);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T findBy(String queryName, String arg, Object queryArg) {
        return (T) entityManager
            .createNamedQuery(queryName("By" + queryName))
            .setParameter(arg, queryArg)
            .getSingleResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public T findBy(String queryName, Map<String, Object> args) throws NoResultException {
        Query q = entityManager.createNamedQuery(queryName("By" + queryName));
        if (args != null && !args.isEmpty()) {
            for (Map.Entry<String, ?> entry : args.entrySet()) {
                q.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return (T) q.getSingleResult();
    }

    @Override
    public List<T> query(String queryName) {
        return query(queryName, null, true, -1, -1);
    }

    @Override
    public List<T> query(String queryName, String arg, Object queryArg) {
        return query(queryName, new ImmutableMap.Builder<String, Object>().put(arg, queryArg).build(), true, -1, -1);
    }

    @Override
    public List<T> query(String queryName, int maxResults, int firstResult) {
        return query(queryName, null, false, maxResults, firstResult);
    }

    @Override
    public List<T> queryBy(String by, String arg, Object queryArg) {
        return query("By" + by, new ImmutableMap.Builder<String, Object>().put(arg, queryArg).build(), true, -1, -1);
    }

    @Override
    public List<T> queryBy(String by, Map<String, Object> queryArgs) {
        return query("By" + by, queryArgs, true, -1, -1);
    }

    @Override
    public List<T> queryBy(String by, Map<String, Object> queryArgs, int maxResults, int firstResult) {
        return query("By" + by, queryArgs, false, maxResults, firstResult);
    }

    @SuppressWarnings("unchecked")
    private List<T> query(String queryName, Map<String, Object> queryArgs, boolean all, int maxResults, int firstResult) {
        try {
            Query query = entityManager.createNamedQuery(queryName(queryName));
            if (!all) {
                query.setMaxResults(maxResults);
                query.setFirstResult(firstResult);
            }
            if (queryArgs != null && !queryArgs.isEmpty()) {
                for (Map.Entry<String, ?> entry : queryArgs.entrySet()) {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }
            return query.getResultList();
        } finally {
            // entityManager.close();
        }
    }

    @Override
    public int count() {
        return queryInt(entityManager.createQuery("select count(o) from " + type.getSimpleName() + " as o"));
    }

    @Override
    public int getIntResult(String queryName) {
        return queryInt(entityManager.createNamedQuery(queryName(queryName)));
    }

    @Override
    public int getIntResultBy(String by, String arg, Object queryArg) {
        return queryInt(entityManager.createNamedQuery(queryName("By" + by)).setParameter(arg, queryArg));
    }

    @Override
    public int getIntResultBy(String by, Map<String, Object> queryArgs) {
        Query q = entityManager.createNamedQuery(queryName("By" + by));
        if (queryArgs != null && !queryArgs.isEmpty()) {
            for (Map.Entry<String, ?> entry : queryArgs.entrySet()) {
                q.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return queryInt(q);
    }

    private int queryInt(Query query) {
        return ((Long) query.getSingleResult()).intValue();
    }

    private String queryName(String name) {
        return type.getSimpleName() + ".find" + name;
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
