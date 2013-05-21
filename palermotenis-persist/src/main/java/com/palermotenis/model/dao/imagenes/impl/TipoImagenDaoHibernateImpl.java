package com.palermotenis.model.dao.imagenes.impl;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.imagenes.tipos.TipoImagen;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.imagenes.TipoImagenDao;

@Repository("tipoImagenDao")
public class TipoImagenDaoHibernateImpl extends AbstractHibernateDao<TipoImagen, Character> implements TipoImagenDao {

}
