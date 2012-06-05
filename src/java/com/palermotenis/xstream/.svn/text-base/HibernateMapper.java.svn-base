/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.xstream;

import com.thoughtworks.xstream.mapper.Mapper;
import com.thoughtworks.xstream.mapper.MapperWrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import org.hibernate.collection.PersistentBag;
import org.hibernate.collection.PersistentList;
import org.hibernate.collection.PersistentMap;
import org.hibernate.collection.PersistentSet;
import org.hibernate.collection.PersistentSortedMap;
import org.hibernate.collection.PersistentSortedSet;
import org.hibernate.proxy.HibernateProxy;

/**
 *
 * @author poly
 */
public class HibernateMapper extends MapperWrapper {

    private Map collectionMap = new HashMap();

    public HibernateMapper(Mapper arg0) {
        super(arg0);
        collectionMap.put(PersistentBag.class, ArrayList.class);
        collectionMap.put(PersistentList.class, ArrayList.class);
        collectionMap.put(PersistentMap.class, HashMap.class);
        collectionMap.put(PersistentSet.class, Set.class);
        collectionMap.put(PersistentSortedMap.class, SortedMap.class);
        collectionMap.put(PersistentSortedSet.class, SortedSet.class);
    }

    @Override
    public Class defaultImplementationOf(Class clazz) {
        if (collectionMap.containsKey(clazz)) {
            return (Class) collectionMap.get(clazz);
        }

        return super.defaultImplementationOf(clazz);
    }

    @Override
    public String serializedClass(Class clazz) {
        // chekc whether we are hibernate proxy and substitute real name
        for (int i = 0; i < clazz.getInterfaces().length; i++) {
            if (HibernateProxy.class.equals(clazz.getInterfaces()[i])) {
                return clazz.getSuperclass().getName();
            }
        }
        if (collectionMap.containsKey(clazz)) {
            return ((Class) collectionMap.get(clazz)).getName();
        }

        return super.serializedClass(clazz);
    }
}
