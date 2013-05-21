package com.palermotenis.controller.struts.actions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.palermotenis.model.beans.geograficos.Ciudad;
import com.palermotenis.model.beans.geograficos.Pais;
import com.palermotenis.model.beans.geograficos.Provincia;
import com.palermotenis.model.service.geograficos.GeographicService;

public class GetCiudades extends InputStreamActionSupport {

    private static final long serialVersionUID = -4744668583308244516L;

    private String q;

    @Autowired
    private GeographicService geographicService;

    @Override
    public String execute() {
        List<String> ciudadesString = new ArrayList<String>();
        for (Ciudad ciudad : getCiudades()) {
            ciudadesString.add(buildCiudadesString(ciudad));
        }
        writeResponse(Joiner.on("\n").join(ciudadesString));
        return SUCCESS;
    }

    private String buildCiudadesString(Ciudad ciudad) {
        Provincia provincia = ciudad.getProvincia();
        Pais pais = provincia.getPais();

        List<String> details = new ImmutableList.Builder<String>()
            .add(ciudad.getId().toString())
            .add(ciudad.getNombre())
            .add(ciudad.getCodigoPostal())
            .add(provincia.getNombre())
            .add(pais.getNombre())
            .build();

        return Joiner.on('|').join(details);
    }

    private List<Ciudad> getCiudades() {
        return geographicService.getCiudadedByNombre("%" + q + "%");
    }

    public void setQ(String q) {
        this.q = q;
    }
}
