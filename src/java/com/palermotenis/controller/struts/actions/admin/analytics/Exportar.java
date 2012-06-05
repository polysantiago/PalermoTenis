/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.admin.analytics;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.export.ExcelExporter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Poly
 */
public class Exportar extends ActionSupport {

    private static String EXCEL = "excel";
    private InputStream inputStream;
    private ExcelExporter excelExporter;

    public String toExcel() {
        excelExporter.setHeaders(new String[]{"Sucursal", "Marca", "Producto", "Valor Clasificatorio", "Presentación", "Stock", "Pago", "Moneda", "Precio", "En Oferta", "Oferta"});
        ByteArrayOutputStream out = (ByteArrayOutputStream) excelExporter.export();
        inputStream = new ByteArrayInputStream(out.toByteArray());
        return EXCEL;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setExcelExporter(ExcelExporter excelExporter) {
        this.excelExporter = excelExporter;
    }

    public String getDate() {
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }
}
