package com.palermotenis.controller.struts.actions.admin.crud;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.UsuarioService;
import com.palermotenis.controller.daos.exceptions.NonexistentEntityException;
import com.palermotenis.model.beans.usuarios.Usuario;

public class MiUsuarioAction extends ActionSupport {

    private static final long serialVersionUID = 3695521658985907117L;
    private static final Logger logger = Logger.getLogger(MiUsuarioAction.class);
    private static final String EDIT = "edit";

    private String username;
    private String contrasenia;
    private String rptContrasenia;

    @Autowired
    private UsuarioService usuariosService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String edit() {
        try {
            getUsuario().setUsuario(username);
            getUsuario().setPassword(passwordEncoder.encodePassword(contrasenia, null));
            usuariosService.edit(getUsuario());
        } catch (NonexistentEntityException ex) {
            logger.error("No existe el usuario", ex);
            return ERROR;
        } catch (Exception ex) {
            logger.error("Error - ", ex);
            return ERROR;
        }
        return EDIT;
    }

    @Override
    public void validate() {
        if (!getUsuario().getUsername().equals(username) && !usuariosService.findUsuariosByUsername(username).isEmpty()) {
            addFieldError("username", "Este nombre de usuario ya está registrado");
        }
    }

    public String getRptContrasenia() {
        return rptContrasenia;
    }

    public void setRptContrasenia(String rptContrasenia) {
        this.rptContrasenia = rptContrasenia;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public String getUsername() {
        return username;
    }

    public Usuario getUsuario() {
        return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
