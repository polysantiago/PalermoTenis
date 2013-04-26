package com.palermotenis.model.service.usuarios;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.model.dao.exceptions.PreexistingEntityException;

public interface UsuarioService extends UserDetailsService {

    public void create(Usuario usuario) throws PreexistingEntityException, Exception;

    public Usuario findUsuario(String usuario);
}
