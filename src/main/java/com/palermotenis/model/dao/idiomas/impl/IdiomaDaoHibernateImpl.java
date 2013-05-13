package com.palermotenis.model.dao.idiomas.impl;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.Idioma;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.idiomas.IdiomaDao;

@Repository("idiomaDao")
public class IdiomaDaoHibernateImpl extends AbstractHibernateDao<Idioma, Integer> implements IdiomaDao {

}
