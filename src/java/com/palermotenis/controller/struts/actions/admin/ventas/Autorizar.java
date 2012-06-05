/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.admin.ventas;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.model.beans.ventas.Listado;
import com.palermotenis.util.SecurityUtil;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.security.access.AccessDeniedException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

/**
 *
 * @author Poly
 */
public class Autorizar extends ActionSupport {

    private static final String BAD_CREDENTIALS = "badCredentials";
    private static final String ACCESS_DENIED = "accessDenied";
    private String usuario;
    private String clave;
    private AuthenticationManager authenticationManager;
    private SecurityUtil securityUtil;
    private GenericDao<Listado, String> listadoService;
    private String listadoId;
    private Listado listado;

    private static final Logger logger = Logger.getLogger(Autorizar.class);

    @Override
    public String execute() {

        Authentication auth = new UsernamePasswordAuthenticationToken(usuario, clave);
        try {
            Authentication result = authenticationManager.authenticate(auth);
            Usuario user = (Usuario) result.getPrincipal();
            if (!securityUtil.isSupervisor(user)) {
                throw new AccessDeniedException("El usuario no es supervisor");
            }
        } catch (BadCredentialsException ex) {
            logger.warn("Credenciales erróneas al autorizar listado");
            return BAD_CREDENTIALS;
        } catch (AccessDeniedException adex) {
            logger.warn("Acceso denegado al autorizar listado");
            return ACCESS_DENIED;
        }

        listado = listadoService.find(listadoId);
        listado.setAutorizado(true);
        try {
            listadoService.edit(listado);
        } catch (HibernateException ex) {
            logger.error("No existe el listado definido!", ex);
            return ERROR;
        } catch (Exception ex) {
            logger.error("Ha ocurrido un error al editar "+listado, ex);
            return ERROR;
        }

        return SUCCESS;
    }

    public Listado getListado(){
        return listadoService.find(listadoId);
    }

    public String getListadoId() {
        return listadoId;
    }

    public void setListadoId(String listadoId) {
        this.listadoId = listadoId;
    }

    public void setListadoService(GenericDao<Listado, String> listadoService) {
        this.listadoService = listadoService;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void setSecurityUtil(SecurityUtil securityUtil) {
        this.securityUtil = securityUtil;
    }

    
}
