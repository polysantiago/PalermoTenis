package com.palermotenis.util.security;

import org.springframework.security.core.context.SecurityContextHolder;

import com.palermotenis.model.beans.authorities.Rol;
import com.palermotenis.model.beans.usuarios.Usuario;

public abstract class SecurityUtil {

    public static boolean userIsInRole(Usuario usuario, Rol rol) {
        return usuario.getRoles().contains(rol);
    }

    public static Usuario getLoggedInUser() {
        return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
