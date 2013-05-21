package com.palermotenis.model.service.exporters;

import java.io.ByteArrayOutputStream;

public interface ExcelExporterService extends ExporterService {

    @Override
    ByteArrayOutputStream export();

    ByteArrayOutputStream export(String[] headers);

}
