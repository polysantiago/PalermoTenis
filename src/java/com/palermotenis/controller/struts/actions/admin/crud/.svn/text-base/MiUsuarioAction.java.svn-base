/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.admin.crud;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.UsuarioService;
import com.palermotenis.controller.daos.exceptions.NonexistentEntityException;
import com.palermotenis.model.beans.usuarios.Usuario;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Poly
 */
public class MiUsuarioAction extends ActionSupport {

    private final static String EDIT = "edit";
    private UsuarioService usuariosService;
    private String username;
    private String contrasenia;
    private String rptContrasenia;
    private Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private PasswordEncoder passwordEncoder;

    public String edit() {
        try {
            usuario.setUsuario(username);
            usuario.setPassword(passwordEncoder.encodePassword(contrasenia, null));
            usuariosService.edit(usuario);            
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(MiUsuarioAction.class.getName()).log(Level.SEVERE, null, ex);
            return ERROR;
        } catch (Exception ex) {
            Logger.getLogger(MiUsuarioAction.class.getName()).log(Level.SEVERE, null, ex);
            return ERROR;
        }
        return EDIT;
    }

    @Override
    public void validate() {
        if (!usuario.getUsername().equals(username) && !usuariosService.findUsuariosByUsername(username).isEmpty()) {
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
        return usuario;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setUsuariosService(UsuarioService usuariosService) {
        this.usuariosService = usuariosService;
    }
}
