package com.palermotenis.model.dao.atributos.tipos;

import com.palermotenis.model.beans.atributos.tipos.TipoAtributoMultipleValores;
import com.palermotenis.model.dao.Dao;

public interface TipoAtributoMultipleValoresDao extends Dao<TipoAtributoMultipleValores, Integer> {

    TipoAtributoMultipleValores getTipoAtributoMultipleValoresById(Integer tipoAtributoId);

}
