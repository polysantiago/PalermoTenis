package com.palermotenis.controller.struts.actions.carrito;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionProxy;
import com.palermotenis.controller.carrito.Carrito;
import com.palermotenis.controller.carrito.CarritoImpl;
import com.palermotenis.infrastructre.testsupport.base.BaseSpringStrutsTestCase;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.service.carrito.CarritoService;
import com.palermotenis.model.service.stock.StockService;
import com.palermotenis.support.TestPagoService;
import com.palermotenis.support.TestPrecioService;
import com.palermotenis.support.TestProductoService;

public class CarritoActionTest extends BaseSpringStrutsTestCase<CarritoAction> {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private TestPagoService testPagoService;

    @Autowired
    private TestProductoService testProductoService;

    @Autowired
    private TestPrecioService testPrecioService;

    @Autowired
    private StockService stockService;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testEditPago() throws Exception {
        Producto producto = testProductoService.create();

        testPrecioService.create(producto);
        stockService.updateQuantity(producto.getStocks().get(0), 1);

        entityManager.flush();
        entityManager.refresh(producto);

        Carrito carrito = new CarritoImpl();
        carritoService.add(carrito, producto.getId());

        String expected = buildExpectedResult(carrito);

        ActionProxy proxy = getActionProxy("/carrito/CarritoAction_editPago");

        CarritoAction action = (CarritoAction) proxy.getAction();
        action.setCuotas(1);
        action.setPagoId(testPagoService.getAny().getId());
        action.setCarrito(carrito);

        proxy.execute();

        String result = response.getContentAsString();

        assertEquals(expected, result);
    }

    private String buildExpectedResult(Carrito carrito) {
        JSONObject main = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Stock stock : carrito.getContenido().keySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.element("id", stock.getId());
            jsonObject.element("item", JSONSerializer.toJSON(carrito.getContenido().get(stock)));
            jsonArray.add(jsonObject);
        }
        main.element("items", jsonArray);
        main.element("total", carrito.getTotal());
        main.element("pago", carrito.getPago().getNombre());

        return main.toString();
    }

}
