/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.util;

import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.authorities.Rol;
import com.palermotenis.model.beans.usuarios.Usuario;

/**
 *
 * @author Poly
 */
public class SecurityUtil {

    private GenericDao<Rol, Integer> rolService;
    private static Rol ROLE_ADMIN;
    private static Rol ROLE_SUPERVISOR;    

    public static boolean userIsInRole(Usuario usuario, Rol rol) {
        return usuario.getRoles().contains(rol);
    }

    public boolean isSupervisor(Usuario usuario) {
        ROLE_SUPERVISOR = rolService.findBy("Rol", "rol", "ROLE_SUPERVISOR");
        ROLE_ADMIN = rolService.findBy("Rol", "rol", "ROLE_ADMIN");
        return SecurityUtil.userIsInRole(usuario, ROLE_ADMIN) || SecurityUtil.userIsInRole(usuario, ROLE_SUPERVISOR);
    }

    public void setRolService(GenericDao<Rol, Integer> rolService) {
        this.rolService = rolService;
    }
}
