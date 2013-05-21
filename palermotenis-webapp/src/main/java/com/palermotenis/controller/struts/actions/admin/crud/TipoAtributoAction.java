package com.palermotenis.controller.struts.actions.admin.crud;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Maps;
import com.palermotenis.controller.struts.actions.InputStreamActionSupport;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import com.palermotenis.model.beans.atributos.tipos.clasif.TipoAtributoClasif;
import com.palermotenis.model.service.atributos.tipos.TipoAtributoService;

public class TipoAtributoAction extends InputStreamActionSupport {

    private static final long serialVersionUID = -2783725459788707738L;

    private List<TipoAtributo> tiposAtributo;
    private Collection<TipoAtributoClasif> clasificaciones;
    private Integer tipoProductoId;
    private Integer tipoAtributoId;
    private Integer unidadId;
    private String clase;
    private String nombre;
    private Character clasif;
    private final Map<String, String> clasesMap = Maps.newLinkedHashMap();

    @Autowired
    private TipoAtributoService tipoAtributoService;

    public String show() {
        tiposAtributo = tipoAtributoService.getTiposAtributosByTipoProducto(tipoProductoId);

        clasesMap.put("java.lang.String", "Texto");
        clasesMap.put("java.lang.Integer", "Entero");
        clasesMap.put("java.lang.Double", "Decimal");
        clasesMap.put("java.lang.Boolean", "Binario");

        clasificaciones = tipoAtributoService.getTipoAtributoClasificatorioMetadata();

        return SHOW;
    }

    public String create() {
        try {
            tipoAtributoService.createNewTipoAtributo(tipoProductoId, nombre, clase, unidadId, clasif);
            success();
        } catch (Exception ex) {
            failure(ex);
        }
        return JSON;
    }

    public String editName() {
        try {
            tipoAtributoService.updateTipoAtributo(tipoAtributoId, nombre);
            success();
        } catch (Exception ex) {
            failure(ex);
        }
        return JSON;
    }

    public String edit() {
        try {
            tipoAtributoService.updateTipoAtributo(tipoAtributoId, unidadId, clase, clasif);
            success();
        } catch (Exception ex) {
            failure(ex);
        }
        return JSON;
    }

    public String destroy() {
        try {
            tipoAtributoService.deleteTipoAtributo(tipoAtributoId);
            success();
        } catch (HibernateException ex) {
            failure(ex);
        }
        return JSON;
    }

    public List<TipoAtributo> getTiposAtributo() {
        return tiposAtributo;
    }

    public Map<String, String> getClasesMap() {
        return clasesMap;
    }

    public void setTipoProductoId(Integer tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
    }

    public void setTipoAtributoId(Integer tipoAtributoId) {
        this.tipoAtributoId = tipoAtributoId;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setUnidadId(Integer unidadId) {
        this.unidadId = unidadId;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public void setClasif(Character clasif) {
        this.clasif = clasif;
    }

    public Collection<TipoAtributoClasif> getClasificaciones() {
        return clasificaciones;
    }

}
