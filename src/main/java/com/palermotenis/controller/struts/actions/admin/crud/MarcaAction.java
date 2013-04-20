package com.palermotenis.controller.struts.actions.admin.crud;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.controller.results.ImageCapable;
import com.palermotenis.model.beans.Marca;
import com.palermotenis.util.StringUtility;

/**
 * 
 * @author Poly
 */
public class MarcaAction extends ActionSupport implements ImageCapable {

    private static final long serialVersionUID = 5928001748528938792L;

    private final String SHOW = "show";
    private final String SHOW_IMAGE = "showImage";
    private final String JSON = "json";

    private Collection<Marca> marcas;
    private Integer marcaId;
    private String nombre;
    private InputStream inputStream;

    private Marca tmpMarca;

    @Autowired
    private GenericDao<Marca, Integer> marcaDao;

    public String show() {
        marcas = marcaDao.findAll();
        return SHOW;
    }

    public String create() {
        Marca marca = new Marca();
        marca.setNombre(nombre);
        marcaDao.create(marca);

        inputStream = StringUtility.getInputString("OK");
        return JSON;
    }

    public String edit() {
        try {
            Marca marca = marcaDao.find(marcaId);
            marca.setNombre(nombre);
            marcaDao.edit(marca);
            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            Logger.getLogger(TipoProductoAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        } catch (Exception ex) {
            Logger.getLogger(TipoProductoAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        }
        return JSON;
    }

    public String destroy() {
        try {
            marcaDao.destroy(marcaDao.find(marcaId));
            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            Logger.getLogger(TipoProductoAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        } catch (Exception ex) {
            Logger.getLogger(TipoProductoAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        }
        return JSON;
    }

    public String showImage() {
        tmpMarca = marcaDao.find(marcaId);
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
            return (tmpMarca == null) ? inputStream.available() : (int) tmpMarca.getImagen().length;
        } catch (IOException ex) {
            Logger.getLogger(MarcaAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * @return the marcas
     */
    public Collection<Marca> getMarcas() {
        return marcas;
    }

    /**
     * @param marcaId
     *            the marcaId to set
     */
    public void setMarcaId(Integer marcaId) {
        this.marcaId = marcaId;
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
    public InputStream getInputStream() {
        return inputStream;
    }
}
