package com.palermotenis.controller.struts.actions;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableMap;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.geograficos.Ciudad;
import com.palermotenis.model.beans.geograficos.Provincia;
import com.palermotenis.model.dao.Dao;
import com.palermotenis.util.StringUtility;

/**
 *
 * @author Poly
 */
public class GetCiudades extends ActionSupport {

	private static final long serialVersionUID = -4744668583308244516L;
	
	private InputStream inputStream;
    private String q;
    
    @Autowired
    private Dao<Ciudad, Integer> ciudadDao;

    @Override
    public String execute() {
        StringBuilder sb = new StringBuilder();
        for(Ciudad c : ciudadDao.queryBy("Nombre",
                new ImmutableMap.Builder<String, Object>().put("nombre", "%" + q + "%").build())){
            Provincia p = c.getProvincia();
            sb.append(c.getId()).append("|");
            sb.append(c.getNombre()).append('|');
            sb.append(c.getCodigoPostal()).append('|');
            sb.append(p.getNombre()).append('|');
            sb.append(p.getPais().getNombre()).append("\n");
        }
        inputStream = StringUtility.getInputString(sb.toString());
        return SUCCESS;
    }


    public void setQ(String q) {
        this.q = q;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
