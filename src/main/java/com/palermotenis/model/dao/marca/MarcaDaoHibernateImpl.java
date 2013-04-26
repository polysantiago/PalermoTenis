package com.palermotenis.model.dao.marca;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.dao.AbstractHibernateDao;

@Repository("marcaDao")
public class MarcaDaoHibernateImpl extends AbstractHibernateDao<Marca, Integer> implements MarcaDao {

    @Override
    public List<Marca> getActiveMarcasByTipoProducto(TipoProducto tipoProducto) {
        return queryBy("TipoProductoAndActiveProducto", "tipoProducto", tipoProducto);
    }

}
