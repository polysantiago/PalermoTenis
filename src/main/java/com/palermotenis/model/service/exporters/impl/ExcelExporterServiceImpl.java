package com.palermotenis.model.service.exporters.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import jxl.CellView;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.precios.Precio;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.beans.valores.ValorClasificatorio;
import com.palermotenis.model.service.exporters.ExcelExporterService;
import com.palermotenis.model.service.productos.tipos.TipoProductoService;
import com.palermotenis.model.service.stock.StockService;

@Service("excelExporterService")
public class ExcelExporterServiceImpl implements ExcelExporterService {

    private static final Logger LOGGER = Logger.getLogger(ExcelExporterServiceImpl.class);

    @Autowired
    private StockService stockService;

    @Autowired
    private TipoProductoService tipoProductoService;

    @Override
    public ByteArrayOutputStream export() {
        return export(new String[] {});
    }

    @Override
    @Transactional(readOnly = true)
    public ByteArrayOutputStream export(String[] headers) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            WritableWorkbook workbook = Workbook.createWorkbook(out);

            int contTp = 0;
            for (TipoProducto tipoProducto : tipoProductoService.getAllTipoProducto()) {
                WritableSheet sheet = workbook.createSheet(tipoProducto.getNombre().replace("/", "-"), contTp++);

                buildHeaders(headers, sheet);

                List<Stock> stocks = stockService.getStocksByTipoProducto(tipoProducto.getId());

                int contS = 1;
                for (Stock stock : stocks) {
                    Collection<? extends Precio> precios = stock.getProducto().getPrecios();
                    for (Precio precio : precios) {
                        sheet.addCell(new Label(0, contS, stock.getSucursal().getNombre()));
                        sheet.addCell(new Label(1, contS, stock.getProducto().getModelo().getMarca().getNombre()));
                        sheet.addCell(new Label(2, contS, buildNombreProducto(stock.getProducto())));
                        sheet.addCell(new Label(3, contS, buildValorClasificatorio(stock.getValorClasificatorio())));
                        sheet.addCell(new Label(4, contS, buildValorPresentacion(stock.getPresentacion())));
                        sheet.addCell(new Number(5, contS, stock.getStock()));
                        if (precio != null) {
                            sheet.addCell(new Label(6, contS, precio.getId().getPago().getNombre()));
                            sheet.addCell(new Label(7, contS, precio.getId().getMoneda().getCodigo()));
                            if (precio.getValor() != null) {
                                sheet.addCell(new Number(8, contS, precio.getValor()));
                                sheet.addCell(new Label(9, contS, precio.isEnOferta() ? "Sí" : "No"));
                                if (precio.isEnOferta()) {
                                    sheet.addCell(new Number(10, contS, precio.getValorOferta()));
                                }
                            }
                        }
                        contS++;
                    }
                }
            }
            workbook.write();
            workbook.close();
        } catch (WriteException ex) {
            LOGGER.error("export() - error writing workbook", ex);
        } catch (IOException ex) {
            LOGGER.error("export() - error writing workbook", ex);
        } finally {
            IOUtils.closeQuietly(out);
        }
        return out;
    }

    private void buildHeaders(String[] headers, WritableSheet sheet) throws WriteException {
        for (int i = 0; i < headers.length; i++) {
            CellView cell = new CellView();
            cell.setAutosize(true);
            sheet.setColumnView(i, cell);
            sheet.addCell(new Label(i, 0, headers[i]));
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

}
