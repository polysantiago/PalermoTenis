package com.palermotenis.model.service.carrito.impl;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.number.CurrencyFormatter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.palermotenis.controller.carrito.Carrito;
import com.palermotenis.controller.carrito.Item;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.clientes.Cliente;
import com.palermotenis.model.beans.pedidos.Pedido;
import com.palermotenis.model.beans.pedidos.PedidoProducto;
import com.palermotenis.model.beans.pedidos.PedidoProductoPK;
import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.model.dao.pedidos.PedidoDao;
import com.palermotenis.model.dao.pedidos.PedidoProductoDao;
import com.palermotenis.model.service.carrito.PedidoService;
import com.palermotenis.model.service.pagos.PagoService;
import com.palermotenis.util.StringUtility;

@Service("pedidoService")
public class PedidoServiceImpl implements PedidoService {

    private static final Locale LOCALE_ES_AR = new Locale("es", "AR");

    private static final String PEDIDO_TEMPLATE = "templates/mail/pedido.vm";
    private static final String INFORMAR_PEDIDO_TEMPLATE = "templates/mail/informar_pedido.vm";

    @Autowired
    private PedidoDao pedidoDao;

    @Autowired
    private PedidoProductoDao pedidoProductoDao;

    @Autowired
    private PagoService pagoService;

    @Autowired
    private CurrencyFormatter currencyFormatter;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    @Value("${templates.mail.pedidos.inform.from}")
    private String informFrom;

    @Value("${templates.mail.pedidos.inform.to}")
    private String informTo;

    @Value("${templates.mail.pedidos.inform.subject}")
    private String informSubject;

    @Value("${templates.mail.pedidos.confirmation.subject}")
    private String confirmationSubject;

    @Value("${templates.mail.pedidos.confirmation.from}")
    private String confirmationFrom;

    @Value("${templates.mail.pedidos.confirmation.replyto}")
    private String confirmationReplyTo;

    @Value("${com.palermotenis.mail.mime.charset}")
    private String encoding;

    @Override
    @Transactional
    public Pedido create(Cliente cliente, Integer pagoId, Integer cuotas, Double total) {
        Pago pago = pagoService.getPagoById(pagoId);

        Pedido pedido = new Pedido(cliente, pago, cuotas, total);
        pedidoDao.create(pedido);

        return pedido;
    }

    @Override
    @Transactional
    public void send(Usuario usuario, Pedido pedido, Carrito carrito) {
        for (Entry<Stock, Item> entry : carrito.getContenido().entrySet()) {
            Stock stock = entry.getKey();
            Item item = entry.getValue();
            PedidoProducto pedidoProducto = createPedidoProducto(pedido, item, stock);
            pedido.addPedidoProducto(pedidoProducto);
        }
        pedidoDao.edit(pedido);

        enviarCopiaPedido(usuario, carrito);
        informarPedido(usuario, pedido);
        carrito.vaciar();
    }

    private PedidoProducto createPedidoProducto(Pedido pedido, Item item, Stock stock) {
        PedidoProductoPK pk = new PedidoProductoPK(pedido, stock);
        PedidoProducto pedidoProducto = new PedidoProducto(pk, item.getCantidad(), item.getSubtotal());
        pedidoProductoDao.create(pedidoProducto);
        return pedidoProducto;
    }

    private void enviarCopiaPedido(final Usuario usuario, final Carrito carrito) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {

                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

                message.setSubject(confirmationSubject);
                message.setTo(usuario.getCliente().getNombre() + "<" + usuario.getUsuario() + ">");
                message.setFrom(confirmationFrom);
                message.setReplyTo(confirmationReplyTo);

                Map<String, Object> model = new ImmutableMap.Builder<String, Object>()
                    .put("usuario", usuario)
                    .put("carrito", carrito)
                    .put("formatter", currencyFormatter)
                    .put("locale", LOCALE_ES_AR)
                    .build();

                String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, PEDIDO_TEMPLATE, encoding,
                    model);

                message.setText(text, true);
            }
        };
        mailSender.send(preparator);
    }

    private void informarPedido(final Usuario usuario, final Pedido pedido) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {

                Cliente cliente = usuario.getCliente();

                String detalle = Joiner.on(", ").join(
                    Iterables.transform(pedido.getPedidosProductos(), new Function<PedidoProducto, String>() {
                        @Override
                        @Nullable
                        public String apply(@Nullable PedidoProducto pedidoProducto) {
                            return StringUtility.buildNameFromStock(pedidoProducto.getId().getStock());
                        }
                    }));

                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(informTo));
                mimeMessage.setFrom(new InternetAddress(informFrom));
                mimeMessage.setReplyTo(InternetAddress.parse(cliente.getNombre() + "<" + usuario.getUsuario() + ">"));
                mimeMessage.setContent(new MimeMultipart("alternative"));
                mimeMessage.setSubject(informSubject, encoding);

                Map<String, Object> model = new ImmutableMap.Builder<String, Object>()
                    .put("cliente", cliente)
                    .put("pedido", pedido)
                    .put("detalle", detalle)
                    .put("formatter", currencyFormatter)
                    .put("locale", LOCALE_ES_AR)
                    .build();

                String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, INFORMAR_PEDIDO_TEMPLATE,
                    encoding, model);

                mimeMessage.setText(text);
            }
        };
        mailSender.send(preparator);
    }

    @Override
    public List<Pedido> getAllPedidos() {
        return pedidoDao.findAll();
    }

    @Override
    public List<Pedido> getPedidosByEmail(String email) {
        return pedidoDao.queryBy("Email", "email", email);
    }

    @Override
    public List<Pedido> getPedidosByNombreCliente(String nombreCliente) {
        return pedidoDao.queryBy("Nombre", "nombre", nombreCliente);
    }

}
