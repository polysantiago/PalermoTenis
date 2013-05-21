package com.palermotenis.model.dao.atributos.tipos;

import com.palermotenis.model.beans.atributos.tipos.TipoAtributoSimple;
import com.palermotenis.model.dao.Dao;

public interface TipoAtributoSimpleDao extends Dao<TipoAtributoSimple, Integer> {

    TipoAtributoSimple getTipoAtributoSimpleById(Integer tipoAtributoId);

}
