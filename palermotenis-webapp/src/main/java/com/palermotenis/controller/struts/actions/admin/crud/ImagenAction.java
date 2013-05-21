package com.palermotenis.controller.struts.actions.admin.crud;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.controller.struts.actions.InputStreamActionSupport;
import com.palermotenis.model.service.imagenes.ImagenService;

public class ImagenAction extends InputStreamActionSupport {

    private static final long serialVersionUID = -5932738809854685114L;

    private static final String UPLOAD = "upload";
    private static final String JSON = "json";

    private File imagen;
    private Integer imagenId;
    private String imagenContentType;
    private String imagenFileName;
    private Integer modeloId;

    @Autowired
    private ImagenService imagenService;

    public String upload() {
        try {
            imagenService.upload(modeloId, imagen, imagenFileName, imagenContentType);
        } catch (FileNotFoundException ex) {
            failure(ex);
        } catch (IOException ex) {
            failure(ex);
        }
        return UPLOAD;
    }

    public String destroy() {
        try {
            imagenService.destroy(imagenId);
            success();
        } catch (HibernateException ex) {
            failure(ex);
        } catch (Exception ex) {
            failure(ex);
        }
        return JSON;
    }

    public void setImagen(File imagen) {
        this.imagen = imagen;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public void setImagenFileName(String imagenFileName) {
        this.imagenFileName = imagenFileName;
    }

    public void setModeloId(Integer modeloId) {
        this.modeloId = modeloId;
    }

    public Integer getModeloId() {
        return modeloId;
    }

    public void setImagenId(Integer imagenId) {
        this.imagenId = imagenId;
    }

}
