package com.palermotenis.controller.struts.actions.admin.crud;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.controller.struts.actions.JsonActionSupport;
import com.palermotenis.model.beans.Categoria;
import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.service.atributos.tipos.TipoAtributoService;
import com.palermotenis.model.service.categorias.CategoriaService;
import com.palermotenis.model.service.marcas.MarcaService;
import com.palermotenis.model.service.modelos.ModeloService;
import com.palermotenis.model.service.precios.impl.PrecioService;
import com.palermotenis.model.service.productos.ProductoService;
import com.palermotenis.model.service.productos.tipos.TipoProductoService;
import com.thoughtworks.xstream.converters.ConversionException;

public class ProductoAction extends JsonActionSupport {
    private static final long serialVersionUID = -9146563702043871823L;

    private static final String PREPARE = "prepare";
    private static final String SHOW = "show";
    private static final String CREATE_SUCCESS = "createSuccess";
    private static final String CREATE_ERROR = "createError";
    private static final String EDIT_SUCCESS = "editSuccess";
    private static final String EDIT_ERROR = "editError";

    private String nombre;
    private String descripcion;
    private String camposJson;
    private Boolean activo;

    private Integer tipoProductoId;
    private Integer marcaId;
    private Integer modeloId;
    private Integer productoId;

    private Producto producto;

    private Marca marca;
    private Modelo modelo;
    private TipoProducto tipoProducto;
    private Collection<Categoria> categorias;
    private Collection<Marca> marcas;
    private Collection<TipoProducto> tiposProductos;

    private final Collection<String> errores = new ArrayList<String>();
    private Collection<Integer> categoriasIds;

    private Map<Integer, String> atributosSimples = new HashMap<Integer, String>();
    private Map<Integer, Integer> atributosTipados = new HashMap<Integer, Integer>();
    private Map<Integer, Collection<String>> atributosMultiples = new HashMap<Integer, Collection<String>>();

    @Autowired
    private TipoProductoService tipoProductoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private MarcaService marcaService;

    @Autowired
    private PrecioService precioService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ModeloService modeloService;

    @Autowired
    private TipoAtributoService tipoAtributoService;

    public String prepare() {
        return PREPARE;
    }

    public String show() {
        if (getModeloId() != -1) {
            setProducto(modeloService.getModeloById(modeloId).getProducto());
        }
        return SHOW;
    }

    public String create() {
        try {
            productoService.createNewProducto(getModeloId(), productoId, descripcion, getCategoriasIds(),
                getAtributosSimples(), getAtributosTipados(), getAtributosMultiples());
            return CREATE_SUCCESS;
        } catch (Exception ex) {
            Logger.getLogger(ProductoAction.class.getName()).log(Level.SEVERE, null, ex);
            getErrores().add(ex.getLocalizedMessage());
            return CREATE_ERROR;
        }
    }

    public String edit() {
        try {
            productoService.updateProducto(getModeloId(), getNombre(), descripcion, activo, getCategoriasIds(),
                getAtributosSimples(), getAtributosTipados(), getAtributosMultiples());
        } catch (Exception ex) {
            Logger.getLogger(ModeloAction.class.getName()).log(Level.SEVERE, null, ex);
            return EDIT_ERROR;
        }
        return EDIT_SUCCESS;
    }

    public String validateFields() {
        JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(camposJson);
        List<DynaBean> beans = (List<DynaBean>) JSONSerializer.toJava(jsonArray);
        JSONObject output = new JSONObject();

        JSONObject activoValidated = new JSONObject();
        if (activo) {
            activoValidated.element("valid", precioService.hasPrecio(productoId));
        } else {
            activoValidated.element("valid", true); // siempre devuelve TRUE si el producto NO está activo
        }

        JSONArray camposJsonValidated = new JSONArray();
        for (DynaBean bean : beans) {
            Integer tipoAtributoId = (Integer) bean.get("tipoAtributoId");
            String valor = (String) bean.get("valor");

            TipoAtributo tipoAtributo = tipoAtributoService.getTipoAtributoById(tipoAtributoId);

            JSONObject jsonObject = new JSONObject();
            jsonObject.element("id", tipoAtributoId);
            try {
                ConvertUtils.convert(valor, tipoAtributo.getClase());
                jsonObject.element("valid", true);
            } catch (ConversionException ex) {
                jsonObject.element("valid", false);
            } catch (Exception e) {
                jsonObject.element("valid", false);
            }
            camposJsonValidated.add(jsonObject);
        }

        output.element("camposJson", camposJsonValidated);
        output.element("activo", activoValidated);

        writeResponse(output);
        return JSON;
    }

    @Override
    public void validate() {
        for (Integer tipoAtributoId : getAtributosSimples().keySet()) {
            String valor = getAtributosSimples().get(tipoAtributoId);
            if (StringUtils.isNotBlank(valor)) {
                try {
                    ConvertUtils.convert(valor, tipoAtributoService.getTipoAtributoById(tipoAtributoId).getClase());
                } catch (ConversionException ex) {
                    addFieldError("atributosSimples[" + tipoAtributoId + "]", "El valor no es válido");
                }
            }
        }
    }

    public void setTipoProductoId(Integer tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getModeloId() {
        return modeloId;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public void setModeloId(Integer modeloId) {
        this.modeloId = modeloId;
    }

    public Collection<Integer> getCategoriasIds() {
        return categoriasIds;
    }

    public void setCategoriasIds(Collection<Integer> categoriasIds) {
        this.categoriasIds = categoriasIds;
    }

    public Map<Integer, String> getAtributosSimples() {
        return atributosSimples;
    }

    public void setAtributosSimples(Map<Integer, String> atributosSimples) {
        this.atributosSimples = atributosSimples;
    }

    public Map<Integer, Integer> getAtributosTipados() {
        return atributosTipados;
    }

    public void setAtributosTipados(Map<Integer, Integer> atributosTipados) {
        this.atributosTipados = atributosTipados;
    }

    public Map<Integer, Collection<String>> getAtributosMultiples() {
        return atributosMultiples;
    }

    public void setAtributosMultiples(Map<Integer, Collection<String>> atributosMultiples) {
        this.atributosMultiples = atributosMultiples;
    }

    public Collection<String> getErrores() {
        return errores;
    }

    public Collection<Categoria> getCategorias() {
        if (categorias == null) {
            categorias = categoriaService.getAllCategorias();
        }
        return categorias;
    }

    public Collection<Marca> getMarcas() {
        if (marcas == null) {
            marcas = marcaService.getAllMarcas();
        }
        return marcas;
    }

    public Collection<TipoProducto> getTiposProducto() {
        if (tiposProductos == null) {
            tiposProductos = tipoProductoService.getAllTipoProducto();
        }
        return tiposProductos;
    }

    public Integer getMarcaId() {
        return marcaId;
    }

    public void setMarcaId(Integer marcaId) {
        this.marcaId = marcaId;
    }

    public Integer getTipoProductoId() {
        return tipoProductoId;
    }

    public TipoProducto getTipoProducto() {
        if (tipoProducto == null) {
            tipoProducto = tipoProductoService.getTipoProductoById(tipoProductoId);
        }
        return tipoProducto;
    }

    public Marca getMarca() {
        if (marca == null) {
            marca = marcaService.getMarcaById(marcaId);
        }
        return marca;
    }

    public Modelo getModelo() {
        if (modelo == null) {
            modelo = modeloService.getModeloById(modeloId);
        }
        return modelo;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setCamposJson(String camposJson) {
        this.camposJson = camposJson;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

}
