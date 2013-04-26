package com.palermotenis.controller.struts.actions;

import java.io.InputStream;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.DynaBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import com.palermotenis.model.dao.Dao;
import com.palermotenis.util.StringUtility;

/**
 * 
 * @author Poly
 */
public class ValidarCampos extends ActionSupport {

    private static final long serialVersionUID = -8704778453708125123L;

    private String camposJson;

    private InputStream inputStream;

    @Autowired
    private Dao<TipoAtributo, Integer> tipoAtributoDao;

    @Override
    public String execute() {

        JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(camposJson);
        List<DynaBean> beans = (List<DynaBean>) JSONSerializer.toJava(jsonArray);
        JSONArray output = new JSONArray();
        for (DynaBean bean : beans) {
            Integer tipoAtributoId = (Integer) bean.get("tipoAtributoId");
            String valor = (String) bean.get("valor");

            TipoAtributo tipoAtributo = tipoAtributoDao.find(tipoAtributoId);

            JSONObject jsonObject = new JSONObject();
            jsonObject.element("id", tipoAtributoId);
            try {
                ConvertUtils.convert(valor, tipoAtributo.getClase());
                jsonObject.element("valid", true);
            } catch (ConversionException ex) {
                jsonObject.element("valid", false);
            }
            output.add(jsonObject);
        }
        inputStream = StringUtility.getInputString(output.toString());
        return SUCCESS;
    }

    /**
     * @param camposJson
     *            the camposJson to set
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
