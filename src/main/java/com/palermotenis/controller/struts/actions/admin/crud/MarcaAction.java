package com.palermotenis.controller.struts.actions.admin.crud;

import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.controller.results.ImageCapable;
import com.palermotenis.controller.struts.actions.JsonActionSupport;
import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.service.marcas.MarcaService;

public class MarcaAction extends JsonActionSupport implements ImageCapable {

    private static final long serialVersionUID = 5928001748528938792L;

    private final String SHOW_IMAGE = "showImage";

    private Collection<Marca> marcas;

    private Integer marcaId;
    private String nombre;

    private Marca tmpMarca;

    @Autowired
    private MarcaService marcaService;

    public String show() {
        return SHOW;
    }

    public String create() {
        marcaService.createNewMarca(nombre);
        success();
        return JSON;
    }

    public String edit() {
        try {
            marcaService.updateMarca(marcaId, nombre);
            success();
        } catch (HibernateException ex) {
            failure(ex);
        } catch (Exception ex) {
            failure(ex);
        }
        return JSON;
    }

    public String destroy() {
        try {
            marcaService.deleteMarca(marcaId);
            success();
        } catch (HibernateException ex) {
            failure(ex);
        } catch (Exception ex) {
            failure(ex);
        }
        return JSON;
    }

    public String showImage() {
        tmpMarca = marcaService.getMarcaById(marcaId);
        return SHOW_IMAGE;
    }

    @Override
    public byte[] getImageInBytes() {
        return tmpMarca.getImagen();
    }

    @Override
    public String getContentType() {
        return (tmpMarca == null) ? "text/html;charset=ISO-8859-1" : tmpMarca.getContentType();
    }

    @Override
    public int getContentLength() {
        try {
            return (tmpMarca == null) ? getInputStream().available() : (int) tmpMarca.getImagen().length;
        } catch (IOException ex) {
            Logger.getLogger(MarcaAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public Collection<Marca> getMarcas() {
        if (marcas == null) {
            marcas = marcaService.getAllMarcas();
        }
        return marcas;
    }

    public void setMarcaId(Integer marcaId) {
        this.marcaId = marcaId;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
