/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.admin.crud;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;

import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.util.StringUtility;

import java.io.InputStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;


/**
 *
 * @author Poly
 */
public class ModeloAction extends ActionSupport {

    private static final String JSON = "json";
    
    private Integer padreId;
    private Integer marcaId;
    private Integer tipoProductoId;
    private String nombre;

    private GenericDao<Modelo, Integer> modeloService;
    private GenericDao<Marca, Integer> marcaService;
    private GenericDao<TipoProducto, Integer> tipoProductoService;

    private Integer modeloId;
    private Integer modeloOrden;
    private Integer modeloRgtId;
    private Integer modeloRgtOrden;

    private EntityManager entityManager;
    private PlatformTransactionManager transactionManager;

    private InputStream inputStream;
    private static final Logger logger = Logger.getLogger(ModeloAction.class);

    public String create() {

        Modelo m = new Modelo();

        if (padreId != -1) {
            m.setPadre(modeloService.find(padreId));
        }

        m.setTipoProducto(tipoProductoService.find(tipoProductoId));
        m.setMarca(marcaService.find(marcaId));
        m.setNombre(nombre);

        modeloService.create(m);

        logger.info("Se ha creado "+m);

        m.setOrden(m.getId());
        modeloService.edit(m);

        inputStream = StringUtility.getInputString(m.getId().toString());

        return JSON;
    }



    public String edit(){
        try {
            Modelo m = modeloService.find(getModeloId());

            logger.info("Intentando editar nombre " +m+ " a "+nombre);

            m.setNombre(nombre);
            modeloService.edit(m);

            logger.info("Se ha editado "+m+" con éxito");
            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            logger.error("No existe el modelo a eliminar", ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        } catch (Exception ex) {
            logger.error("Ha ocurrido un error al editar el modelo "+getModeloId(), ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        }
        return JSON;
    }

    public String destroy(){
        Modelo modelo = modeloService.find(modeloId);
        if(modelo.getProducto() != null && modelo.getProducto().hasPedidos()){
            inputStream = StringUtility.getInputString("El producto tiene pedidos asociados.\nPor favor, elimínelos o confírmelos antes de borrar este producto.");
            return JSON;
        }
        try {
            modeloService.destroy(modelo);
            logger.info("Se ha eliminado el modelo cuya id es "+getModeloId());
            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            logger.error("No existe el modelo a eliminar", ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        } catch (Exception ex){
            logger.error("Ha ocurrido un error al eliminar el modelo "+getModeloId(), ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        }
        return JSON;
    }

    public String move(){
        try {
            TransactionTemplate tt = new TransactionTemplate(transactionManager);
            tt.execute(new TransactionCallbackWithoutResult() {

                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    entityManager.createNativeQuery("{call moverModelo(?,?,?,?)}")
                        .setParameter(1, modeloId).setParameter(2, modeloOrden)
                        .setParameter(3, modeloRgtId).setParameter(4, modeloRgtOrden)
                        .executeUpdate();
                }
            });
            inputStream = StringUtility.getInputString("OK");
        } catch (Exception ex) {
            logger.error("Ha ocurrido un error al mover el modelo "+modeloId, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        }
        return JSON;
    }

    /**
     * @param padreId the padreId to set
     */
    public void setPadreId(Integer padreId) {
        this.padreId = padreId;
    }

    /**
     * @param marcaId the marcaId to set
     */
    public void setMarcaId(Integer marcaId) {
        this.marcaId = marcaId;
    }

    /**
     * @param tipoProductoId the tipoProductoId to set
     */
    public void setTipoProductoId(Integer tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
    }


    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @param modeloService the modelosService to set
     */
    public void setModeloService(GenericDao<Modelo, Integer> modeloService) {
        this.modeloService = modeloService;
    }

    /**
     * @param marcaService the marcasService to set
     */
    public void setMarcaService(GenericDao<Marca, Integer> marcaService) {
        this.marcaService = marcaService;
    }

    /**
     * @param tiposProductoService the tiposProductoService to set
     */
    public void setTipoProductoService(GenericDao<TipoProducto, Integer> tipoProductoService) {
        this.tipoProductoService = tipoProductoService;
    }

    /**
     * @return the modeloId
     */
    public Integer getModeloId() {
        return modeloId;
    }

    /**
     * @param modeloId the modeloId to set
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
