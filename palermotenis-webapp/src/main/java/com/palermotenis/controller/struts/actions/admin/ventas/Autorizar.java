package com.palermotenis.controller.struts.actions.admin.ventas;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.model.beans.ventas.Listado;
import com.palermotenis.model.service.security.SecurityService;
import com.palermotenis.model.service.ventas.VentaService;

public class Autorizar extends ActionSupport {

    private static final long serialVersionUID = -8940207114274196062L;
    private static final Logger logger = Logger.getLogger(Autorizar.class);

    private static final String BAD_CREDENTIALS = "badCredentials";
    private static final String ACCESS_DENIED = "accessDenied";

    private String usuario;
    private String clave;

    private String listadoId;
    private Listado listado;

    @Autowired
    private VentaService ventaService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public String execute() {
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, clave);
            Authentication result = authenticationManager.authenticate(authentication);
            Usuario user = (Usuario) result.getPrincipal();
            if (!securityService.isSupervisor(user)) {
                throw new AccessDeniedException("El usuario no es supervisor");
            }
            listado = ventaService.authorizeListing(usuario, clave, listadoId);
        } catch (BadCredentialsException ex) {
            logger.debug("Credenciales erróneas al autorizar listado");
            return BAD_CREDENTIALS;
        } catch (AccessDeniedException adex) {
            logger.debug("Acceso denegado al autorizar listado");
            return ACCESS_DENIED;
        } catch (Exception ex) {
            logger.error("Ha ocurrido un error al editar " + listado, ex);
            return ERROR;
        }
        return SUCCESS;
    }

    public Listado getListado() {
        if (listado == null) {
            listado = ventaService.getListadoById(listadoId);
        }
        return listado;
    }

    public String getListadoId() {
        return listadoId;
    }

    public void setListadoId(String listadoId) {
        this.listadoId = listadoId;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

}
