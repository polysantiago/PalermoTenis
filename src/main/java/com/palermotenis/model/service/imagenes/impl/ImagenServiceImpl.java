package com.palermotenis.model.service.imagenes.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ServletContextAware;

import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.imagenes.Imagen;
import com.palermotenis.model.beans.imagenes.ImagenEscalada;
import com.palermotenis.model.beans.imagenes.tipos.TipoImagen;
import com.palermotenis.model.dao.imagenes.ImagenDao;
import com.palermotenis.model.dao.imagenes.TipoImagenDao;
import com.palermotenis.model.service.imagenes.ImagenService;
import com.palermotenis.model.service.modelos.ModeloService;
import com.palermotenis.util.imagen.ImagenUtil;

@Service("imagenService")
@Transactional(propagation = Propagation.REQUIRED)
public class ImagenServiceImpl implements ImagenService, ServletContextAware {

    @Autowired
    private ImagenDao imagenDao;

    @Autowired
    private TipoImagenDao tipoImagenDao;

    @Autowired
    private ModeloService modeloService;

    private ServletContext servletContext;

    @Override
    public Imagen create(Integer modeloId, File imagenFile, String fileName, String contentType, String categoria)
            throws IOException {
        Modelo modelo = modeloService.getModeloById(modeloId);
        Imagen imagen = new Imagen(contentType, imagenFile.length(), fileName, categoria, modelo);

        FileInputStream file = new FileInputStream(imagenFile);
        BufferedImage bufferedImage = ImageIO.read(IOUtils.toBufferedInputStream(file));

        imagen.setAncho(bufferedImage.getWidth());
        imagen.setAlto(bufferedImage.getHeight());

        String hashKey = RandomStringUtils.randomAlphanumeric(32);
        imagen.setHashKey(hashKey);
        imagenDao.create(imagen);

        return imagen;
    }

    @Override
    public void upload(Integer modeloId, File imagenFile, String fileName, String contentType) throws IOException {
        Imagen imagen = create(modeloId, imagenFile, fileName, contentType, "Grande");
        String hashKey = imagen.getHashKey();
        File newimg = new File(servletContext.getRealPath(ImagenUtil.MODELOS_FOLDER), hashKey + ".jpg");

        FileUtils.copyFile(imagenFile, newimg);

        for (TipoImagen tipoImagen : tipoImagenDao.findAll()) {
            String nombre = hashKey + "_" + tipoImagen.getTipo() + ".jpg";

            File newrezimg = new File(servletContext.getRealPath(ImagenUtil.RESIZES_FOLDER), nombre);
            FileImageOutputStream imageOutput = new FileImageOutputStream(newrezimg);
            byte[] img = ImagenUtil.createImagenEscalada(newimg, imagen, tipoImagen).getImagen();
            imageOutput.write(img, 0, img.length);
            imageOutput.close();
        }
    }

    @Override
    public void destroy(Integer imagenId) {
        Imagen imagen = imagenDao.find(imagenId);

        File imgFile = new File(servletContext.getRealPath(ImagenUtil.MODELOS_FOLDER), imagen.getHashKey() + ".jpg");
        if (imgFile.exists()) {
            imgFile.delete();
        }
        for (TipoImagen tipoImagen : tipoImagenDao.findAll()) {
            String nombre = imagen.getHashKey() + "_" + tipoImagen.getTipo() + ".jpg";
            File rezimgFile = new File(servletContext.getRealPath(ImagenUtil.RESIZES_FOLDER), nombre);
            if (rezimgFile.exists()) {
                rezimgFile.delete();
            }
        }
        imagenDao.destroy(imagen);
    }

    @Override
    public Imagen getImagenByHashKey(String hashKey) {
        return imagenDao.findBy("HashKey", "hashKey", hashKey);
    }

    @Override
    public ImagenEscalada getImagenEscalada(String hashKey, Character tipoImagenId) throws IOException {
        Imagen imagen = getImagenByHashKey(hashKey);
        TipoImagen tipoImagen = getTipoImagen(tipoImagenId);

        File file = new File(servletContext.getRealPath(ImagenUtil.MODELOS_FOLDER), hashKey + ".jpg");
        return ImagenUtil.getImagenEscalada(file, imagen, tipoImagen);
    }

    @Override
    public TipoImagen getTipoImagen(Character tipo) throws EntityNotFoundException {
        return tipoImagenDao.load(tipo);
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public List<Imagen> getImagenesByModelo(Integer modeloId) {
        Modelo modelo = modeloService.getModeloById(modeloId);
        return modelo.getImagenes();
    }

}
