package com.palermotenis.model.dao.usuario;

import java.util.List;

import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.model.dao.Dao;

public interface UsuarioDao extends Dao<Usuario, String> {

    Usuario getUsuarioByUsername(String username);

    List<Usuario> getUsuariosByUsername(String username);

}
