package com.palermotenis.model.service.usuarios;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.palermotenis.model.beans.authorities.Rol;
import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.model.dao.exceptions.PreexistingEntityException;

public interface UsuarioService extends UserDetailsService {

    Usuario createActive(String email, String password, Rol rol) throws PreexistingEntityException;

    void create(Usuario usuario) throws PreexistingEntityException;

    void update(Usuario usuario, String username, String password);

    Usuario getUsuarioByLoginame(String usuario);

    List<Usuario> getUsuariosByUsername(String username);

}
