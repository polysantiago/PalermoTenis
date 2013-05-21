package com.palermotenis.support;

import com.palermotenis.model.beans.clientes.Cliente;

public interface TestClienteService extends TestService<Cliente> {

    Cliente create(boolean isSuscriptor);

}
