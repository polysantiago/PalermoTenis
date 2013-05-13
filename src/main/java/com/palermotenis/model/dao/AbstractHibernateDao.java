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

import org.apache.commons.collections.MapUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableMap;

@Transactional(propagation = Propagation.REQUIRED, noRollbackFor =
    { NoResultException.class, EntityNotFoundException.class }, readOnly = true)
public abstract class AbstractHibernateDao<T, PK extends Serializable> implements Dao<T, PK> {

    private static final String DESTROY_POSTFIX = ".destroy";
    private static final String EDIT_POSTFIX = ".edit";
    private static final String FIND_POSTFIX = ".find";
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
    @Transactional(readOnly = false)
    public void create(T entity) {
        entityManager.persist(entity);
    }

    @Override
    @Transactional(readOnly = false)
    public void edit(T entity) {
        entityManager.merge(entity);
    }

    @Override
    @Transactional(readOnly = false)
    public int edit(String queryName) {
        return executeUpdate(entityManager.createNamedQuery(editName(queryName)));
    }

    @Override
    @Transactional(readOnly = false)
    public int edit(String queryName, String arg, Object queryArg) {
        return executeUpdate(entityManager.createNamedQuery(editName(queryName)).setParameter(arg, queryArg));
    }

    @Override
    @Transactional(readOnly = false)
    public int edit(String queryName, Map<String, Object> queryArgs) {
        return executeUpdate(create(editName(queryName), queryArgs, true, -1, -1));
    }

    @Override
    @Transactional(readOnly = false)
    public void destroy(T entity) {
        entityManager.remove(entity);
    }

    @Override
    @Transactional(readOnly = false)
    public int destroy(String queryName) {
        return executeUpdate(entityManager.createNamedQuery(destroyName(queryName)));
    }

    @Override
    @Transactional(readOnly = false)
    public int destroy(String queryName, String arg, Object queryArg) {
        return executeUpdate(entityManager.createNamedQuery(destroyName(queryName)).setParameter(arg, queryArg));
    }

    @Override
    @Transactional(readOnly = false)
    public int destroy(String queryName, Map<String, Object> queryArgs) {
        return executeUpdate(create(destroyName(queryName), queryArgs, true, -1, -1));
    }

    private int executeUpdate(Query query) {
        return query.executeUpdate();
    }

    @Override
    public T find(PK id) {
        return entityManager.find(type, id);
    }

    @Override
    public T load(PK id) throws EntityNotFoundException {
        T entity = find(id);
        if (entity == null) {
            throw new EntityNotFoundException("Entity " + type.getName() + "[" + id + "] not found");
        }
        return entity;
    }

    @Override
    public boolean exists(T entity) {
        return entityManager.contains(entity);
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
        Query query = create(queryName("By" + queryName), args, true, -1, -1);
        return (T) query.getSingleResult();
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
        Query query = create(queryName(queryName), queryArgs, all, maxResults, firstResult);
        return query.getResultList();
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
        return queryInt(create(queryName("By" + by), queryArgs, true, -1, -1));
    }

    private int queryInt(Query query) {
        return ((Integer) query.getSingleResult()).intValue();
    }

    private Query create(String queryName, Map<String, Object> queryArgs, boolean all, int maxResults, int firstResult) {
        Query query = entityManager.createNamedQuery(queryName);
        if (!all) {
            query.setMaxResults(maxResults);
            query.setFirstResult(firstResult);
        }
        if (MapUtils.isNotEmpty(queryArgs)) {
            for (Map.Entry<String, ?> entry : queryArgs.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return query;
    }

    private String queryName(String name) {
        return buildName(name, FIND_POSTFIX);
    }

    private String editName(String name) {
        return buildName(name, EDIT_POSTFIX);
    }

    private String destroyName(String name) {
        return buildName(name, DESTROY_POSTFIX);
    }

    private String buildName(String name, String postfix) {
        return type.getSimpleName() + postfix + name;
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
