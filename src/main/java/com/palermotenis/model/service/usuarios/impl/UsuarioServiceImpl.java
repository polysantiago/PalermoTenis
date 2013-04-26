package com.palermotenis.model.service.usuarios.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.model.dao.exceptions.PreexistingEntityException;
import com.palermotenis.model.dao.usuario.UsuarioDao;
import com.palermotenis.model.service.usuarios.UsuarioService;

@Service("usuarioService")
@Transactional(propagation = Propagation.REQUIRED)
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    @Autowired
    private UsuarioDao usuarioDao;

    @Override
    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        try {
            usuarioDao.create(usuario);
        } catch (Exception ex) {
            if (findUsuario(usuario.getUsuario()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        }
    }

    @Override
    public Usuario findUsuario(String usuario) {
        return usuarioDao.find(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        Usuario usuario = usuarioDao.getUsuarioByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
        return usuario;
    }
}
