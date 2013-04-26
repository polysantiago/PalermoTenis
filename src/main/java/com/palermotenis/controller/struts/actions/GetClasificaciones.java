package com.palermotenis.controller.struts.actions;

import java.io.InputStream;
import java.util.Collection;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.atributos.tipos.clasif.TipoAtributoClasif;
import com.palermotenis.model.dao.Dao;
import com.palermotenis.util.StringUtility;

/**
 *
 * @author Poly
 */
public class GetClasificaciones extends ActionSupport {

	private static final long serialVersionUID = -879847503471376338L;

	private InputStream inputStream;
    
    @Autowired
    private Dao<TipoAtributoClasif, Character> tipoAtributoClasifDao;

    @Override
    public String execute() {

        Collection<TipoAtributoClasif> clasificaciones = tipoAtributoClasifDao.findAll();

        JSONArray clasifArr = (JSONArray) JSONSerializer.toJSON(clasificaciones);

        inputStream = StringUtility.getInputString(clasifArr.toString());

        return SUCCESS;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
