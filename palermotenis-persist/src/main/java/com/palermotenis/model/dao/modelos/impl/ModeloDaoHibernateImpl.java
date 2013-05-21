package com.palermotenis.model.dao.modelos.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
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
import com.palermotenis.model.dao.modelos.ModeloDao;

@Repository("modeloDao")
public class ModeloDaoHibernateImpl extends AbstractHibernateDao<Modelo, Integer> implements ModeloDao {

    private static final Logger LOGGER = Logger.getLogger(ModeloDaoHibernateImpl.class);

    @Resource
    private PlatformTransactionManager transactionManager;

    @Override
    public List<Modelo> getModelosByTipoProducto(TipoProducto tipoProducto) {
        return queryBy("TipoProducto", "tipoProducto", tipoProducto);
    }

    @Override
    public List<Modelo> getModelosByMarcaAndTipoProducto(Marca marca, TipoProducto tipoProducto) {
        Map<String, Object> args = buildMarcaAndTipoProductoArgs(marca, tipoProducto);
        return queryBy("Marca,TipoProducto", args);
    }

    @Override
    public List<Modelo> getModelosWithRootActiveProductos(Marca marca, TipoProducto tipoProducto) {
        Map<String, Object> args = buildMarcaAndTipoProductoArgs(marca, tipoProducto);
        return queryBy("Marca,TipoProducto-Active", args);
    }

    @Override
    public List<Modelo> getModelosWithLeafActiveProductos(Marca marca, TipoProducto tipoProducto) {
        Map<String, Object> args = buildMarcaAndTipoProductoArgs(marca, tipoProducto);
        return queryBy("Marca,TipoProducto-Leafs", args);
    }

    @Override
    public List<Modelo> getModelosByPadre(Modelo padre) {
        return queryBy("Padre", "padre", padre);
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

    @Override
    public void updateTree(Integer right) {
        int rowsUpdated = edit("Tree", "right", right);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("updateTree() - " + rowsUpdated + " rows updated");
        }
    }

    @Override
    public void destroy(Modelo modelo) {
        Integer right = modelo.getRight();
        Integer left = modelo.getLeft();
        Integer width = modelo.getWidth();

        super.destroy(modelo);

        if (!modelo.isLeaf()) {
            int deleted = destroy("Range", argsMap("right", right, "left", left));
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("delete() - " + deleted + " rows deleted");
            }
        }
        int updated = edit("RightAfterDelete", argsMap("right", right, "width", width));
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("delete() - " + updated + " rows updated on right fix");
        }
        updated = edit("LeftAfterDelete", argsMap("right", right, "width", width));
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("delete() - " + updated + " rows updated on left fix");
        }
    }

    private Map<String, Object> argsMap(String argName1, Object arg1, String argName2, Object arg2) {
        return new ImmutableMap.Builder<String, Object>().put(argName1, arg1).put(argName2, arg2).build();
    }

}
