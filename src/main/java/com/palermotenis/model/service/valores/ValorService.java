package com.palermotenis.model.service.valores;

import java.util.Collection;
import java.util.List;

import com.palermotenis.model.beans.atributos.tipos.TipoAtributoClasificatorio;
import com.palermotenis.model.beans.valores.ValorClasificatorio;
import com.palermotenis.model.beans.valores.ValorPosible;

public interface ValorService {

    void createNewValor(Integer tipoAtributoTipadoId, String unidad);

    void updateValor(Integer valorPosibleId, String unidad);

    void deleteValor(Integer valorPosibleId);

    ValorPosible getValorPosibleById(Integer valorPosibleId);

    ValorClasificatorio getValorClasificatorioById(Integer valorClasificatorioId);

    List<ValorPosible> getValoresPosiblesByTipo(Integer tipoAtributoTipadoId);

    List<ValorClasificatorio> getValoresClasificatoriosByTipos(
            Collection<TipoAtributoClasificatorio> tiposAtributosClasificatorios);

}
