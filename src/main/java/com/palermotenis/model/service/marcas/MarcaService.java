package com.palermotenis.model.service.marcas;

import java.util.List;

import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;

public interface MarcaService {

    List<Marca> getAllMarcas();

    List<Marca> getActiveMarcasByTipoProducto(TipoProducto tipoProducto);

    Marca getMarcaById(Integer marcaId);

}
