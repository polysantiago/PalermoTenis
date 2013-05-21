package com.palermotenis.model.service.clientes.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.authorities.Rol;
import com.palermotenis.model.beans.clientes.Cliente;
import com.palermotenis.model.beans.clientes.Direccion;
import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.model.dao.clientes.ClienteDao;
import com.palermotenis.model.dao.exceptions.PreexistingEntityException;
import com.palermotenis.model.service.authorities.RolService;
import com.palermotenis.model.service.clientes.ClienteService;
import com.palermotenis.model.service.newsletter.SuscriptorService;
import com.palermotenis.model.service.usuarios.UsuarioService;

@Service("clienteService")
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteDao clienteDao;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @Autowired
    private SuscriptorService suscriptorService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Usuario registerCliente(String email, String password, String nombre, Direccion direccion, String telefono,
            boolean isSuscriptor) throws PreexistingEntityException {

        Rol rol = rolService.getRol("ROLE_CLIENTE");
        Usuario usuario = usuarioService.createActive(email, password, rol);

        Cliente cliente = new Cliente(nombre, email, direccion, telefono);
        usuario.setCliente(cliente);

        createCliente(cliente);

        if (isSuscriptor) {
            suscriptorService.create(email, true);
        }

        return usuario;
    }

    @Override
    @Transactional
    public void createCliente(Cliente cliente) {
        clienteDao.create(cliente);
    }

    @Override
    public List<Cliente> getClientesByNombre(String nombre) {
        return clienteDao.query("Nombre", "nombre", nombre);
    }

    @Override
    public List<Cliente> getClientesByEmail(String email) {
        return clienteDao.queryBy("Email", "email", email);
    }

    @Override
    public List<Cliente> getAllClientes() {
        return clienteDao.findAll();
    }

}
