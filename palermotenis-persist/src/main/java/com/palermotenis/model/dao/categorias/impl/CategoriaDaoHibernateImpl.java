package com.palermotenis.model.dao.categorias.impl;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.Categoria;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.categorias.CategoriaDao;

@Repository("categoriaDao")
public class CategoriaDaoHibernateImpl extends AbstractHibernateDao<Categoria, Integer> implements CategoriaDao {

}
