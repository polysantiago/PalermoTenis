package com.palermotenis.controller.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.palermotenis.controller.daos.exceptions.NonexistentEntityException;
import com.palermotenis.controller.daos.exceptions.PreexistingEntityException;
import com.palermotenis.model.beans.usuarios.Usuario;

public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    private EntityManager emf = null;

    @Override
    public EntityManager getEntityManager() {
        return emf;
    }

    @Override
    @PersistenceContext(unitName = "PalermoTenis")
    public void setEntityManager(EntityManager emf) {
        this.emf = emf;
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    @Override
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

    @Override
    public List<Usuario> findUsuariosByUsername(String username) {
        EntityManager em = getEntityManager();
        try {
            return em
                .createQuery("select object(o) from Usuario as o where usuario = :usuario")
                .setParameter("usuario", username)
                .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Usuario findUsuario(String usuario) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, usuario);
        } finally {
            em.close();
        }
    }

    @Override
    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Usuario as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException, DataAccessException {
        EntityManager em = getEntityManager();
        try {
            return (Usuario) em
                .createNamedQuery("Usuario.findByUsuario")
                .setParameter("usuario", usuario)
                .getSingleResult();
        } catch (NoResultException nre) {
            throw new UsernameNotFoundException(nre.getLocalizedMessage());
        } finally {
            em.close();
        }
    }
}
