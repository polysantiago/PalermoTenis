package com.palermotenis.model.dao.atributos.tipos;

import com.palermotenis.model.beans.atributos.tipos.TipoAtributoTipado;
import com.palermotenis.model.dao.Dao;

public interface TipoAtributoTipadoDao extends Dao<TipoAtributoTipado, Integer> {

    TipoAtributoTipado getTipoAtributoTipadoById(Integer tipoAtributoTipadoId);

}
