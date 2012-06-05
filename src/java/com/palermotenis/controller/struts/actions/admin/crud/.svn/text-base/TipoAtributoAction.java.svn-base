/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.admin.crud;

import com.google.common.collect.ImmutableMap;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Unidad;
import com.palermotenis.model.beans.atributos.Atributo;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import com.palermotenis.model.beans.atributos.tipos.clasif.TipoAtributoClasif;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.util.StringUtility;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.HibernateException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author Poly
 */
public class TipoAtributoAction extends ActionSupport {

    private final String CREATE = "create";
    private final String EDIT = "edit";
    private final String DESTROY = "destroy";
    private final String SHOW = "show";
    private final String JSON = "json";
    private GenericDao<TipoAtributo, Integer> tipoAtributoService;
    private GenericDao<TipoAtributoClasif, Character> tipoAtributoClasifService;
    private GenericDao<TipoProducto, Integer> tipoProductoService;
    private GenericDao<Unidad, Integer> unidadService;
    private EntityManager entityManager;
    private Collection<TipoAtributo> tiposAtributo;
    private Collection<TipoAtributoClasif> clasificaciones;
    private Integer tipoProductoId;
    private Integer tipoAtributoId;
    private Integer unidadId;
    private String clase;
    private String nombre;
    private Character clasif;
    private InputStream inputStream;
    private Map<String, String> clasesMap = new HashMap<String, String>();
    private PlatformTransactionManager transactionManager;

    public String show() {
        TipoProducto tipoProducto = tipoProductoService.find(tipoProductoId);
        Map<String, Object> args = new ImmutableMap.Builder<String, Object>().put("tipoProducto", tipoProducto).build();

        tiposAtributo = tipoAtributoService.queryBy("TipoProducto", args);

        getClasesMap().put("java.lang.String", "Texto");
        getClasesMap().put("java.lang.Integer", "Entero");
        getClasesMap().put("java.lang.Double", "Decimal");
        getClasesMap().put("java.lang.Boolean", "Binario");

        clasificaciones = tipoAtributoClasifService.findAll();

        return SHOW;
    }
    
    public String create() {
        try {
            final TipoAtributo tipoAtributo = new TipoAtributo();
            tipoAtributo.setTipoProducto(tipoProductoService.find(tipoProductoId));
            tipoAtributo.setNombre(nombre);
            tipoAtributo.setClase(Class.forName(clase));

            Unidad unidad = null;
            if (unidadId != null) {
                unidad = unidadService.find(unidadId);
            }

            tipoAtributo.setUnidad(unidad);
            tipoAtributoService.create(tipoAtributo);

            TransactionTemplate tt = new TransactionTemplate(transactionManager);
            tt.execute(new TransactionCallbackWithoutResult() {

                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    String query = "UPDATE TipoAtributo t SET t.class = :clazz WHERE t = :tipoAtributo";
                    entityManager.createQuery(query).setParameter("clazz", clasif).setParameter("tipoAtributo", tipoAtributo).executeUpdate();
                }
            });
                        
            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            Logger.getLogger(TipoAtributoAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TipoAtributoAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        } catch (Exception ex) {
            Logger.getLogger(TipoAtributoAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        }
        return JSON;
    }

    public String editName() {
        try {
            TipoAtributo tipoAtributo = tipoAtributoService.find(tipoAtributoId);
            tipoAtributo.setNombre(nombre);
            tipoAtributoService.edit(tipoAtributo);

            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            Logger.getLogger(TipoAtributoAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        } catch (Exception ex) {
            Logger.getLogger(TipoAtributoAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        }
        return JSON;
    }

    public String edit() {
        try {
            TipoAtributo tipoAtributo = tipoAtributoService.find(tipoAtributoId);
            Unidad unidad = null;

            if (unidadId != null) {
                unidad = unidadService.find(unidadId);
            }

            if (tipoAtributo.getClase() == Double.class && clase.equals("java.lang.Integer")) {
                for (Atributo a : tipoAtributo.getAtributos()) {
                    if (a.getValor() != null && a.getValor().getUnidad() != null) {
                        int i = (int) ((Double) a.getValor().getUnidad()).doubleValue();
                        a.getValor().setUnidad(Integer.valueOf(i));
                    }
                }
            }

            tipoAtributo.setUnidad(unidad);
            tipoAtributo.setClase(Class.forName(clase));

            entityManager.createQuery("UPDATE TipoAtributo t SET t.class = :clazz WHERE t = :tipoAtributo").setParameter("clazz", clasif).setParameter("tipoAtributo", tipoAtributo).executeUpdate();

            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            Logger.getLogger(TipoAtributoAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TipoAtributoAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        } catch (Exception ex) {
            Logger.getLogger(TipoAtributoAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        }
        return JSON;
    }

    public String destroy() {
        try {
            tipoAtributoService.destroy(tipoAtributoService.find(tipoAtributoId));
            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            Logger.getLogger(TipoAtributoAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        }
        return JSON;
    }

    /**
     * @param tipoAtributoService the tipoAtributoService to set
     */
    public void setTipoAtributoService(GenericDao<TipoAtributo, Integer> tipoAtributoService) {
        this.tipoAtributoService = tipoAtributoService;
    }

    /**
     * @return the tiposAtributo
     */
    public Collection<TipoAtributo> getTiposAtributo() {
        return tiposAtributo;
    }

    /**
     * @return the clasesMap
     */
    public Map<String, String> getClasesMap() {
        return clasesMap;
    }

    /**
     * @param tipoProductoId the tipoProductoId to set
     */
    public void setTipoProductoId(Integer tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
    }

    /**
     * @param tipoAtributoId the tipoAtributoId to set
     */
    public void setTipoAtributoId(Integer tipoAtributoId) {
        this.tipoAtributoId = tipoAtributoId;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * @param unidadService the unidadesService to set
     */
    public void setUnidadService(GenericDao<Unidad, Integer> unidadService) {
        this.unidadService = unidadService;
    }

    /**
     * @param unidadId the unidadId to set
     */
    public void setUnidadId(Integer unidadId) {
        this.unidadId = unidadId;
    }

    /**
     * @param clase the clase to set
     */
    public void setClase(String clase) {
        this.clase = clase;
    }

    /**
     * @param clasif the clasif to set
     */
    public void setClasif(Character clasif) {
        this.clasif = clasif;
    }

    /**
     * @param tipoAtributoClasifService the clasifService to set
     */
    public void setTipoAtributoClasifService(GenericDao<TipoAtributoClasif, Character> tipoAtributoClasifService) {
        this.tipoAtributoClasifService = tipoAtributoClasifService;
    }

    public Collection<TipoAtributoClasif> getClasificaciones() {
        return clasificaciones;
    }

    public void setTipoProductoService(GenericDao<TipoProducto, Integer> tipoProductoService) {
        this.tipoProductoService = tipoProductoService;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setJtaTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

}
