package com.palermotenis.model.service.carrito.impl;

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
import com.palermotenis.util.SecurityUtil;
import com.palermotenis.util.StringUtility;

@Service("pedidoService")
public class PedidoServiceImpl implements PedidoService {

    private static final String ENCODING = "ISO-8859-1";
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

    @Override
    @Transactional
    public Pedido createNewPedido(Integer pagoId, Integer cuotas, Double total) {
        Pago pago = pagoService.getPagoById(pagoId);

        Pedido pedido = new Pedido(getUsuario().getCliente(), pago, cuotas, total);
        pedidoDao.create(pedido);

        return pedido;
    }

    @Override
    @Transactional
    public void send(Pedido pedido, Carrito carrito) {
        for (Entry<Stock, Item> entry : carrito.getContenido().entrySet()) {
            Stock stock = entry.getKey();
            Item item = entry.getValue();
            PedidoProducto pedidoProducto = createPedidoProducto(pedido, item, stock);
            pedido.addPedidoProducto(pedidoProducto);
        }
        pedidoDao.edit(pedido);

        enviarCopiaPedido(carrito);
        informarPedido(pedido);
        carrito.vaciar();
    }

    private PedidoProducto createPedidoProducto(Pedido pedido, Item item, Stock stock) {
        PedidoProductoPK pk = new PedidoProductoPK(pedido, stock);
        PedidoProducto pedidoProducto = new PedidoProducto(pk, item.getCantidad(), item.getSubtotal());
        pedidoProductoDao.create(pedidoProducto);
        return pedidoProducto;
    }

    private void enviarCopiaPedido(final Carrito carrito) {
        final Usuario usuario = getUsuario();
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

                String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, PEDIDO_TEMPLATE, ENCODING,
                    model);

                message.setText(text, true);
            }
        };
        mailSender.send(preparator);
    }

    private void informarPedido(final Pedido pedido) {
        final Usuario usuario = getUsuario();
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

                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress("pedidos@palermotenis.com.ar"));
                mimeMessage.setFrom(new InternetAddress("PalermoTenis <noreply@palermotenis.com.ar>"));
                mimeMessage.setReplyTo(InternetAddress.parse(cliente.getNombre() + "<" + usuario.getUsuario() + ">"));
                mimeMessage.setContent(new MimeMultipart("alternative"));
                mimeMessage.setSubject("Se ha realizado un pedido desde PalermoTenis.com.ar", ENCODING);

                Map<String, Object> model = new ImmutableMap.Builder<String, Object>()
                    .put("cliente", cliente)
                    .put("pedido", pedido)
                    .put("detalle", detalle)
                    .put("formatter", currencyFormatter)
                    .put("locale", LOCALE_ES_AR)
                    .build();

                String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, INFORMAR_PEDIDO_TEMPLATE,
                    ENCODING, model);

                mimeMessage.setText(text);
            }
        };
        mailSender.send(preparator);
    }

    private Usuario getUsuario() {
        return SecurityUtil.getLoggedInUser();
    }

}
