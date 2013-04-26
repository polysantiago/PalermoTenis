package com.palermotenis.model.dao.sucursales;

import java.util.List;

import com.palermotenis.model.beans.Sucursal;
import com.palermotenis.model.dao.Dao;

public interface SucursalDao extends Dao<Sucursal, Integer> {

    List<Sucursal> getAllSucursales();

}
