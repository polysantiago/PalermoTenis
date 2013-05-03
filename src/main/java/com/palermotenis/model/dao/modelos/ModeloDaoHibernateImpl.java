package com.palermotenis.model.dao.modelos;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.google.common.collect.ImmutableMap;
import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.dao.AbstractHibernateDao;

@Repository("modeloDao")
public class ModeloDaoHibernateImpl extends AbstractHibernateDao<Modelo, Integer> implements ModeloDao {

    @Resource
    private PlatformTransactionManager transactionManager;

    @Override
    public List<Modelo> getModelosByTipoProducto(TipoProducto tipoProducto) {
        return queryBy("TipoProducto", "tipoProduco", tipoProducto);
    }

    @Override
    public List<Modelo> getModelosByMarcaAndTipoProducto(Marca marca, TipoProducto tipoProducto) {
        Map<String, Object> args = buildMarcaAndTipoProductoArgs(marca, tipoProducto);
        return queryBy("Marca,TipoProducto", args);
    }

    @Override
    public List<Modelo> getModelosByMarcaAndActiveTipoProducto(Marca marca, TipoProducto tipoProducto) {
        Map<String, Object> args = buildMarcaAndTipoProductoArgs(marca, tipoProducto);
        return queryBy("Marca,TipoProducto-Active", args);
    }

    @Override
    public List<Modelo> getModelosByActiveParent(Modelo parent) {
        return queryBy("Padre-Active", "padre", parent);
    }

    @Override
    public void moveModelo(final Integer modeloId, final Integer modeloOrden, final Integer modeloRgtId,
            final Integer modeloRgtOrden) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                getEntityManager()
                    .createNativeQuery("{call moverModelo(?,?,?,?)}")
                    .setParameter(1, modeloId)
                    .setParameter(2, modeloOrden)
                    .setParameter(3, modeloRgtId)
                    .setParameter(4, modeloRgtOrden)
                    .executeUpdate();
            }
        });
    }

    private Map<String, Object> buildMarcaAndTipoProductoArgs(Marca marca, TipoProducto tipoProducto) {
        Map<String, Object> args = new ImmutableMap.Builder<String, Object>()
            .put("tipoProducto", tipoProducto)
            .put("marca", marca)
            .build();
        return args;
    }

}
