package com.palermotenis.model.dao.atributos.tipos;

import com.palermotenis.model.beans.atributos.tipos.TipoAtributoClasificatorio;
import com.palermotenis.model.dao.Dao;

public interface TipoAtributoClasificatorioDao extends Dao<TipoAtributoClasificatorio, Integer> {

    TipoAtributoClasificatorio getTipoAtributoClasificatorioById(Integer tipoAtributoId);

}
