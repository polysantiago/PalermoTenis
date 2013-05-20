package com.palermotenis.model.dao.atributos.tipos.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.atributos.tipos.TipoAtributoDao;

@Repository("tipoAtributoDao")
public class TipoAtributoDaoHibernateImpl extends AbstractHibernateDao<TipoAtributo, Integer> implements
    TipoAtributoDao {

    @Override
    public List<TipoAtributo> getTiposAtributosByTipoProducto(TipoProducto tipoProducto) {
        return queryBy("TipoProducto", "tipoProducto", tipoProducto);
    }

    @Override
    public void updateTipoAtributoClass(Character metadata, TipoAtributo tipoAtributo) {
        getEntityManager()
            .createQuery("UPDATE TipoAtributo t SET t.class = :clazz WHERE t = :tipoAtributo")
            .setParameter("clazz", metadata)
            .setParameter("tipoAtributo", tipoAtributo)
            .executeUpdate();
    }

}
