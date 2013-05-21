package com.palermotenis.controller.struts.actions.clientes;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.clientes.Direccion;
import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.model.dao.exceptions.PreexistingEntityException;
import com.palermotenis.model.service.clientes.ClienteService;
import com.palermotenis.model.service.usuarios.UsuarioService;

public class Registrar extends ActionSupport {

    private static final long serialVersionUID = 2099616862622522956L;

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
    private UsuarioService usuarioService;

    @Autowired
    private ClienteService clienteService;

    @Override
    public String execute() {
        try {
            usuario = clienteService.registerCliente(email, contrasenia, nombre, new Direccion(direccion, ciudad),
                telefono, suscriptor);
        } catch (PreexistingEntityException e) {
            return ERROR;
        }
        return SUCCESS;
    }

    @Override
    public void validate() {
        List<Usuario> usuarios = usuarioService.getUsuariosByUsername(email);
        if (CollectionUtils.isNotEmpty(usuarios)) {
            addFieldError("email", "Esta casilla de mail ya está registrada");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getRptContrasenia() {
        return rptContrasenia;
    }

    public void setRptContrasenia(String rptContrasenia) {
        this.rptContrasenia = rptContrasenia;
    }

    public boolean isSuscriptor() {
        return suscriptor;
    }

    public void setSuscriptor(boolean suscriptor) {
        this.suscriptor = suscriptor;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
