package com.palermotenis.controller.struts.actions.carrito.checkout;

import java.util.Locale;
import java.util.Map;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.velocity.app.VelocityEngine;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.number.CurrencyFormatter;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.google.common.collect.ImmutableMap;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.carrito.Carrito;
import com.palermotenis.controller.carrito.Item;
import com.palermotenis.controller.struts.actions.carrito.CarritoAction;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.clientes.Cliente;
import com.palermotenis.model.beans.pedidos.Pedido;
import com.palermotenis.model.beans.pedidos.PedidoProducto;
import com.palermotenis.model.beans.pedidos.PedidoProductoPK;
import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.model.dao.Dao;
import com.palermotenis.util.SecurityUtil;
import com.palermotenis.util.StringUtility;

/**
 * 
 * @author Poly
 */
public class EnviarPedido extends ActionSupport implements ServletRequestAware {

    private static final long serialVersionUID = -8773400739393474328L;
    private static final Logger logger = Logger.getLogger(CarritoAction.class);
    private static final Locale LOCALE_ES_AR = new Locale("es", "AR");

    private int pagoId;
    private int cuotas;
    private Carrito carrito;
    private Pedido pedido;

    private JavaMailSender mailSender;
    private VelocityEngine velocityEngine;
    private HttpServletRequest request;

    @Autowired
    private Dao<Pago, Integer> pagoDao;

    @Autowired
    private Dao<Pedido, Integer> pedidoDao;

    @Autowired
    private Dao<PedidoProducto, Integer> pedidoProductoDao;

    @Autowired
    private CurrencyFormatter currencyFormatter;

    @Override
    public String execute() {

        logger.info("Creando pedido " + contextInfo());
        pedido = new Pedido(getUsuario().getCliente(), pagoDao.find(pagoId), cuotas, carrito.getTotal());
        pedidoDao.create(pedido);
        for (Stock stock : carrito.getContenido().keySet()) {
            try {
                Item item = carrito.getContenido().get(stock);
                PedidoProductoPK pk = new PedidoProductoPK(pedido, stock);
                PedidoProducto pp = new PedidoProducto(pk, item.getCantidad());
                pp.setSubtotal(item.getSubtotal());
                pedidoProductoDao.create(pp);
                pedido.addPedidoProducto(pp);
            } catch (HibernateException ex) {
                Logger.getLogger(EnviarPedido.class.getName()).log(Level.ERROR, null, ex);
                return ERROR;
            } catch (Exception ex) {
                Logger.getLogger(EnviarPedido.class.getName()).log(Level.ERROR, null, ex);
                return ERROR;
            }
        }
        try {
            pedidoDao.edit(pedido);
        } catch (HibernateException ex) {
            Logger.getLogger(EnviarPedido.class.getName()).log(Level.ERROR, null, ex);
            return ERROR;
        } catch (Exception ex) {
            Logger.getLogger(EnviarPedido.class.getName()).log(Level.ERROR, null, ex);
            return ERROR;
        }

        logger.info(pedido + " creado " + contextInfo());
        enviarCopiaPedido(getUsuario());
        informarPedido(pedido, getUsuario());
        carrito.vaciar();

        return SUCCESS;
    }

    private void enviarCopiaPedido(final Usuario usuario) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {

                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

                message.setSubject("Confirmación del Pedido desde PalermoTenis.com.ar");
                message.setTo(usuario.getCliente().getNombre() + "<" + usuario.getUsuario() + ">");
                message.setFrom("PalermoTenis <noreply@palermotenis.com.ar>");
                message.setReplyTo("PalermoTenis <consultas@palermotenis.com.ar>");

                Map<String, Object> model = new ImmutableMap.Builder<String, Object>()
                    .put("usuario", usuario)
                    .put("carrito", carrito)
                    .put("formatter", currencyFormatter)
                    .put("locale", LOCALE_ES_AR)
                    .build();

                String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                    "com/palermotenis/templates/mail/pedido.vm", "ISO-8859-1", model);

                message.setText(text, true);
            }
        };
        mailSender.send(preparator);
    }

    private void informarPedido(final Pedido pedido, final Usuario usuario) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {

                Cliente cliente = usuario.getCliente();

                StringBuilder s = new StringBuilder();

                for (PedidoProducto pp : pedido.getPedidosProductos()) {
                    s.append(StringUtility.buildNameFromStock(pp.getId().getStock())).append(", ");
                }
                s.deleteCharAt(s.length() - 2);

                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress("pedidos@palermotenis.com.ar"));
                mimeMessage.setFrom(new InternetAddress("PalermoTenis <noreply@palermotenis.com.ar>"));
                mimeMessage.setReplyTo(InternetAddress.parse(cliente.getNombre() + "<" + usuario.getUsuario() + ">"));
                mimeMessage.setContent(new MimeMultipart("alternative"));
                mimeMessage.setSubject("Se ha realizado un pedido desde PalermoTenis.com.ar", "ISO-8859-1");
                mimeMessage.setText(
                    "Nº de cliente : " + cliente.getId() + "\n" + "Nombre del cliente : " + cliente.getNombre() + "\n"
                            + "Teléfono : " + cliente.getTelefono() + "\n" + "Nº de pedido : " + pedido.getId() + "\n"
                            + "Detalle : " + s + "\n" + "Total : "
                            + currencyFormatter.print(pedido.getTotal(), LOCALE_ES_AR), "ISO-8859-1");
            }
        };
        try {
            this.mailSender.send(preparator);
            logger.info("Copia enviada a " + usuario.getUsuario() + " por " + pedido);
        } catch (MailException ex) {
            Logger.getLogger(EnviarPedido.class.getName()).log(Level.ERROR, null, ex);
        }
    }

    private String contextInfo() {
        return " - User[" + getUsuario() + "] - IP[" + getRemoteAddress() + "] - Browser[" + getBrowser() + "]";
    }

    private Usuario getUsuario() {
        return SecurityUtil.getLoggedInUser();
    }

    private String getBrowser() {
        return request.getHeader("User-Agent");
    }

    private String getRemoteAddress() {
        return request.getRemoteAddr();
    }

    /**
     * @param pagoId
     *            the pagoId to set
     */
    public void setPagoId(int pagoId) {
        this.pagoId = pagoId;
    }

    /**
     * @param carrito
     *            the carrito to set
     */
    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    /**
     * @param cuotas
     *            the cuotas to set
     */
    public void setCuotas(int cuotas) {
        this.cuotas = cuotas;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    public Pedido getPedido() {
        return pedido;
    }
}
