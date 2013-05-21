package com.palermotenis.model.service.proveedores;

import java.util.List;

import com.palermotenis.model.beans.proveedores.Proveedor;

public interface ProveedorService {

    void createNewProveedor(String nombre);

    void updateProveedor(Integer proveedorId, String nombre);

    void deleteProveedor(Integer proveedorId);

    Proveedor getProveedorById(Integer proveedorId);

    List<Proveedor> getAllProveedores();

}
