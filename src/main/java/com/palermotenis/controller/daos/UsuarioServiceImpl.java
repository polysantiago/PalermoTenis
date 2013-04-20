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
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author Poly
 */
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    private EntityManager emf = null;

    public EntityManager getEntityManager() {
        return emf;
    }

    @PersistenceContext(unitName = "PalermoTenis")
    public void setEntityManager(EntityManager emf) {
        this.emf = emf;
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();            
            em.persist(usuario);            
        } catch (Exception ex) {
            if (findUsuario(usuario.getUsuario()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            
            usuario = em.merge(usuario);
            
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuario.getUsuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            em.remove(usuario);
            
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Usuario as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Usuario> findUsuariosByUsername(String username) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("select object(o) from Usuario as o where usuario = :usuario").setParameter("usuario", username).getResultList();
        } finally {
            em.close();
        }
    }

    public Usuario findUsuario(String usuario) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, usuario);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Usuario as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException, DataAccessException {
        EntityManager em = getEntityManager();
        try {
            return (Usuario) em.createNamedQuery("Usuario.findByUsuario").setParameter("usuario",usuario).getSingleResult();
        }catch(NoResultException nre){
            throw new UsernameNotFoundException(nre.getLocalizedMessage());
        } finally {
            em.close();
        }
    }
}
