package com.palermotenis.model.service.atributos.tipos;

import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import com.palermotenis.model.beans.valores.ValorPosible;

public interface TipoAtributoService {

    TipoAtributo getTipoAtributoById(Integer tipoAtributoId);

    void clearValoresPosibles(ValorPosible valorPosible);

}
