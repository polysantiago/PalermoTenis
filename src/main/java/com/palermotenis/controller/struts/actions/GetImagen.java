/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.controller.results.ImageCapable;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.imagenes.Imagen;
import com.palermotenis.model.beans.imagenes.ImagenEscalada;
import com.palermotenis.model.beans.imagenes.tipos.TipoImagen;
import com.palermotenis.util.imagen.ImagenUtil;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletContext;

/**
 *
 * @author Poly
 */
public class GetImagen extends ActionSupport implements ImageCapable{

    private String hash;
    private GenericDao<Imagen, Integer> imagenService;
    private GenericDao<TipoImagen, Character> tipoImagenService;
    private GenericDao<Modelo, Integer> modeloService;
    private Character tipoImagenId;
    private ImagenEscalada imagenEscalada;
    private Integer modeloId;
    private Collection<Imagen> imagenes;
    private ServletContext servletContext;

    @Override
    public String execute() throws IOException {

        Imagen imagen = imagenService.findBy("HashKey", "hashKey", hash);

        TipoImagen tipoImagen = tipoImagenService.find(tipoImagenId);
        File file = new File(servletContext.getRealPath(ImagenUtil.MODELOS_FOLDER), hash + ".jpg");
        imagenEscalada = ImagenUtil.getImagenEscalada(file, imagen, tipoImagen);

        return "imagenResult";
    }

    public String doList(){
        Modelo modelo = modeloService.find(modeloId);
        imagenes = modelo.getImagenes();
        return "imagenesList";
    }

    public byte[] getImageInBytes() {
        return getImagenEscalada().getImagen();
    }

    public String getContentType() {
        return getImagenEscalada().getContentType();
    }

    public int getContentLength() {
        return (int) getImagenEscalada().getTamanio();
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * @param imagenService the imagenService to set
     */
    public void setImagenService(GenericDao<Imagen, Integer> imagenService) {
        this.imagenService = imagenService;
    }

    /**
     * @param tipoImagen the tipoImagen to set
     */
    public void setTipoImagenId(Character tipoImagenId) {
        this.tipoImagenId = tipoImagenId;
    }

    /**
     * @param tipoImagenService the tipoImagenService to set
     */
    public void setTipoImagenService(GenericDao<TipoImagen, Character> tipoImagenService) {
        this.tipoImagenService = tipoImagenService;
    }
    
    /**
     * @return the imagenes
     */
    public Collection<Imagen> getImagenes() {
        return imagenes;
    }

    /**
     * @return the imagenEscalada
     */
    public ImagenEscalada getImagenEscalada() {
        return imagenEscalada;
    }

    /**
     * @param modelosService the modelosService to set
     */
    public void setModeloService(GenericDao<Modelo, Integer> modeloService) {
        this.modeloService = modeloService;
    }

    /**
     * @param modeloId the modeloId to set
     */
    public void setModeloId(Integer modeloId) {
        this.modeloId = modeloId;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
