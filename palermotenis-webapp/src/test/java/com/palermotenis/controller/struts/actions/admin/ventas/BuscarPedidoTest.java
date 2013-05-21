package com.palermotenis.controller.struts.actions.admin.ventas;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.infrastructure.testsupport.base.BaseSpringStrutsTestCase;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.clientes.Cliente;
import com.palermotenis.model.beans.pedidos.Pedido;
import com.palermotenis.model.service.carrito.PedidoService;
import com.palermotenis.support.TestPedidoService;

public class BuscarPedidoTest extends BaseSpringStrutsTestCase<BuscarPedido> {

    private static final JsonConfig CONFIG = new JsonConfig();

    static {
        CONFIG.registerJsonBeanProcessor(Pedido.class, new JsonBeanProcessor() {

            @Override
            public JSONObject processBean(Object bean, JsonConfig jsonConfig) {
                Pedido p = (Pedido) bean;
                JSONObject o = new JSONObject();
                JsonConfig clienteConfig = new JsonConfig();
                clienteConfig.setExcludes(new String[]
                    { "pedidos", "usuario", "ventas" });
                clienteConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

                o.element("id", p.getId());
                o.element("total", p.getTotal());
                o.element("fecha", p.getFecha());
                o.element("pago", JSONSerializer.toJSON(p.getPago()));
                o.element("cuotas", p.getCuotas());
                o.element("cliente", JSONSerializer.toJSON(p.getCliente(), clienteConfig));
                return o;
            }
        });
    }

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private TestPedidoService testPedidoService;

    @PersistenceContext
    private EntityManager entityManager;

    private Pedido pedido;

    @Before
    public void setUpTest() {
        pedido = testPedidoService.create();
    }

    @Test
    @Transactional
    public void testBuscarPedidoXEmail() throws UnsupportedEncodingException, ServletException, JSONException {
        entityManager.flush();
        String searchVal = pedido.getCliente().getEmail();
        List<Pedido> pedidos = pedidoService.getPedidosByEmail(searchVal);
        buscarPedido("E", searchVal, pedidos);
    }

    @Test
    @Transactional
    public void testBuscarPedidoXNombre() throws UnsupportedEncodingException, ServletException, JSONException {
        entityManager.flush();
        String searchVal = pedido.getCliente().getNombre();
        List<Pedido> pedidos = pedidoService.getPedidosByNombreCliente(searchVal);
        buscarPedido("N", searchVal, pedidos);
    }

    private void buscarPedido(String filter, String searchVal, List<Pedido> pedidos)
            throws UnsupportedEncodingException, ServletException, JSONException {
        request.setParameter("filter", filter);
        request.setParameter("searchVal", searchVal);

        String result = executeAction(getActionUrl());
        String expected = buildExpectedResult(pedidos);

        List<PedidoJson> expectedPedidos = toList(expected);
        List<PedidoJson> resultPedidos = toList(result);

        assertNotNull(resultPedidos);
        assertThat(resultPedidos, is(not(empty())));
        assertThat(resultPedidos, containsInAnyOrder(expectedPedidos.toArray()));
    }

    private List<PedidoJson> toList(String list) {
        return (List<PedidoJson>) JSONArray.toCollection(JSONArray.fromObject(list, CONFIG), PedidoJson.class);
    }

    private String buildExpectedResult(List<Pedido> pedidos) {
        return JSONSerializer.toJSON(pedidos, CONFIG).toString();
    }

    @Override
    protected String getActionName() {
        return "BuscarPedido";
    }

    @Override
    protected String getActionNamespace() {
        return "/admin/ventas";
    }

    public static class PedidoJson {
        private Integer id;
        private Double total;
        private Date fecha;
        private Integer cuotas;
        private Pago pago;
        private Cliente cliente;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Double getTotal() {
            return total;
        }

        public void setTotal(Double total) {
            this.total = total;
        }

        public Date getFecha() {
            return fecha;
        }

        public void setFecha(Date fecha) {
            this.fecha = fecha;
        }

        public Integer getCuotas() {
            return cuotas;
        }

        public void setCuotas(Integer cuotas) {
            this.cuotas = cuotas;
        }

        public Pago getPago() {
            return pago;
        }

        public void setPago(Pago pago) {
            this.pago = pago;
        }

        public Cliente getCliente() {
            return cliente;
        }

        public void setCliente(Cliente cliente) {
            this.cliente = cliente;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
            result = prime * result + ((cuotas == null) ? 0 : cuotas.hashCode());
            result = prime * result + ((id == null) ? 0 : id.hashCode());
            result = prime * result + ((pago == null) ? 0 : pago.hashCode());
            result = prime * result + ((total == null) ? 0 : total.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            PedidoJson other = (PedidoJson) obj;
            if (cliente == null) {
                if (other.cliente != null) {
                    return false;
                }
            } else if (!cliente.getEmail().equals(other.cliente.getEmail())) {
                return false;
            }
            if (cuotas == null) {
                if (other.cuotas != null) {
                    return false;
                }
            } else if (!cuotas.equals(other.cuotas)) {
                return false;
            }
            if (id == null) {
                if (other.id != null) {
                    return false;
                }
            } else if (!id.equals(other.id)) {
                return false;
            }
            if (pago == null) {
                if (other.pago != null) {
                    return false;
                }
            } else if (!pago.getId().equals(other.pago.getId())) {
                return false;
            }
            if (total == null) {
                if (other.total != null) {
                    return false;
                }
            } else if (!total.equals(other.total)) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "PedidoJson [id=" + id + ", total=" + total + ", cuotas=" + cuotas + ", pago=" + pago.getId()
                    + ", cliente=" + cliente.getEmail() + "]";
        }

    }

}
