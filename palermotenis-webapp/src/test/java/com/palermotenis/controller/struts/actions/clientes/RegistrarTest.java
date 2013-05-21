package com.palermotenis.controller.struts.actions.clientes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionProxy;
import com.palermotenis.infrastructure.testsupport.base.BaseSpringStrutsTestCase;
import com.palermotenis.model.beans.clientes.Cliente;
import com.palermotenis.model.beans.newsletter.Suscriptor;
import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.model.service.newsletter.SuscriptorService;
import com.palermotenis.model.service.usuarios.UsuarioService;
import com.palermotenis.support.TestClienteService;

public class RegistrarTest extends BaseSpringStrutsTestCase<Registrar> {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private SuscriptorService suscriptorService;

    @Autowired
    private TestClienteService testClienteService;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testRegistrar() throws Exception {
        ActionProxy proxy = getActionProxy("/clientes_web/Registrar");
        Registrar action = (Registrar) proxy.getAction();

        Cliente testCliente = testClienteService.create(true);

        String testEmail = testCliente.getEmail();

        action.setNombre(testCliente.getNombre());
        action.setCiudad(testCliente.getDireccion().getCiudad());
        action.setContrasenia("testPassword");
        action.setDireccion(testCliente.getDireccion().getDireccion());
        action.setEmail(testEmail);
        action.setSuscriptor(true);
        action.setTelefono(testCliente.getTelefono());

        proxy.execute();

        Usuario usuario = action.getUsuario();
        assertNotNull(usuario);

        // refresh
        entityManager.flush();
        entityManager.refresh(usuario);

        assertNotNull(usuario);
        assertTrue(usuario.isEnabled());
        assertTrue(usuario.isAccountNonLocked());
        assertTrue(usuario.isActivo());
        assertNotEquals("testPassword", usuario.getPassword());
        assertEquals(testEmail, usuario.getUsername());
        assertEquals(testEmail, usuario.getUsuario());

        assertNotNull(usuario.getCliente());
        assertEquals("testName", usuario.getCliente().getNombre());
        assertEquals(testEmail, usuario.getCliente().getEmail());
        assertEquals("123456788", usuario.getCliente().getTelefono());

        assertNotNull(usuario.getCliente().getDireccion());
        assertEquals("testCiudad", usuario.getCliente().getDireccion().getCiudad());
        assertEquals("testAddress 123", usuario.getCliente().getDireccion().getDireccion());

        Suscriptor suscriptor = suscriptorService.getSuscriptorByEmail(testEmail);
        assertNotNull(suscriptor);
        assertTrue(suscriptor.isActivo());
    }

}
