/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.controller.struts.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import com.palermotenis.util.StringUtility;
import java.io.InputStream;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.DynaBean;

/**
 *
 * @author Poly
 */
public class ValidarCampos extends ActionSupport {
    private GenericDao<TipoAtributo, Integer> tipoAtributoService;
    private String camposJson;

    private InputStream inputStream;

    @Override
    public String execute(){

        JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(camposJson);
        List<DynaBean> beans = (List<DynaBean>) JSONSerializer.toJava(jsonArray);
        JSONArray output = new JSONArray();
        for(DynaBean bean : beans){
            Integer tipoAtributoId = (Integer)bean.get("tipoAtributoId");
            String valor = (String)bean.get("valor");

            TipoAtributo tipoAtributo = tipoAtributoService.find(tipoAtributoId);

            JSONObject jsonObject = new JSONObject();
            jsonObject.element("id", tipoAtributoId);
            try {
                ConvertUtils.convert(valor, tipoAtributo.getClase());
                jsonObject.element("valid",true);
            } catch (ConversionException ex){
                jsonObject.element("valid", false);
            }
            output.add(jsonObject);
        }
        inputStream = StringUtility.getInputString(output.toString());
        return SUCCESS;
    }

    /**
     * @param tipoAtributoService the tipoAtributoService to set
     */
    public void setTipoAtributoService(GenericDao<TipoAtributo, Integer> tipoAtributoService) {
        this.tipoAtributoService = tipoAtributoService;
    }

    /**
     * @param camposJson the camposJson to set
     */
    public void setCamposJson(String camposJson) {
        this.camposJson = camposJson;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }
}
