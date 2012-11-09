/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.admin.crud;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Deporte;
import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.Sucursal;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.beans.atributos.Atributo;
import com.palermotenis.model.beans.atributos.AtributoMultipleValores;
import com.palermotenis.model.beans.atributos.AtributoTipado;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoClasificatorio;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.productos.tipos.ClasificableState;
import com.palermotenis.model.beans.productos.tipos.DefaultState;
import com.palermotenis.model.beans.productos.tipos.PresentableAndClasificableState;
import com.palermotenis.model.beans.productos.tipos.PresentableState;
import com.palermotenis.model.beans.productos.tipos.State;
import com.palermotenis.model.beans.valores.Valor;
import com.palermotenis.model.beans.valores.ValorClasificatorio;
import com.palermotenis.model.beans.valores.ValorPosible;
import com.palermotenis.util.Convertor;
import com.palermotenis.util.StringUtility;
import com.thoughtworks.xstream.converters.ConversionException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.DynaBean;
import org.hibernate.HibernateException;

/**
 *
 * @author Poly
 */
public class ProductoAction extends ActionSupport {

    private static final String PREPARE = "prepare";
    private static final String SHOW = "show";
    private static final String CREATE_SUCCESS = "createSuccess";
    private static final String CREATE_ERROR = "createError";
    private static final String EDIT_SUCCESS = "editSuccess";
    private static final String EDIT_ERROR = "editError";
    private static final String JSON = "json";
    private GenericDao<TipoProducto, Integer> tipoProductoService;
    private GenericDao<Producto, Integer> productoService;
    private GenericDao<Modelo, Integer> modeloService;
    private GenericDao<Deporte, Integer> deporteService;
    private GenericDao<Atributo, Integer> atributoService;
    private GenericDao<AtributoTipado, Integer> atributoTipadoService;
    private GenericDao<AtributoMultipleValores, Integer> atributoMultipleValoresService;
    private GenericDao<ValorPosible, Integer> valorPosibleService;
    private GenericDao<ValorClasificatorio, Integer> valorClasificatorioService;
    private GenericDao<TipoAtributo, Integer> tipoAtributoService;
    private GenericDao<Stock, Integer> stockService;
    private GenericDao<Marca, Integer> marcaService;
    private GenericDao<Presentacion, Integer> presentacionService;
    private GenericDao<Sucursal, Integer> sucursalService;
    private String nombre;
    private String descripcion;
    private String camposJson;
    private Boolean activo;
    private Integer tipoProductoId;
    private Integer marcaId;
    private Integer modeloId;
    private Integer productoId;
    private TipoProducto tipoProducto;
    private Marca marca;
    private Modelo modelo;
    private Producto producto;
    private Collection<Deporte> deportes;
    private Collection<Marca> marcas;
    private Collection<TipoProducto> tiposProducto;
    private Convertor convertor;
    private Collection<String> errores = new ArrayList<String>();
    private Collection<Integer> deportesIds;
    private Map<Integer, String> atributosSimples = new HashMap<Integer, String>();
    private Map<Integer, Integer> atributosTipados = new HashMap<Integer, Integer>();
    private Map<Integer, Collection<String>> atributosMultiples = new HashMap<Integer, Collection<String>>();
    private InputStream inputStream;

    public String prepare() {
        deportes = deporteService.findAll();
        marcas = marcaService.findAll();
        tiposProducto = tipoProductoService.findAll();

        marca = marcaService.find(marcaId);
        tipoProducto = tipoProductoService.find(tipoProductoId);
        modelo = modeloService.find(modeloId);

        return PREPARE;
    }

    public String show() {
        if (getModeloId() != -1) {
            setProducto(((Modelo) modeloService.find(getModeloId())).getProducto());
        }
        deportes = deporteService.findAll();
        marcas = marcaService.findAll();
        tiposProducto = tipoProductoService.findAll();

        marca = marcaService.find(marcaId);
        tipoProducto = tipoProductoService.find(tipoProductoId);

        return SHOW;
    }

    public String create() {
        Producto p = null;
        try {
            p = new Producto();

            p.setModelo(modeloService.find(getModeloId()));
            p.setTipoProducto(tipoProductoService.find(tipoProductoId));
            p.setDescripcion(descripcion);

            Collection<Deporte> dpts = new ArrayList<Deporte>();
            for (Integer i : getDeportesIds()) {
                dpts.add(deporteService.find(i));
            }
            p.getModelo().setDeportes(dpts);

            productoService.create(p);

            //Atributos Simples
            for (Integer i : getAtributosSimples().keySet()) {

                String valor = getAtributosSimples().get(i);
                if (valor != null && !valor.isEmpty()) {
                    TipoAtributo t = tipoAtributoService.find(i);

                    Atributo a = new Atributo(t, p);
                    a.setValor(new Valor(t));
                    atributoService.create(a);
                    Object o = ConvertUtils.convert(valor, t.getClase());
                    a.getValor().setUnidad(o);
                }
            }

            //Atributos Tipados
            for (Integer i : getAtributosTipados().keySet()) {

                Integer valorPosibleId = getAtributosTipados().get(i);
                if (valorPosibleId != null) {

                    AtributoTipado a = new AtributoTipado();
                    a.setProducto(p);
                    a.setTipoAtributo(tipoAtributoService.find(i));
                    a.setValorPosible(valorPosibleService.find(valorPosibleId));

                    atributoTipadoService.create(a);
                }
            }

            //Atributos Múltiples Valores
            for (Integer i : getAtributosMultiples().keySet()) {
                for (String s : getAtributosMultiples().get(i)) {

                    AtributoMultipleValores m = new AtributoMultipleValores();
                    m.setProducto(p);
                    m.setTipoAtributo(tipoAtributoService.find(i));
                    m.setValorPosible(valorPosibleService.find(Integer.parseInt(s)));

                    atributoTipadoService.create(m);

                }
            }

            //Atributos Clasificatorios
            TipoProducto tp = p.getTipoProducto();
            List<ValorClasificatorio> vc = null;
            List<Presentacion> prs = null;

            Collection<TipoAtributoClasificatorio> tac = tp.getTiposAtributoClasificatorios();
            if (tp.isClasificable() && tac != null && !tac.isEmpty()) {
                vc = valorClasificatorioService.queryBy("TipoAtributoList", "tipoAtributoList", tac);
            }

            Collection<TipoPresentacion> tpr = tp.getTiposPresentacion();
            if (tp.isPresentable() && tpr != null && !tpr.isEmpty()) {
                prs = presentacionService.queryBy("TipoList", "tipoList", tpr);
            }

            List<Sucursal> ss = sucursalService.findAll();

            State state = new DefaultState(p, ss);
            if (isClasificable(tp, tac) && isPresentable(tp, tpr)) {
                state = new PresentableAndClasificableState(p, ss, vc, prs);
            } else if (isClasificable(tp, tac)) {
                state = new ClasificableState(p, ss, vc);
            } else if (isPresentable(tp, tpr)) {
                state = new PresentableState(p, ss, prs);
            }
            state.setStockService(stockService);
            state.createStock();

            return CREATE_SUCCESS;
        } catch (HibernateException ex) {
            Logger.getLogger(ProductoAction.class.getName()).log(Level.SEVERE, null, ex);
            if (p != null) {
                try {
                    productoService.destroy(p);
                } catch (HibernateException ex1) {
                    Logger.getLogger(ProductoAction.class.getName()).log(Level.SEVERE, null, ex1);
                    getErrores().add(ex.getLocalizedMessage());
                }
            }
            getErrores().add(ex.getLocalizedMessage());
            return CREATE_ERROR;
        }
    }

    public String edit() {
        Modelo m = modeloService.find(getModeloId());
        Producto p = m.getProducto();

        if (!m.getNombre().equals(nombre)) {
            m.setNombre(getNombre());
        }

        p.setDescripcion(descripcion);
        p.setActivo(activo);
        Collection<Deporte> newDeportes = new ArrayList<Deporte>();
        for (Integer i : getDeportesIds()) {
            newDeportes.add(deporteService.find(i));
        }
        m.setDeportes(newDeportes);

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("producto", p);
        try {
            modeloService.edit(m);

            //Atributos Simples
            for (Integer i : getAtributosSimples().keySet()) {
                String valor = getAtributosSimples().get(i);
                TipoAtributo t = tipoAtributoService.find(i);
                try {
                    args.put("tipoAtributo", t);
                    Atributo a = atributoService.findBy("Producto,Tipo", args);
                    if (valor != null && !valor.isEmpty()) {
                        Object o = ConvertUtils.convert(valor, t.getClase());
                        a.getValor().setUnidad(o);
                    } else {
                        atributoService.destroy(a);
                    }
                } catch (NoResultException e) {
                    if (valor != null && !valor.isEmpty()) {
                        Atributo a = new Atributo();
                        Valor newValor = new Valor();
                        Object o = ConvertUtils.convert(valor, t.getClase());
                        newValor.setTipoAtributo(t);
                        newValor.setUnidad(o);
                        a.setValor(newValor);
                        a.setProducto(p);
                        a.setTipoAtributo(t);
                        atributoService.create(a);
                    }
                }
            }

            //Atributos Tipados
            for (Integer i : getAtributosTipados().keySet()) {

                Integer valorPosibleId = getAtributosTipados().get(i);
                TipoAtributo t = tipoAtributoService.find(i);

                try {
                    args.put("tipoAtributo", t);
                    AtributoTipado a = (AtributoTipado) atributoService.findBy("Producto,Tipo", args);
                    if (getAtributosTipados().get(i) != null) {
                        ValorPosible v = valorPosibleService.find(valorPosibleId);
                        a.setValorPosible(v);
                        atributoTipadoService.edit(a);
                    } else {
                        atributoTipadoService.destroy(a);
                    }
                } catch (NoResultException e) {
                    if (valorPosibleId != null) {
                        AtributoTipado a = new AtributoTipado();
                        a.setProducto(p);
                        a.setTipoAtributo(t);
                        ValorPosible v = valorPosibleService.find(valorPosibleId);
                        a.setValorPosible(v);
                        atributoTipadoService.create(a);
                    }
                }
            }

            args.remove("tipoAtributo");
            //Atributos Multiples
            Collection<AtributoMultipleValores> amv = atributoMultipleValoresService.queryBy("Producto", args);
            if (amv != null && !amv.isEmpty()) {
                for (AtributoMultipleValores a : amv) {
                    atributoService.destroy(a);
                }
            }

            for (Integer i : getAtributosMultiples().keySet()) {
                for (String s : getAtributosMultiples().get(i)) {
                    if (s != null && !s.isEmpty()) {
                        AtributoMultipleValores am = new AtributoMultipleValores();
                        am.setProducto(p);
                        am.setTipoAtributo(tipoAtributoService.find(i));
                        am.setValorPosible(valorPosibleService.find(Integer.parseInt(s)));

                        atributoMultipleValoresService.create(am);
                    }
                }
            }

        } catch (HibernateException ex) {
            Logger.getLogger(ModeloAction.class.getName()).log(Level.SEVERE, null, ex);
            return EDIT_ERROR;
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
            activoValidated.element("valid", convertor.hasPrecio(productoService.find(productoId)));
        } else {
            activoValidated.element("valid", true); //siempre devuelve TRUE si el producto NO está activo
        }

        JSONArray camposJsonValidated = new JSONArray();
        for (DynaBean bean : beans) {
            Integer tipoAtributoId = (Integer) bean.get("tipoAtributoId");
            String valor = (String) bean.get("valor");

            TipoAtributo tipoAtributo = tipoAtributoService.find(tipoAtributoId);

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

        inputStream = StringUtility.getInputString(output.toString());
        return JSON;
    }

    @Override
    public void validate() {
        for (Integer i : getAtributosSimples().keySet()) {
            String valor = getAtributosSimples().get(i);

            if (valor != null && !valor.isEmpty()) {
                try {
                    ConvertUtils.convert(valor, tipoAtributoService.find(i).getClase());
                } catch (ConversionException ex) {
                    Logger.getLogger(ProductoAction.class.getName()).log(Level.SEVERE, null, ex);
                    addFieldError("atributosSimples[" + i + "]", "El valor no es válido");
                }
            }
        }
    }

    private boolean isClasificable(TipoProducto tipoProducto, Collection<TipoAtributoClasificatorio> collection) {
        return tipoProducto.isClasificable() && collection != null && !collection.isEmpty();
    }

    private boolean isPresentable(TipoProducto tipoProducto, Collection<TipoPresentacion> collection) {
        return tipoProducto.isPresentable() && collection != null && !collection.isEmpty();
    }

    /**
     * @param modeloService the modelosService to set
     */
    public void setModeloService(GenericDao<Modelo, Integer> modeloService) {
        this.modeloService = modeloService;
    }

    /**
     * @param productoService the productoService to set
     */
    public void setProductoService(GenericDao<Producto, Integer> productoService) {
        this.productoService = productoService;
    }

    /**
     * @param deporteService the deportesService to set
     */
    public void setDeporteService(GenericDao<Deporte, Integer> deporteService) {
        this.deporteService = deporteService;
    }

    /**
     * @param atributoService the atributoService to set
     */
    public void setAtributoService(GenericDao<Atributo, Integer> atributoService) {
        this.atributoService = atributoService;
    }

    /**
     * @param atributoTipadoService the atributoTipadoService to set
     */
    public void setAtributoTipadoService(GenericDao<AtributoTipado, Integer> atributoTipadoService) {
        this.atributoTipadoService = atributoTipadoService;
    }

    /**
     * @param atributoMultipleValoresService the atributoMultipleValoresService to set
     */
    public void setAtributoMultipleValoresService(GenericDao<AtributoMultipleValores, Integer> atributoMultipleValoresService) {
        this.atributoMultipleValoresService = atributoMultipleValoresService;
    }

    /**
     * @param valorPosibleService the valorPosibleService to set
     */
    public void setValorPosibleService(GenericDao<ValorPosible, Integer> valorPosibleService) {
        this.valorPosibleService = valorPosibleService;
    }

    public void setValorClasificatorioService(GenericDao<ValorClasificatorio, Integer> valorClasificatorioService) {
        this.valorClasificatorioService = valorClasificatorioService;
    }

    /**
     * @param tipoAtributoService the tipoAtributoService to set
     */
    public void setTipoAtributoService(GenericDao<TipoAtributo, Integer> tipoAtributoService) {
        this.tipoAtributoService = tipoAtributoService;
    }

    /**
     * @param stockService the stockService to set
     */
    public void setStockService(GenericDao<Stock, Integer> stockService) {
        this.stockService = stockService;
    }

    /**
     * @param tipoProductoId the tipoProductoId to set
     */
    public void setTipoProductoId(Integer tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the modeloId
     */
    public Integer getModeloId() {
        return modeloId;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    /**
     * @param modeloId the modeloId to set
     */
    public void setModeloId(Integer modeloId) {
        this.modeloId = modeloId;
    }

    public void setPresentacionService(GenericDao<Presentacion, Integer> presentacionService) {
        this.presentacionService = presentacionService;
    }

    /**
     * @return the deportesIds
     */
    public Collection<Integer> getDeportesIds() {
        return deportesIds;
    }

    /**
     * @param deportesIds the deportesIds to set
     */
    public void setDeportesIds(Collection<Integer> deportesIds) {
        this.deportesIds = deportesIds;
    }

    /**
     * @return the atributosSimples
     */
    public Map<Integer, String> getAtributosSimples() {
        return atributosSimples;
    }

    /**
     * @param atributosSimples the atributosSimples to set
     */
    public void setAtributosSimples(Map<Integer, String> atributosSimples) {
        this.atributosSimples = atributosSimples;
    }

    /**
     * @return the atributosTipados
     */
    public Map<Integer, Integer> getAtributosTipados() {
        return atributosTipados;
    }

    /**
     * @param atributosTipados the atributosTipados to set
     */
    public void setAtributosTipados(Map<Integer, Integer> atributosTipados) {
        this.atributosTipados = atributosTipados;
    }

    /**
     * @return the atributosMultiples
     */
    public Map<Integer, Collection<String>> getAtributosMultiples() {
        return atributosMultiples;
    }

    /**
     * @param atributosMultiples the atributosMultiples to set
     */
    public void setAtributosMultiples(Map<Integer, Collection<String>> atributosMultiples) {
        this.atributosMultiples = atributosMultiples;
    }

    /**
     * @return the errores
     */
    public Collection<String> getErrores() {
        return errores;
    }

    /**
     * @param marcaService the marcasService to set
     */
    public void setMarcaService(GenericDao<Marca, Integer> marcaService) {
        this.marcaService = marcaService;
    }

    /**
     * @param tipoProductoService the tipoProductoService to set
     */
    public void setTipoProductoService(GenericDao<TipoProducto, Integer> tipoProductoService) {
        this.tipoProductoService = tipoProductoService;
    }

    public void setSucursalService(GenericDao<Sucursal, Integer> sucursalService) {
        this.sucursalService = sucursalService;
    }

    /**
     * @return the deportes
     */
    public Collection<Deporte> getDeportes() {
        return deportes;
    }

    /**
     * @return the marcas
     */
    public Collection<Marca> getMarcas() {
        return marcas;
    }

    /**
     * @return the tiposProducto
     */
    public Collection<TipoProducto> getTiposProducto() {
        return tiposProducto;
    }

    /**
     * @return the marcaId
     */
    public Integer getMarcaId() {
        return marcaId;
    }

    /**
     * @param marcaId the marcaId to set
     */
    public void setMarcaId(Integer marcaId) {
        this.marcaId = marcaId;
    }

    /**
     * @return the tipoProductoId
     */
    public Integer getTipoProductoId() {
        return tipoProductoId;
    }

    /**
     * @return the tipoProducto
     */
    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    /**
     * @param tipoProducto the tipoProducto to set
     */
    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    /**
     * @return the marca
     */
    public Marca getMarca() {
        return marca;
    }

    /**
     * @param marca the marca to set
     */
    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    /**
     * @return the modelo
     */
    public Modelo getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    /**
     * @return the producto
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * @param camposJson the camposJson to set
     */
    public void setCamposJson(String camposJson) {
        this.camposJson = camposJson;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public void setConvertor(Convertor convertor) {
        this.convertor = convertor;
    }
}
