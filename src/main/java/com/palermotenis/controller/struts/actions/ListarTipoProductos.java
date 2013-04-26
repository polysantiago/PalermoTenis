package com.palermotenis.controller.struts.actions;

import java.util.Collection;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.service.marcas.MarcaService;
import com.palermotenis.model.service.productos.tipos.TipoProductoService;

public class ListarTipoProductos extends JsonActionSupport {

    private static final long serialVersionUID = 1772931751114222306L;

    @Autowired
    private TipoProductoService tipoProductoService;

    @Autowired
    private MarcaService marcaService;

    @Override
    public String execute() {
        Collection<TipoProducto> tipoProductos = getRoots();
        JSONArray jsonArray = new JSONArray();
        for (TipoProducto tipoProducto : tipoProductos) {
            JSONObject jsonObject = createTipoProducto(tipoProducto);
            jsonArray.add(jsonObject);
        }
        writeResponse(jsonArray);
        return SUCCESS;
    }

    private JSONObject createTipoProducto(TipoProducto tipoProducto) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.element("id", tipoProducto.getId());
        jsonObject.element("text", tipoProducto.getNombre());

        JSONArray jsonArrMarcas = createMarcas(tipoProducto);
        jsonObject.element("children", jsonArrMarcas);
        return jsonObject;
    }

    private List<TipoProducto> getRoots() {
        return tipoProductoService.getRootsTipoProducto();
    }

    private List<Marca> getActiveMarcas(TipoProducto tipoProducto) {
        return marcaService.getActiveMarcasByTipoProducto(tipoProducto);
    }

    private JSONArray createMarcas(TipoProducto tipoProducto) {
        Collection<Marca> marcas = getActiveMarcas(tipoProducto);
        JSONArray jsonArrMarcas = new JSONArray();
        for (Marca marca : marcas) {
            jsonArrMarcas.add(createMarca(marca));
        }
        return jsonArrMarcas;
    }

    private JSONObject createMarca(Marca marca) {
        JSONObject jsonObjMarca = new JSONObject();
        jsonObjMarca.element("id", marca.getId());
        jsonObjMarca.element("text", marca.getNombre());
        jsonObjMarca.element("leaf", "true");
        return jsonObjMarca;
    }

}
