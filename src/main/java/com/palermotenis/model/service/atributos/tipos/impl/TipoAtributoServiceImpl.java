package com.palermotenis.model.service.atributos.tipos.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoClasificatorio;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoTipado;
import com.palermotenis.model.beans.valores.ValorPosible;
import com.palermotenis.model.dao.atributos.tipos.TipoAtributoClasificatorioDao;
import com.palermotenis.model.dao.atributos.tipos.TipoAtributoDao;
import com.palermotenis.model.dao.atributos.tipos.TipoAtributoMultipleValoresDao;
import com.palermotenis.model.dao.atributos.tipos.TipoAtributoSimpleDao;
import com.palermotenis.model.dao.atributos.tipos.TipoAtributoTipadoDao;
import com.palermotenis.model.service.atributos.tipos.TipoAtributoService;

@Service("tipoAtributoService")
@Transactional(propagation = Propagation.REQUIRED, noRollbackFor =
    { NoResultException.class, EntityNotFoundException.class })
public class TipoAtributoServiceImpl implements TipoAtributoService {

    @Autowired
    private TipoAtributoDao tipoAtributoDao;

    @Autowired
    private TipoAtributoSimpleDao tipoAtributoSimpleDao;

    @Autowired
    private TipoAtributoTipadoDao tipoAtributoTipadoDao;

    @Autowired
    private TipoAtributoClasificatorioDao tipoAtributoClasificatorioDao;

    @Autowired
    private TipoAtributoMultipleValoresDao tipoAtributoMultipleValoresDao;

    @Override
    public TipoAtributo getTipoAtributoById(Integer tipoAtributoId) {
        return tipoAtributoDao.find(tipoAtributoId);
    }

    @Override
    public TipoAtributo getTipoAtributoByNombre(String nombre) throws NoResultException {
        return tipoAtributoDao.findBy("Nombre", "nombre", nombre);
    }

    @Override
    public void clearValoresPosibles(ValorPosible valorPosible) {
        TipoAtributoTipado tipoAtributo = valorPosible.getTipoAtributo();
        if (tipoAtributo != null) {
            tipoAtributo.getValoresPosibles().remove(valorPosible);
            tipoAtributoTipadoDao.edit(tipoAtributo);
        }

    }

    @Override
    public List<TipoAtributoClasificatorio> getAllTiposAtributosClasificatorios() {
        return tipoAtributoClasificatorioDao.findAll();
    }

}
