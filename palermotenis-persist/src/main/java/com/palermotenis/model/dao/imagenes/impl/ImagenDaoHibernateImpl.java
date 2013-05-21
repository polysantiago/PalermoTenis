package com.palermotenis.model.dao.imagenes.impl;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.imagenes.Imagen;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.imagenes.ImagenDao;

@Repository("imagenDao")
public class ImagenDaoHibernateImpl extends AbstractHibernateDao<Imagen, Integer> implements ImagenDao {

}
