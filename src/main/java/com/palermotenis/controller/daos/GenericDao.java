/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.controller.daos;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

/**
 *
 * @author Poly
 */
public interface GenericDao <T, PK> {

    void create(T o);
    
    T find(PK id);

    T load(PK id) throws EntityNotFoundException;

    boolean exists(T o);

    List<T> findAll();

    List<T> findAll(int maxResults, int firstResult);    

    T findBy(String queryName, String arg, Object queryArg);

    T findBy(String queryName, Map<String, Object> queryArgs) throws NoResultException;

    List<T> query(String queryName);

    List<T> query(String queryName, String arg, Object queryArg);

    List<T> query(String queryName, int maxResults, int firstResult);

    List<T> queryBy(String by, String arg, Object queryArg);

    List<T> queryBy(String by, Map<String, Object> queryArgs);   

    List<T> queryBy(String by, Map<String, Object> queryArgs, int maxResults, int firstResult);

    int count();

    int getIntResult(String queryName);

    int getIntResultBy(String queryName, String args, Object queryArg);

    int getIntResultBy(String queryName, Map<String, Object> queryArgs);

    void edit(T o);

    void destroy(T o);
}

