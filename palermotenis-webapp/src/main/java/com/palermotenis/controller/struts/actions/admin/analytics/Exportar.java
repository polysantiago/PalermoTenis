package com.palermotenis.controller.struts.actions.admin.analytics;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.service.exporters.ExcelExporterService;

public class Exportar extends ActionSupport {

    private static final String[] HEADERS = new String[]
        { "Sucursal", "Marca", "Producto", "Valor Clasificatorio", "Presentación", "Stock", "Pago", "Moneda", "Precio",
                "En Oferta", "Oferta" };

    private static final long serialVersionUID = 8843428628791493785L;

    private static final String EXCEL = "excel";
    private InputStream inputStream;

    @Autowired
    private ExcelExporterService excelExporterService;

    public String toExcel() {
        inputStream = new ByteArrayInputStream(excelExporterService.export(HEADERS).toByteArray());
        return EXCEL;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public String getDate() {
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }
}
