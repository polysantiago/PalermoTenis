package com.palermotenis.util;

import org.springframework.security.core.context.SecurityContextHolder;

import com.palermotenis.model.beans.authorities.Rol;
import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.model.dao.Dao;

public class SecurityUtil {

    private static Rol ROLE_ADMIN;
    private static Rol ROLE_SUPERVISOR;

    private Dao<Rol, Integer> rolDao;

    public static boolean userIsInRole(Usuario usuario, Rol rol) {
        return usuario.getRoles().contains(rol);
    }

    public boolean isSupervisor(Usuario usuario) {
        ROLE_SUPERVISOR = rolDao.findBy("Rol", "rol", "ROLE_SUPERVISOR");
        ROLE_ADMIN = rolDao.findBy("Rol", "rol", "ROLE_ADMIN");
        return SecurityUtil.userIsInRole(usuario, ROLE_ADMIN) || SecurityUtil.userIsInRole(usuario, ROLE_SUPERVISOR);
    }

    public static Usuario getLoggedInUser() {
        return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public void setRolDao(Dao<Rol, Integer> rolDao) {
        this.rolDao = rolDao;
    }
}
