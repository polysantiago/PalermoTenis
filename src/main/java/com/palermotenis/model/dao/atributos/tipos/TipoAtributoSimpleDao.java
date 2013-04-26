package com.palermotenis.model.dao.atributos.tipos;

import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import com.palermotenis.model.dao.Dao;

public interface TipoAtributoSimpleDao extends Dao<TipoAtributo, Integer> {

    TipoAtributo getTipoAtributoSimpleById(Integer tipoAtributoId);

}
