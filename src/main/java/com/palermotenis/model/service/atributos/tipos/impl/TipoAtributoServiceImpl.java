package com.palermotenis.model.service.atributos.tipos.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoTipado;
import com.palermotenis.model.beans.valores.ValorPosible;
import com.palermotenis.model.dao.atributos.tipos.TipoAtributoSimpleDao;
import com.palermotenis.model.dao.atributos.tipos.TipoAtributoTipadoDao;
import com.palermotenis.model.service.atributos.tipos.TipoAtributoService;

@Service("tipoAtributoService")
@Transactional(propagation = Propagation.REQUIRED)
public class TipoAtributoServiceImpl implements TipoAtributoService {

    @Autowired
    private TipoAtributoSimpleDao tipoAtributoSimpleDao;

    @Autowired
    private TipoAtributoTipadoDao tipoAtributoTipadoDao;

    @Override
    public TipoAtributo getTipoAtributoById(Integer tipoAtributoId) {
        return tipoAtributoSimpleDao.getTipoAtributoSimpleById(tipoAtributoId);
    }

    @Override
    public void clearValoresPosibles(ValorPosible valorPosible) {
        TipoAtributoTipado tipoAtributo = valorPosible.getTipoAtributo();
        if (tipoAtributo != null) {
            tipoAtributo.getValoresPosibles().remove(valorPosible);
            tipoAtributoTipadoDao.edit(tipoAtributo);
        }

    }

}
