/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.export;

import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.beans.precios.Precio;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.valores.ValorClasificatorio;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jxl.CellView;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 * @author Poly
 */
public class ExcelExporter extends AbstractExporter implements ApplicationContextAware {

    private String[] headers;
    private ApplicationContext applicationContext;

    @Override
    public OutputStream export() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            WritableWorkbook w = Workbook.createWorkbook(out);

            int contTp = 0;
            for (TipoProducto tp : getTipoProductoService().findAll()) {
                WritableSheet s = w.createSheet(tp.getNombre().replace("/", "-"), contTp++);
                buildHeaders(s);
                Map<String, Object> args = new HashMap<String, Object>();
                args.put("tipoProducto", tp);
                List<Stock> stocks = getStockService().queryBy("TipoProducto", args);
                int contS = 1;
                for (Stock stock : stocks) {                    
                    Collection<? extends Precio> precios = stock.getProducto().getPrecios();
                    for (Precio precio : precios) {
                        s.addCell(new Label(0, contS, stock.getSucursal().getNombre()));
                        s.addCell(new Label(1, contS, stock.getProducto().getModelo().getMarca().getNombre()));
                        s.addCell(new Label(2, contS, buildNombreProducto(stock.getProducto())));
                        s.addCell(new Label(3, contS, buildValorClasificatorio(stock.getValorClasificatorio())));
                        s.addCell(new Label(4, contS, buildValorPresentacion(stock.getPresentacion())));
                        s.addCell(new Number(5, contS, stock.getStock()));
                        if (precio != null) {
                            s.addCell(new Label(6, contS, precio.getId().getPago().getNombre()));
                            s.addCell(new Label(7, contS, precio.getId().getMoneda().getCodigo()));
                            if (precio.getValor() != null) {
                                s.addCell(new Number(8, contS, precio.getValor()));
                                s.addCell(new Label(9, contS, precio.isEnOferta() ? "Sí" : "No"));
                                if (precio.isEnOferta()) {
                                    s.addCell(new Number(10, contS, precio.getValorOferta()));
                                }
                            }
                        }
                        contS++;
                    }
                }
            }
            w.write();
            w.close();
        } catch (WriteException ex) {
            Logger.getRootLogger().error(null, ex);
        } catch (IOException ex) {
            Logger.getRootLogger().error(null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getRootLogger().error(null, ex);
            }
        }
        return out;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }

    private void buildHeaders(WritableSheet w) throws WriteException {
        for (int i = 0; i < headers.length; i++) {
            CellView cv = new CellView();
            cv.setAutosize(true);
            w.setColumnView(i, cv);
            w.addCell(new Label(i, 0, headers[i]));
        }
    }

    private String buildNombreProducto(Producto producto) {
        String nombreProducto = "";
        for (Modelo padre : producto.getModelo().getPadres()) {
            nombreProducto += padre.getNombre() + " > ";
        }
        return nombreProducto += producto.getModelo().getNombre();
    }

    private String buildValorClasificatorio(ValorClasificatorio v) {
        return v == null ? "" : v.getTipoAtributo().getNombre() + ": " + v.getNombre();
    }

    private String buildValorPresentacion(Presentacion p) {
        return p == null ? "" : p.getTipoPresentacion().getNombre() + " " + p.getNombre();
    }
    
    private GenericDao<Stock, Integer> getStockService(){
        return (GenericDao<Stock, Integer>)applicationContext.getBean("stockService");
    }

    private GenericDao<TipoProducto, Integer> getTipoProductoService(){
        return (GenericDao<TipoProducto, Integer>)applicationContext.getBean("tipoProductoService");
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
