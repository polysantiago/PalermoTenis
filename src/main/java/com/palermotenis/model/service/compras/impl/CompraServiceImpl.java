package com.palermotenis.model.service.compras.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.compras.Compra;
import com.palermotenis.model.beans.compras.ProductoCompra;
import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.model.dao.compras.CompraDao;
import com.palermotenis.model.service.compras.CompraService;

@Service("compraService")
public class CompraServiceImpl implements CompraService {

    @Autowired
    private CompraDao compraDao;

    @Override
    @Transactional
    public void registerNewPurchase(Usuario usuario, List<ProductoCompra> productoCompras) {
        Compra compra = new Compra(usuario);

        for (ProductoCompra productoCompra : productoCompras) {
            compra.addProductoCompra(productoCompra);
        }

        compraDao.create(compra);
    }

}
