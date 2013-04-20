/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.admin.crud;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.web.context.ServletContextAware;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.imagenes.Imagen;
import com.palermotenis.model.beans.imagenes.tipos.TipoImagen;
import com.palermotenis.util.StringUtility;
import com.palermotenis.util.imagen.ImagenUtil;

/**
 * 
 * @author Poly
 */
public class ImagenAction extends ActionSupport implements ServletContextAware {

    private static final long serialVersionUID = -5932738809854685114L;

    private static final String UPLOAD = "upload";
    private static final String JSON = "json";

    private File imagen;
    private Integer imagenId;
    private String imagenContentType;
    private String imagenFileName;
    private Integer modeloId;
    private InputStream inputStream;
    private ServletContext servletContext;

    @Autowired
    private GenericDao<Modelo, Integer> modeloDao;

    @Autowired
    private GenericDao<Imagen, Integer> imagenDao;

    @Autowired
    private GenericDao<TipoImagen, Character> tipoImagenDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String upload() {
        FileInputStream file;
        try {
            Imagen i = new Imagen();
            file = new FileInputStream(imagen);
            i.setNombre(imagenFileName);
            i.setTipo(imagenContentType);
            i.setTamanio(imagen.length());
            i.setCategoria("Grande");
            i.setModelo(modeloDao.find(getModeloId()));

            InputStream is = new ByteArrayInputStream(IOUtils.toByteArray(file));
            BufferedImage im = ImageIO.read(is);

            i.setAncho(im.getWidth());
            i.setAlto(im.getHeight());

            String hashKey = passwordEncoder.encodePassword(i.getNombre(), null);
            i.setHashKey(hashKey);
            imagenDao.create(i);

            File newimg = new File(servletContext.getRealPath(ImagenUtil.MODELOS_FOLDER), hashKey + ".jpg");
            FileUtils.copyFile(imagen, newimg);

            for (TipoImagen ti : tipoImagenDao.findAll()) {
                String nombre = hashKey + "_" + ti.getTipo() + ".jpg";
                File newrezimg = new File(servletContext.getRealPath(ImagenUtil.RESIZES_FOLDER), nombre);
                FileImageOutputStream imageOutput = new FileImageOutputStream(newrezimg);
                byte[] img = ImagenUtil.createImagenEscalada(newimg, i, ti).getImagen();
                imageOutput.write(img, 0, img.length);
                imageOutput.close();
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImagenAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ImagenAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return UPLOAD;
    }

    public String destroy() {
        Imagen i = imagenDao.find(imagenId);
        try {
            File imgFile = new File(servletContext.getRealPath(ImagenUtil.MODELOS_FOLDER), i.getHashKey() + ".jpg");
            if (imgFile.exists()) {
                imgFile.delete();
            }
            for (TipoImagen ti : tipoImagenDao.findAll()) {
                String nombre = i.getHashKey() + "_" + ti.getTipo() + ".jpg";
                File rezimgFile = new File(servletContext.getRealPath(ImagenUtil.RESIZES_FOLDER), nombre);
                if (rezimgFile.exists()) {
                    rezimgFile.delete();
                }
            }
            imagenDao.destroy(i);
            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            Logger.getLogger(ImagenAction.class.getName()).log(Level.SEVERE, null, ex);
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        } catch (Exception e) {
            Logger.getLogger(ImagenAction.class.getName()).log(Level.SEVERE, null, e);
            inputStream = StringUtility.getInputString(e.getLocalizedMessage());
        }
        return JSON;
    }

    /**
     * @param imagen
     *            the imagen to set
     */
    public void setImagen(File imagen) {
        this.imagen = imagen;
    }

    /**
     * @param imagenContentType
     *            the imagenContentType to set
     */
    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    /**
     * @param imagenFileName
     *            the imagenFileName to set
     */
    public void setImagenFileName(String imagenFileName) {
        this.imagenFileName = imagenFileName;
    }

    /**
     * @param modeloId
     *            the modeloId to set
     */
    public void setModeloId(Integer modeloId) {
        this.modeloId = modeloId;
    }

    /**
     * @return the modeloId
     */
    public Integer getModeloId() {
        return modeloId;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * @param imagenId
     *            the imagenId to set
     */
    public void setImagenId(Integer imagenId) {
        this.imagenId = imagenId;
    }

    @Override
    public void setServletContext(ServletContext sc) {
        this.servletContext = sc;
    }

}
