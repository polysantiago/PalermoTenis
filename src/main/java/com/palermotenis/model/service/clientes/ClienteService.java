package com.palermotenis.model.service.clientes;

import java.util.List;

import com.palermotenis.model.beans.clientes.Cliente;

public interface ClienteService {

    void createCliente(Cliente cliente);

    List<Cliente> getClientesByEmail(String email);

}
