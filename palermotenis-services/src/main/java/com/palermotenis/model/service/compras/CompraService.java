package com.palermotenis.model.service.compras;

import java.util.List;

import com.palermotenis.model.beans.usuarios.Usuario;

public interface CompraService {

    void registerNewPurchase(Usuario usuario, List<List<? extends Number>> stocks);

}
