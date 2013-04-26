package com.palermotenis.controller.struts.actions.admin.ventas;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.model.beans.ventas.Listado;
import com.palermotenis.model.dao.Dao;
import com.palermotenis.util.SecurityUtil;

/**
 * 
 * @author Poly
 */
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
    private Dao<Listado, String> listadoDao;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityUtil securityUtil;

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

        listado = listadoDao.find(listadoId);
        listado.setAutorizado(true);
        try {
            listadoDao.edit(listado);
        } catch (HibernateException ex) {
            logger.error("No existe el listado definido!", ex);
            return ERROR;
        } catch (Exception ex) {
            logger.error("Ha ocurrido un error al editar " + listado, ex);
            return ERROR;
        }

        return SUCCESS;
    }

    public Listado getListado() {
        return listadoDao.find(listadoId);
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
