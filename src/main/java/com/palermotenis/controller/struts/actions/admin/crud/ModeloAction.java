package com.palermotenis.controller.struts.actions.admin.crud;

import java.io.InputStream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.dao.Dao;
import com.palermotenis.util.StringUtility;

/**
 * 
 * @author Poly
 */
public class ModeloAction extends ActionSupport {

    private static final long serialVersionUID = -3215895548120987791L;
    private static final Logger logger = Logger.getLogger(ModeloAction.class);
    private static final String JSON = "json";

    private Integer padreId;
    private Integer marcaId;
    private Integer tipoProductoId;
    private Integer modeloId;
    private Integer modeloOrden;
    private Integer modeloRgtId;
    private Integer modeloRgtOrden;

    private String nombre;

    private EntityManager entityManager;
    private PlatformTransactionManager transactionManager;

    private InputStream inputStream;

    @Autowired
    private Dao<Modelo, Integer> modeloDao;

    @Autowired
    private Dao<Marca, Integer> marcaDao;

    @Autowired
    private Dao<TipoProducto, Integer> tipoProductoDao;

    public String create() {

        Modelo m = new Modelo();

        if (padreId != -1) {
            m.setPadre(modeloDao.find(padreId));
        }

        m.setTipoProducto(tipoProductoDao.find(tipoProductoId));
        m.setMarca(marcaDao.find(marcaId));
        m.setNombre(nombre);

        modeloDao.create(m);

        logger.info("Se ha creado " + m);

        m.setOrden(m.getId());
        modeloDao.edit(m);

        inputStream = StringUtility.getInputString(m.getId().toString());

        return JSON;
    }

    public String edit() {
        try {
            Modelo m = modeloDao.find(getModeloId());

            logger.info("Intentando editar nombre " + m + " a " + nombre);

            m.setNombre(nombre);
            modeloDao.edit(m);

            logger.info("Se ha editado " + m + " con éxito");
            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            logger.error("No existe el modelo a eliminar", ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        } catch (Exception ex) {
            logger.error("Ha ocurrido un error al editar el modelo " + getModeloId(), ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        }
        return JSON;
    }

    public String destroy() {
        Modelo modelo = modeloDao.find(modeloId);
        if (modelo.getProducto() != null && modelo.getProducto().hasPedidos()) {
            inputStream = StringUtility
                .getInputString("El producto tiene pedidos asociados.\nPor favor, elimínelos o confírmelos antes de borrar este producto.");
            return JSON;
        }
        try {
            modeloDao.destroy(modelo);
            logger.info("Se ha eliminado el modelo cuya id es " + getModeloId());
            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            logger.error("No existe el modelo a eliminar", ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        } catch (Exception ex) {
            logger.error("Ha ocurrido un error al eliminar el modelo " + getModeloId(), ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        }
        return JSON;
    }

    public String move() {
        try {
            TransactionTemplate tt = new TransactionTemplate(transactionManager);
            tt.execute(new TransactionCallbackWithoutResult() {

                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    entityManager
                        .createNativeQuery("{call moverModelo(?,?,?,?)}")
                        .setParameter(1, modeloId)
                        .setParameter(2, modeloOrden)
                        .setParameter(3, modeloRgtId)
                        .setParameter(4, modeloRgtOrden)
                        .executeUpdate();
                }
            });
            inputStream = StringUtility.getInputString("OK");
        } catch (Exception ex) {
            logger.error("Ha ocurrido un error al mover el modelo " + modeloId, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        }
        return JSON;
    }

    /**
     * @param padreId
     *            the padreId to set
     */
    public void setPadreId(Integer padreId) {
        this.padreId = padreId;
    }

    /**
     * @param marcaId
     *            the marcaId to set
     */
    public void setMarcaId(Integer marcaId) {
        this.marcaId = marcaId;
    }

    /**
     * @param tipoProductoId
     *            the tipoProductoId to set
     */
    public void setTipoProductoId(Integer tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
    }

    /**
     * @param nombre
     *            the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the modeloId
     */
    public Integer getModeloId() {
        return modeloId;
    }

    /**
     * @param modeloId
     *            the modeloId to set
     */
    public void setModeloId(Integer modeloId) {
        this.modeloId = modeloId;
    }

    public void setModeloOrden(Integer modeloOrden) {
        this.modeloOrden = modeloOrden;
    }

    public void setModeloRgtId(Integer modeloRgtId) {
        this.modeloRgtId = modeloRgtId;
    }

    public void setModeloRgtOrden(Integer modeloRgtOrden) {
        this.modeloRgtOrden = modeloRgtOrden;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setJtaTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

}
