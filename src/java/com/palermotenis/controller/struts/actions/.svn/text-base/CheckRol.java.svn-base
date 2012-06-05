/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.usuarios.Usuario;
import java.io.InputStream;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.palermotenis.util.StringUtility;
import com.palermotenis.model.beans.authorities.Rol;

/**
 *
 * @author poly
 */
public class CheckRol extends ActionSupport {

    private String role;
    private GenericDao<Rol, Integer> rolService;
    private InputStream inputStream;

    public String check() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof Usuario)) {
            inputStream = StringUtility.getInputString("NOT_AUTHENTICATED");
        } else if (!authentication.getAuthorities().contains(rolService.findBy("Rol", "rol", role))) {
            inputStream = StringUtility.getInputString("NOT_IN_ROLE");
        } else {
            inputStream = StringUtility.getInputString("OK");
        }
        return SUCCESS;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setRolService(GenericDao<Rol, Integer> rolService) {
        this.rolService = rolService;
    }
}
