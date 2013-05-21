package com.palermotenis.model.service.clientes;

import java.util.List;

import com.palermotenis.model.beans.clientes.Cliente;
import com.palermotenis.model.beans.clientes.Direccion;
import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.model.dao.exceptions.PreexistingEntityException;

public interface ClienteService {

    Usuario registerCliente(String email, String password, String nombre, Direccion direccion, String telefono,
            boolean isSuscriptor) throws PreexistingEntityException;

    void createCliente(Cliente cliente);

    List<Cliente> getAllClientes();

    List<Cliente> getClientesByNombre(String nombre);

    List<Cliente> getClientesByEmail(String email);

}
