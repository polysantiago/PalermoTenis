package com.palermotenis.model.service.sucursales;

import java.util.List;

import com.palermotenis.model.beans.Sucursal;

public interface SucursalService {

    List<Sucursal> getAllSucursales();

    void createNewSucursal(String nombre, String direccion, String telefono);

    void updateSucursal(Integer sucursalId, String nombre, String direccion, String telefono);

    void deleteSucursal(Integer sucursalId);

}
