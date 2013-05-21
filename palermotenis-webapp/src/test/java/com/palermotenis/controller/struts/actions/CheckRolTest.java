package com.palermotenis.controller.struts.actions;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.google.common.collect.Lists;
import com.palermotenis.infrastructure.testsupport.base.BaseSpringStrutsTestCase;
import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.model.service.authorities.RolService;

public class CheckRolTest extends BaseSpringStrutsTestCase<CheckRol> {

    private static final String ACTION_NAME = "CheckRol_*";
    private static final String ACTION_NAMESPACE = "/";
    private static final String ACTION_MAPPING = "/CheckRol_*";

    @Autowired
    private RolService rolService;

    @Test
    public void testCheckRolUnauthenticated() throws UnsupportedEncodingException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(Mockito.mock(Authentication.class));
        String result = executeAction("/CheckRol_check");
        assertEquals("NOT_AUTHENTICATED", result);
        assertEquals("text/plain;charset=ISO-8859-1", response.getContentType());
    }

    @Test
    public void testCheckRolNotInRole() throws UnsupportedEncodingException, ServletException {
        initializeUserInRole("ROLE_CLIENTE");
        request.setParameter("role", "INVALID_ROLE");
        String result = executeAction("/CheckRol_check");
        assertEquals("NOT_IN_ROLE", result);
        assertEquals("text/plain;charset=ISO-8859-1", response.getContentType());
    }

    @Test
    public void testCheckUserInRole() throws UnsupportedEncodingException, ServletException {
        initializeUserInRole("ROLE_CLIENTE");
        request.setParameter("role", "ROLE_CLIENTE");
        String result = executeAction("/CheckRol_check");
        assertEquals("OK", result);
        assertEquals("text/plain;charset=ISO-8859-1", response.getContentType());
    }

    private void initializeUserInRole(String role) {
        GrantedAuthority rol = rolService.getRol(role);
        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(new Usuario(),
            "password", Lists.newArrayList(rol));
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);
    }

    @Override
    protected String getActionUrl() {
        return ACTION_MAPPING;
    }

    @Override
    protected String getActionNamespace() {
        return ACTION_NAMESPACE;
    }

    @Override
    protected String getActionName() {
        return ACTION_NAME;
    }

}
