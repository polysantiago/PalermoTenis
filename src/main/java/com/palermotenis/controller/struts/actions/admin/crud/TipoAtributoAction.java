package com.palermotenis.controller.struts.actions.admin.crud;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.google.common.collect.ImmutableMap;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.controller.struts.actions.JsonActionSupport;
import com.palermotenis.model.beans.Unidad;
import com.palermotenis.model.beans.atributos.Atributo;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import com.palermotenis.model.beans.atributos.tipos.clasif.TipoAtributoClasif;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;

/**
 * 
 * @author Poly
 */
public class TipoAtributoAction extends JsonActionSupport {

    private static final long serialVersionUID = -2783725459788707738L;

    private final String SHOW = "show";

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
    private final Map<String, String> clasesMap = new HashMap<String, String>();
    private PlatformTransactionManager transactionManager;

    @Autowired
    private GenericDao<TipoAtributo, Integer> tipoAtributoDao;

    @Autowired
    private GenericDao<TipoAtributoClasif, Character> tipoAtributoClasifDao;

    @Autowired
    private GenericDao<TipoProducto, Integer> tipoProductoDao;

    @Autowired
    private GenericDao<Unidad, Integer> unidadDao;

    public String show() {
        TipoProducto tipoProducto = tipoProductoDao.find(tipoProductoId);
        Map<String, Object> args = new ImmutableMap.Builder<String, Object>().put("tipoProducto", tipoProducto).build();

        tiposAtributo = tipoAtributoDao.queryBy("TipoProducto", args);

        getClasesMap().put("java.lang.String", "Texto");
        getClasesMap().put("java.lang.Integer", "Entero");
        getClasesMap().put("java.lang.Double", "Decimal");
        getClasesMap().put("java.lang.Boolean", "Binario");

        clasificaciones = tipoAtributoClasifDao.findAll();

        return SHOW;
    }

    public String create() {
        try {
            final TipoAtributo tipoAtributo = new TipoAtributo();
            tipoAtributo.setTipoProducto(tipoProductoDao.find(tipoProductoId));
            tipoAtributo.setNombre(nombre);
            tipoAtributo.setClase(Class.forName(clase));

            Unidad unidad = null;
            if (unidadId != null) {
                unidad = unidadDao.find(unidadId);
            }

            tipoAtributo.setUnidad(unidad);
            tipoAtributoDao.create(tipoAtributo);

            TransactionTemplate tt = new TransactionTemplate(transactionManager);
            tt.execute(new TransactionCallbackWithoutResult() {

                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    String query = "UPDATE TipoAtributo t SET t.class = :clazz WHERE t = :tipoAtributo";
                    entityManager
                        .createQuery(query)
                        .setParameter("clazz", clasif)
                        .setParameter("tipoAtributo", tipoAtributo)
                        .executeUpdate();
                }
            });

            success();
        } catch (HibernateException ex) {
            failure(ex);
        } catch (ClassNotFoundException ex) {
            failure(ex);
        } catch (Exception ex) {
            failure(ex);
        }
        return JSON;
    }

    public String editName() {
        try {
            TipoAtributo tipoAtributo = tipoAtributoDao.find(tipoAtributoId);
            tipoAtributo.setNombre(nombre);
            tipoAtributoDao.edit(tipoAtributo);

            success();
        } catch (HibernateException ex) {
            failure(ex);
        } catch (Exception ex) {
            failure(ex);
        }
        return JSON;
    }

    public String edit() {
        try {
            TipoAtributo tipoAtributo = tipoAtributoDao.find(tipoAtributoId);
            Unidad unidad = null;

            if (unidadId != null) {
                unidad = unidadDao.find(unidadId);
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

            entityManager
                .createQuery("UPDATE TipoAtributo t SET t.class = :clazz WHERE t = :tipoAtributo")
                .setParameter("clazz", clasif)
                .setParameter("tipoAtributo", tipoAtributo)
                .executeUpdate();

            success();
        } catch (HibernateException ex) {
            failure(ex);
        } catch (ClassNotFoundException ex) {
            failure(ex);
        } catch (Exception ex) {
            failure(ex);
        }
        return JSON;
    }

    public String destroy() {
        try {
            tipoAtributoDao.destroy(tipoAtributoDao.find(tipoAtributoId));
            success();
        } catch (HibernateException ex) {
            failure(ex);
        }
        return JSON;
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
     * @param tipoProductoId
     *            the tipoProductoId to set
     */
    public void setTipoProductoId(Integer tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
    }

    /**
     * @param tipoAtributoId
     *            the tipoAtributoId to set
     */
    public void setTipoAtributoId(Integer tipoAtributoId) {
        this.tipoAtributoId = tipoAtributoId;
    }

    /**
     * @param nombre
     *            the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the inputStream
     */
    @Override
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * @param unidadId
     *            the unidadId to set
     */
    public void setUnidadId(Integer unidadId) {
        this.unidadId = unidadId;
    }

    /**
     * @param clase
     *            the clase to set
     */
    public void setClase(String clase) {
        this.clase = clase;
    }

    /**
     * @param clasif
     *            the clasif to set
     */
    public void setClasif(Character clasif) {
        this.clasif = clasif;
    }

    public Collection<TipoAtributoClasif> getClasificaciones() {
        return clasificaciones;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setJtaTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

}
