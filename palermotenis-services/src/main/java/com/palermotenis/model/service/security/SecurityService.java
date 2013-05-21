package com.palermotenis.model.service.security;

import com.palermotenis.model.beans.usuarios.Usuario;

public interface SecurityService {

    boolean isLoggedInUserSupervisor();

    boolean isSupervisor(Usuario usuario);

}
