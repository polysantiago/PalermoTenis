package com.palermotenis.controller.struts.actions;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.palermotenis.model.beans.authorities.Rol;
import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.model.service.authorities.RolService;

public class CheckRol extends InputStreamActionSupport {

    private static final long serialVersionUID = -7583788267327228266L;

    private String role;

    @Autowired
    private RolService rolService;

    public String check() {
        Authentication authentication = getAuthentication();
        if (!(authentication.getPrincipal() instanceof Usuario)) {
            writeResponse("NOT_AUTHENTICATED");
        } else if (!isInRol(authentication)) {
            writeResponse("NOT_IN_ROLE");
        } else {
            success();
        }
        return SUCCESS;
    }

    private boolean isInRol(Authentication authentication) {
        try {
            return authentication.getAuthorities().contains(getRol());
        } catch (NoResultException ex) {
            return false;
        }
    }

    private Rol getRol() {
        return rolService.getRol(role);
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public void setRole(String role) {
        this.role = role;
    }
}
