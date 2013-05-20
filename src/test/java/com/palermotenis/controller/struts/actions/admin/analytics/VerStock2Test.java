package com.palermotenis.controller.struts.actions.admin.analytics;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.infrastructre.testsupport.base.BaseSpringStrutsTestCase;
import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.Sucursal;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.beans.valores.ValorClasificatorio;
import com.palermotenis.model.dao.marca.MarcaDao;
import com.palermotenis.model.dao.modelos.ModeloDao;
import com.palermotenis.model.dao.productos.tipos.TipoProductoDao;
import com.palermotenis.model.dao.stock.StockDao;
import com.palermotenis.support.TestModeloService;

public class VerStock2Test extends BaseSpringStrutsTestCase<VerStock2> {

    @Autowired
    private TipoProductoDao tipoProductoDao;

    @Autowired
    private ModeloDao modeloDao;

    @Autowired
    private MarcaDao marcaDao;

    @Autowired
    private StockDao stockDao;

    @Autowired
    private TestModeloService testModeloService;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testVerStock2() throws UnsupportedEncodingException, ServletException {
        Integer rows = 20;
        Integer page = 1;
        String sidx = "s.producto.modelo.marca.nombre";
        String sord = "asc";

        request.setParameter("rows", rows.toString());
        request.setParameter("page", page.toString());
        request.setParameter("sidx", sidx);
        request.setParameter("sord", sord);

        String expected = buildExpectedResponse(null, null, null, sidx, sord, rows, page);
        String result = executeAction("/admin/estadisticas/VerStock2");

        assertEquals(expected, result);
    }

    private String buildExpectedResponse(Integer tipoProductoId, Integer modeloId, Integer marcaId, String sidx,
            String sord, int rows, int page) {
        TipoProducto tp = tipoProductoId != null ? tipoProductoDao.find(tipoProductoId) : null;
        JSONObject rootObj = new JSONObject();
        JSONArray rowsArr = new JSONArray();

        Map<String, Object> args = new HashMap<String, Object>();
        if (tp != null) {
            args.put("tipoProducto", tp);
        }

        if (sidx.contains(",")) {
            String[] as = sidx.split(",");
            as[0] += " " + sord;
            sidx = as[0] + "," + as[1];
        }

        String namedQuery = "Stock.findBy";
        String countQuery = null;

        if (modeloId != null) {
            namedQuery += "Modelo";
            countQuery = "Modelo-Count";
            args.put("modelo", modeloDao.find(modeloId));
            args.remove("tipoProducto");
        } else if (marcaId != null) {
            namedQuery += "TipoProducto,Marca";
            countQuery = "TipoProducto,Marca-Count";
            args.put("marca", marcaDao.find(marcaId));
        } else if (tipoProductoId != null) {
            namedQuery += "TipoProducto";
            countQuery = "TipoProducto-Count";
        } else {
            namedQuery = "Stock.findAll";
        }

        Session session = (Session) entityManager.getDelegate();
        Query query = session
            .createQuery(session.getNamedQuery(namedQuery).getQueryString() + " ORDER BY " + sidx + " " + sord)
            .setMaxResults(rows)
            .setFirstResult(rows * (page - 1));
        for (Map.Entry<String, ?> entry : args.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        List<Stock> stocks = query.list();

        int records = countQuery != null ? stockDao.getIntResultBy(countQuery, args) : stockDao.count();

        for (Stock stock : stocks) {
            Producto producto = stock.getProducto();
            TipoProducto tipoProducto = producto.getTipoProducto();
            Modelo modelo = producto.getModelo();
            Marca marca = modelo.getMarca();
            Presentacion presentacion = stock.getPresentacion();
            Sucursal sucursal = stock.getSucursal();
            ValorClasificatorio valorClasificatorio = stock.getValorClasificatorio();

            JSONObject row = new JSONObject();
            row.element("id", stock.getId());
            JSONArray cellArr = new JSONArray();

            cellArr.add(tipoProducto.getNombre());
            cellArr.add(marca.getNombre());

            StringBuilder nombreProducto = new StringBuilder();
            for (Modelo padre : modelo.getPadres()) {
                nombreProducto.append(padre.getNombre()).append(" > ");
            }
            nombreProducto.append(modelo.getNombre());
            cellArr.add(nombreProducto.toString());

            cellArr.add(tipoProducto.isClasificable() && valorClasificatorio != null ? valorClasificatorio
                .getTipoAtributo()
                .getNombre() + ": " + valorClasificatorio.getNombre() : "");

            cellArr.add(tipoProducto.isPresentable() && presentacion != null ? presentacion
                .getTipoPresentacion()
                .getNombre() + ": " + presentacion.getNombre() : "");

            cellArr.add(sucursal.getNombre());
            cellArr.add(stock.getStock());

            row.element("cell", cellArr);
            rowsArr.add(row);
        }

        rootObj.element("rows", rowsArr);
        rootObj.element("records", records);
        rootObj.element("total", Math.ceil((double) records / rows));
        rootObj.element("page", page);

        return rootObj.toString();
    }

}
