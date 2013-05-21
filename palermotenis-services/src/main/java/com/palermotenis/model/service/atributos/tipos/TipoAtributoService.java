package com.palermotenis.model.service.atributos.tipos;

import java.util.List;

import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoClasificatorio;
import com.palermotenis.model.beans.atributos.tipos.clasif.TipoAtributoClasif;
import com.palermotenis.model.beans.valores.ValorPosible;

public interface TipoAtributoService {

    void createNewTipoAtributo(Integer tipoProductoId, String nombre, String clase, Integer unidadId, Character metadata);

    void updateTipoAtributo(Integer tipoAtributoId, String nombre);

    void updateTipoAtributo(Integer tipoAtributoId, Integer unidadId, String clase, Character metadata);

    void deleteTipoAtributo(Integer tipoAtributoId);

    void clearValoresPosibles(ValorPosible valorPosible);

    TipoAtributo getTipoAtributoByNombre(String nombre);

    TipoAtributo getTipoAtributoById(Integer tipoAtributoId);

    List<TipoAtributo> getTiposAtributosByTipoProducto(Integer tipoProductoId);

    List<TipoAtributoClasificatorio> getAllTiposAtributosClasificatorios();

    List<TipoAtributoClasif> getTipoAtributoClasificatorioMetadata();

}
