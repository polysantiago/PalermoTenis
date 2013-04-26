package com.palermotenis.model.dao.usuario.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.usuario.UsuarioDao;

@Repository("usuarioDao")
public class UsuarioDaoHibernateImpl extends AbstractHibernateDao<Usuario, String> implements UsuarioDao {

    @Override
    public Usuario getUsuarioByUsername(String username) {
        return findBy("Usuario", "usuario", username);
    }

    @Override
    public List<Usuario> getUsuariosByUsername(String username) {
        return query("Usuario", "usuario", username);
    }

}
