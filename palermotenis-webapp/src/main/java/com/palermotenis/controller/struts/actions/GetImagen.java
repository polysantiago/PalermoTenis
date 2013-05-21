package com.palermotenis.controller.struts.actions;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.results.ImageCapable;
import com.palermotenis.model.beans.imagenes.Imagen;
import com.palermotenis.model.beans.imagenes.ImagenEscalada;
import com.palermotenis.model.service.imagenes.ImagenService;

public class GetImagen extends ActionSupport implements ImageCapable {

    private static final String IMAGENES_LIST = "imagenesList";

    private static final String IMAGEN_RESULT = "imagenResult";

    private static final long serialVersionUID = 368332131271534228L;

    private String hash;
    private Character tipoImagenId;
    private ImagenEscalada imagenEscalada;
    private Integer modeloId;
    private List<Imagen> imagenes;

    @Autowired
    private ImagenService imagenService;

    @Override
    public String execute() throws IOException {
        imagenEscalada = imagenService.getImagenEscalada(hash, tipoImagenId);
        return IMAGEN_RESULT;
    }

    public String doList() {
        imagenes = imagenService.getImagenesByModelo(modeloId);
        return IMAGENES_LIST;
    }

    @Override
    public byte[] getImageInBytes() {
        return getImagenEscalada().getImagen();
    }

    @Override
    public String getContentType() {
        return getImagenEscalada().getContentType();
    }

    @Override
    public int getContentLength() {
        return (int) getImagenEscalada().getTamanio();
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setTipoImagenId(Character tipoImagenId) {
        this.tipoImagenId = tipoImagenId;
    }

    public List<Imagen> getImagenes() {
        return imagenes;
    }

    public ImagenEscalada getImagenEscalada() {
        return imagenEscalada;
    }

    public void setModeloId(Integer modeloId) {
        this.modeloId = modeloId;
    }
}
