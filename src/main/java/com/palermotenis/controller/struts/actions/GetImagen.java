package com.palermotenis.controller.struts.actions;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.results.ImageCapable;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.imagenes.Imagen;
import com.palermotenis.model.beans.imagenes.ImagenEscalada;
import com.palermotenis.model.beans.imagenes.tipos.TipoImagen;
import com.palermotenis.model.dao.Dao;
import com.palermotenis.util.imagen.ImagenUtil;

/**
 *
 * @author Poly
 */
public class GetImagen extends ActionSupport implements ImageCapable{

	private static final long serialVersionUID = 368332131271534228L;
	
	private String hash;
    private Character tipoImagenId;
    private ImagenEscalada imagenEscalada;
    private Integer modeloId;
    private Collection<Imagen> imagenes;
    private ServletContext servletContext;
    
    @Autowired
    private Dao<Imagen, Integer> imagenDao;
    
    @Autowired
    private Dao<TipoImagen, Character> tipoImagenDao;
    
    @Autowired
    private Dao<Modelo, Integer> modeloDao;

    @Override
    public String execute() throws IOException {

        Imagen imagen = imagenDao.findBy("HashKey", "hashKey", hash);

        TipoImagen tipoImagen = tipoImagenDao.find(tipoImagenId);
        File file = new File(servletContext.getRealPath(ImagenUtil.MODELOS_FOLDER), hash + ".jpg");
        imagenEscalada = ImagenUtil.getImagenEscalada(file, imagen, tipoImagen);

        return "imagenResult";
    }

    public String doList(){
        Modelo modelo = modeloDao.find(modeloId);
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
     * @param tipoImagen the tipoImagen to set
     */
    public void setTipoImagenId(Character tipoImagenId) {
        this.tipoImagenId = tipoImagenId;
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
     * @param modeloId the modeloId to set
     */
    public void setModeloId(Integer modeloId) {
        this.modeloId = modeloId;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
