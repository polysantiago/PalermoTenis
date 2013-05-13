package com.palermotenis.model.service.atributos.tipos;

import java.util.List;

import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoClasificatorio;
import com.palermotenis.model.beans.valores.ValorPosible;

public interface TipoAtributoService {

    List<TipoAtributoClasificatorio> getAllTiposAtributosClasificatorios();

    TipoAtributo getTipoAtributoByNombre(String nombre);

    TipoAtributo getTipoAtributoById(Integer tipoAtributoId);

    void clearValoresPosibles(ValorPosible valorPosible);

}
