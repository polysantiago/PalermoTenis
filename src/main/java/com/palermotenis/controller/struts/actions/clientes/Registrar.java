/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.clientes;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.authorities.Rol;
import com.palermotenis.model.beans.clientes.Cliente;
import com.palermotenis.model.beans.clientes.Direccion;
import com.palermotenis.model.beans.newsletter.Suscriptor;
import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.model.dao.Dao;
import com.palermotenis.model.dao.usuario.UsuarioDao;
import com.palermotenis.util.StringUtility;

/**
 * 
 * @author Poly
 */
public class Registrar extends ActionSupport {

    private static final long serialVersionUID = 2099616862622522956L;
    private static final Logger logger = Logger.getLogger(Registrar.class);

    private String nombre;
    private String email;
    private String ciudad;
    private String direccion;
    private String telefono;
    private String contrasenia;
    private String rptContrasenia;

    private boolean suscriptor;

    private Usuario usuario;

    @Autowired
    private Dao<Cliente, Integer> clienteDao;

    @Autowired
    private Dao<Rol, Integer> rolDao;

    @Autowired
    private Dao<Suscriptor, Integer> suscriptorDao;

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String execute() {

        usuario = new Usuario(email);
        usuario.setPassword(passwordEncoder.encodePassword(contrasenia, null));
        usuario.setActivo(true);
        usuario.addRol(rolDao.findBy("Rol", "rol", "ROLE_CLIENTE"));

        Cliente cliente = new Cliente(nombre, new Direccion(direccion, ciudad), telefono);
        cliente.setUsuario(usuario);
        cliente.setEmail(email);
        usuario.setCliente(cliente);
        try {
            clienteDao.create(cliente); // crear cliente
            if (isSuscriptor()) {
                Suscriptor sus = new Suscriptor(email, StringUtility.buildRandomString(), true);
                suscriptorDao.create(sus); // crear suscriptor
            }
        } catch (HibernateException ex) {
            logger.error("Error al crear cliente", ex);
            try {
                usuarioDao.destroy(usuario);
            } catch (Exception ex1) {
                logger.error("No se pudo eliminar el usuario " + usuario + " luego del error", ex1);
            }
        }

        return SUCCESS;
    }

    @Override
    public void validate() {
        List<Usuario> usuarios = usuarioDao.getUsuariosByUsername(getEmail());
        if (!usuarios.isEmpty()) {
            addFieldError("email", "Esta casilla de mail ya está registrada");
        }
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre
     *            the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * @param ciudad
     *            the ciudad to set
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion
     *            the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono
     *            the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the contrasenia
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * @param contrasenia
     *            the contrasenia to set
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     * @return the rptContrasenia
     */
    public String getRptContrasenia() {
        return rptContrasenia;
    }

    /**
     * @param rptContrasenia
     *            the rptContrasenia to set
     */
    public void setRptContrasenia(String rptContrasenia) {
        this.rptContrasenia = rptContrasenia;
    }

    /**
     * @return the suscriptor
     */
    public boolean isSuscriptor() {
        return suscriptor;
    }

    /**
     * @param suscriptor
     *            the suscriptor to set
     */
    public void setSuscriptor(boolean suscriptor) {
        this.suscriptor = suscriptor;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }
}
