package com.palermotenis.model.service.usuarios;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.model.dao.exceptions.PreexistingEntityException;

public interface UsuarioService extends UserDetailsService {

    void create(Usuario usuario) throws PreexistingEntityException, Exception;

    void update(Usuario usuario, String username, String password);

    Usuario getUsuarioByLoginame(String usuario);

    List<Usuario> getUsuariosByUsername(String username);
}
