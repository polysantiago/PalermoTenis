/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.palermotenis.model.beans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.apache.velocity.app.VelocityEngine;
import org.hibernate.CacheMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.format.number.CurrencyFormatter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.palermotenis.controller.carrito.Carrito;
import com.palermotenis.jobs.EnviarListadoStockJob;
import com.palermotenis.model.beans.precios.PrecioPresentacion;
import com.palermotenis.model.beans.precios.PrecioUnidad;
import com.palermotenis.model.beans.precios.pks.PrecioPresentacionPK;
import com.palermotenis.model.beans.precios.pks.PrecioProductoPK;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.valores.ValorPosible;
import com.palermotenis.model.dao.Dao;

/**
 * 
 * @author Poly
 */
@Transactional
public class TesteoTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private VelocityEngine velocityEngine;
    private ApplicationContext applicationContext;
    private JavaMailSender mailSender;
    private Dao<Stock, Integer> stockService;
    private Dao<Producto, Integer> productoService;
    private Dao<Modelo, Integer> modeloService;
    private Dao<ValorPosible, Integer> valorPosibleService;
    private Dao<PrecioUnidad, PrecioProductoPK> precioUnidadService;
    private Dao<PrecioPresentacion, PrecioPresentacionPK> precioPresentacionService;
    private CurrencyFormatter currencyFormatter;
    private Carrito carrito;
    private EnviarListadoStockJob enviarListadoStockJob;

    // @Test
    public void asASas() {
        List<String> list1 = new ArrayList<String>();
        List<Integer> list2 = new ArrayList<Integer>();
        List<Double> list3 = new ArrayList<Double>();

        list1.add("hola"); // 4
        list1.add("pepe"); // 4
        list2.add(1); // 4
        list2.add(2); // 4
        list2.add(3); // 4
        list3.add(4.0); // 4
        list3.add(5.0);

        int cont = 0;
        for (String s : list1) {
            for (Integer i : list2) {
                for (Double d : list3) {
                    System.out.print(s + "\t");
                    System.out.print(i + "\t");
                    System.out.print(d + "\t");
                    System.out.println("Cont: " + ++cont);
                }
            }
        }

        System.out.println(cont);
        System.out.println("");

        cont = 0;
        int cont2 = 0;
        List<Object> list = new ArrayList<Object>(list1);
        addList(list, list2);
        list2.addAll(new ArrayList(list));
        List<Object> newList = addList(new ArrayList(list2), list3);

        for (Object o : newList) {
            if (o instanceof String) {
                System.out.print((String) o + "\t");
            } else if (o instanceof Integer) {
                System.out.print(o + "\t");
            } else if (o instanceof Double) {
                System.out.print(o + "\t");
            }
            if (++cont % 3 == 0) {
                System.out.println("Cont: " + ++cont2);
            }
        }

    }

    private List<Object> addList(List<Object> list, List<?> newList) {
        if (newList.size() > 1) {
            for (int i = 0; i < newList.size() - 1; i++) {
                list.addAll(list);
                System.out.println(list);
            }
        }
        list.addAll(newList);
        return list;
    }

    // @Test
    public void sendMail() {

        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {

                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

                message.setSubject("¡Nuevo Sitio Web y Más Ofertas!");
                message.setFrom("Newsletter PalermoTenis.com.ar <newsletter@palermotenis.com.ar>");
                message.setReplyTo("PalermoTenis <consultas@palermotenis.com.ar>");
                message.setBcc(InternetAddress.parse("pablo.santiago@gmail.com"));

                Map<String, Object> model = new HashMap<String, Object>();

                String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                    "templates/newsletter/newsletter.vm", "ISO-8859-1", model);

                message.setText(text, true);
            }
        };
        mailSender.send(preparator);
    }

    @Test
    public void test() {
        Session session = (Session) em.getDelegate();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        ScrollableResults stocks = session
            .getNamedQuery("Stock.findAll")
            .setCacheMode(CacheMode.IGNORE)
            .scroll(ScrollMode.FORWARD_ONLY);
        int count = 0;
        while (stocks.next()) {
            Stock stock = (Stock) stocks.get(0);
            Producto producto = stock.getProducto();
            // String modeloZF = ("0000" +
            // producto.getModelo().getId()).substring(String.valueOf(producto.getModelo().getId()).length());
            String productoZF = ("0000" + producto.getId()).substring(String.valueOf(producto.getId()).length());
            String presentacionZF = (stock.getPresentacion() != null) ? ("0000" + stock.getPresentacion().getId())
                .substring(String.valueOf(stock.getPresentacion().getId()).length()) : "0000";
            stock.setCodigoDeBarra(producto.getModelo().getId() + productoZF + presentacionZF);
            em.merge(stock);
            if (++count % 100 == 0) {
                session.flush();
                session.clear();
            }
        }
        tx.commit();
        session.close();
    }

    public void getCotiz() {

        // double start = System.currentTimeMillis();

        try {
            String yahooURL = "http://quote.yahoo.com/d/quotes.txt?s=USDARS=X&f=l1&e=.csv";
            URL url = new URL(yahooURL);
            HttpURLConnection m_con = (HttpURLConnection) url.openConnection();
            m_con.setDoOutput(true);

            BufferedReader reader = new BufferedReader(new InputStreamReader(m_con.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("1 USD = " + Float.parseFloat(line) + " ARS");
            }
            reader.close();
            m_con.disconnect();

        } catch (MalformedURLException ex) {
            Logger.getLogger(TesteoTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TesteoTest.class.getName()).log(Level.SEVERE, null, ex);
        } /*
           * finally { System.out.println("Yahoo!: " + ((System.currentTimeMillis() - start) / 1000)); }
           */

    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        applicationContext = new FileSystemXmlApplicationContext(
            "src/main/resources/spring/applicationContext-business.xml");
        mailSender = (JavaMailSender) applicationContext.getBean("mailSender");
        velocityEngine = (VelocityEngine) applicationContext.getBean("velocityEngine");
        stockService = (Dao<Stock, Integer>) applicationContext.getBean("stockService");
        productoService = (Dao<Producto, Integer>) applicationContext.getBean("productoService");
        modeloService = (Dao<Modelo, Integer>) applicationContext.getBean("modeloService");
        valorPosibleService = (Dao<ValorPosible, Integer>) applicationContext.getBean("valorPosibleService");
        precioUnidadService = (Dao<PrecioUnidad, PrecioProductoPK>) applicationContext.getBean("precioUnidadService");
        precioPresentacionService = (Dao<PrecioPresentacion, PrecioPresentacionPK>) applicationContext
            .getBean("precioPresentacionService");

        // sucursalService = (GenericDao<Sucursal, Integer>) applicationContext.getBean("sucursalService");

        currencyFormatter = (CurrencyFormatter) applicationContext.getBean("currencyFormatter");
        carrito = (Carrito) applicationContext.getBean("carrito");
        enviarListadoStockJob = (EnviarListadoStockJob) applicationContext.getBean("enviarListadoStockJob");

        emf = (EntityManagerFactory) applicationContext.getBean("entityManagerFactory");
        em = emf.createEntityManager();
        TransactionSynchronizationManager.bindResource(emf, new EntityManagerHolder(em));
    }

    @After
    public void tearDown() {
        // EntityManagerHolder holder = (EntityManagerHolder) TransactionSynchronizationManager.getResource(emf);
        // holder.getEntityManager().
        TransactionSynchronizationManager.unbindResource(emf);
        EntityManagerFactoryUtils.closeEntityManager(em);
    }
}
