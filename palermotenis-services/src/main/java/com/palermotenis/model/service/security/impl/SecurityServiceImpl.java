package com.palermotenis.model.service.security.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.palermotenis.model.beans.authorities.Rol;
import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.model.service.authorities.RolService;
import com.palermotenis.model.service.security.SecurityService;
import com.palermotenis.util.security.SecurityUtil;

@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

    private static Rol ROLE_ADMIN;
    private static Rol ROLE_SUPERVISOR;

    @Autowired
    private RolService rolService;

    @Override
    public boolean isLoggedInUserSupervisor() {
        Usuario usuario = SecurityUtil.getLoggedInUser();
        if (usuario == null) {
            return false;
        }
        return isSupervisor(usuario);
    }

    @Override
    public boolean isSupervisor(Usuario usuario) {
        ROLE_SUPERVISOR = rolService.getRol("ROLE_SUPERVISOR");
        ROLE_ADMIN = rolService.getRol("ROLE_ADMIN");
        return SecurityUtil.userIsInRole(usuario, ROLE_ADMIN) || SecurityUtil.userIsInRole(usuario, ROLE_SUPERVISOR);
    }

}
