/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.daos;

import com.palermotenis.controller.daos.exceptions.NonexistentEntityException;
import com.palermotenis.controller.daos.exceptions.PreexistingEntityException;
import com.palermotenis.model.beans.usuarios.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author Poly
 */
public interface UsuarioService extends UserDetailsService {

    public EntityManager getEntityManager();

    public void setEntityManager(EntityManager emf);

    public void create(Usuario usuario) throws PreexistingEntityException, Exception;

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception;

    public void destroy(String id) throws NonexistentEntityException;

    public List<Usuario> findUsuarioEntities();

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult);

    public List<Usuario> findUsuariosByUsername(String username);

    public Usuario findUsuario(String usuario);

    public int getUsuarioCount();
}
