package com.palermotenis.model.dao.valores;

import java.util.Collection;
import java.util.List;

import com.palermotenis.model.beans.atributos.tipos.TipoAtributoClasificatorio;
import com.palermotenis.model.beans.valores.ValorClasificatorio;
import com.palermotenis.model.dao.Dao;

public interface ValorClasificatorioDao extends Dao<ValorClasificatorio, Integer> {

    List<ValorClasificatorio> getValoresClasificatoriosByTiposAtributo(
            Collection<TipoAtributoClasificatorio> tiposAtributosClasificatorios);

}
