package com.palermotenis.util.imagen;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import com.palermotenis.model.beans.imagenes.Imagen;
import com.palermotenis.model.beans.imagenes.ImagenEscalada;
import com.palermotenis.model.beans.imagenes.tipos.TipoImagen;

public class ImagenUtil {

    public final static String MODELOS_FOLDER = "/images/modelos/";
    public final static String RESIZES_FOLDER = "/images/modelos/resizes/";
    private static Map<ImagenKey, ImagenEscalada> imagenMap = new HashMap<ImagenKey, ImagenEscalada>();

    public static ImagenEscalada getImagenEscalada(File file, Imagen imagen, TipoImagen tipoImagen) throws IOException {
        ImagenKey key = new ImagenKey(imagen, tipoImagen);
        if (imagenMap.containsKey(key)) {
            return imagenMap.get(key);
        }
        ImagenEscalada ie = createImagenEscalada(file, imagen, tipoImagen);
        imagenMap.put(key, ie);
        return ie;
    }

    public static ImagenEscalada createImagenEscalada(File file, Imagen imagen, TipoImagen tipoImagen)
            throws IOException {

        BufferedImage im = ImageIO.read(file);

        int thumbWidth = tipoImagen.getWidth();
        int thumbHeight = tipoImagen.getHeight();

        double thumbRatio = (double) thumbWidth / (double) thumbHeight;
        int imageWidth = im.getWidth(null);
        int imageHeight = im.getHeight(null);
        double imageRatio = (double) imageWidth / (double) imageHeight;
        if (thumbRatio < imageRatio) {
            thumbHeight = (int) (thumbWidth / imageRatio);
        } else {
            thumbWidth = (int) (thumbHeight * imageRatio);
        }

        BufferedImage thumbImage = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics2D = thumbImage.createGraphics();

        graphics2D.setComposite(AlphaComposite.Src);

        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
            RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);

        graphics2D.drawImage(im, 0, 0, thumbWidth, thumbHeight, null);
        graphics2D.dispose();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        String extension = imagen.getNombre().substring(imagen.getNombre().toLowerCase().lastIndexOf('.') + 1);

        Iterator<ImageWriter> it = ImageIO.getImageWritersByFormatName(extension);
        ImageWriter writer = it.next();
        ImageWriteParam iwp = writer.getDefaultWriteParam();
        iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        if (extension.equalsIgnoreCase("gif")) {
            iwp.setCompressionType("lzw");
        }
        iwp.setCompressionQuality(1.0f);

        ImageOutputStream output = ImageIO.createImageOutputStream(os);
        output.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        writer.setOutput(output);

        IIOImage image = new IIOImage(thumbImage, null, null);
        writer.write(null, image, iwp);
        writer.dispose();

        ImagenEscalada newImg = new ImagenEscalada();
        newImg.setContentType(imagen.getTipo());
        newImg.setImagen(os.toByteArray());
        newImg.setImagenOriginal(imagen);
        newImg.setNombre(imagen.getNombre());
        newImg.setTamanio(os.size());
        newImg.setTipo(tipoImagen);
        newImg.setAlto(thumbHeight);
        newImg.setAncho(imageWidth);

        imagen.setAlto(im.getHeight());
        imagen.setAncho(im.getWidth());

        return newImg;
    }

}
