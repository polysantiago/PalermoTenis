/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.util.StringUtility;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author Poly
 */
public class ListarTipoProductoMarcas extends ActionSupport {

    private InputStream inputStream;
    private EntityManager entityManager;

    @Override
    public String execute() {


        List<Object[]> l = entityManager
                .createQuery("select t,m, "
                + "(select count(*) from Producto p where p.tipoProducto = t and p.modelo.marca = m) "
                + "from TipoProducto t, Marca m order by t.nombre, m.nombre")
                .getResultList();

        Map<TipoProducto, ArrayList<LinkedHashMap<Marca, Long>>> map = new LinkedHashMap<TipoProducto, ArrayList<LinkedHashMap<Marca, Long>>>();

        for (Object[] o : l) {
            TipoProducto t = (TipoProducto) o[0];
            Marca m = (Marca) o[1];
            Long pC = (Long) o[2];

            ArrayList<LinkedHashMap<Marca, Long>> a;
            if (!map.containsKey(t)) {
                a = new ArrayList<LinkedHashMap<Marca, Long>>();
            } else {
                a = map.get(t);
            }
            LinkedHashMap<Marca, Long> hm = new LinkedHashMap<Marca, Long>();
            hm.put(m, pC);
            a.add(hm);
            if (!map.containsKey(t)) {
                map.put(t, a);
            }
        }

        JSONArray jsonArray = new JSONArray();
        for (TipoProducto t : map.keySet()) {
            JSONObject jsonObject = new JSONObject();

            jsonObject.element("id", t.getId());
            jsonObject.element("text", t.getNombre());

            JSONArray jsonArrMarcas = new JSONArray();
            for (Map<Marca, Long> m2 : map.get(t)) {
                for (Marca m : m2.keySet()) {
                    JSONObject jsonObjMarca = new JSONObject();
                    jsonObjMarca.element("id", m.getId());
                    jsonObjMarca.element("text", m.getNombre());
                    jsonObjMarca.element("leaf", "true");
                    jsonObjMarca.element("productosCount", m2.get(m));
                    jsonArrMarcas.add(jsonObjMarca);
                }
            }
            jsonObject.element("children", jsonArrMarcas);
            jsonArray.add(jsonObject);
        }

        inputStream = StringUtility.getInputString(jsonArray.toString());
        return SUCCESS;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
