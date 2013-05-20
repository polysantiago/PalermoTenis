package com.palermotenis.model.service.atributos.tipos.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.palermotenis.model.beans.Unidad;
import com.palermotenis.model.beans.atributos.Atributo;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoClasificatorio;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoMultipleValores;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoSimple;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoTipado;
import com.palermotenis.model.beans.atributos.tipos.clasif.TipoAtributoClasif;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.beans.valores.ValorPosible;
import com.palermotenis.model.dao.atributos.tipos.TipoAtributoClasificatorioDao;
import com.palermotenis.model.dao.atributos.tipos.TipoAtributoClasificatorioMetadataDao;
import com.palermotenis.model.dao.atributos.tipos.TipoAtributoDao;
import com.palermotenis.model.dao.atributos.tipos.TipoAtributoMultipleValoresDao;
import com.palermotenis.model.dao.atributos.tipos.TipoAtributoSimpleDao;
import com.palermotenis.model.dao.atributos.tipos.TipoAtributoTipadoDao;
import com.palermotenis.model.service.atributos.tipos.TipoAtributoService;
import com.palermotenis.model.service.productos.tipos.TipoProductoService;
import com.palermotenis.model.service.unidades.UnidadService;

@Service("tipoAtributoService")
@Transactional(propagation = Propagation.REQUIRED, noRollbackFor =
    { NoResultException.class, EntityNotFoundException.class })
public class TipoAtributoServiceImpl implements TipoAtributoService {

    @Autowired
    private TipoProductoService tipoProductoService;

    @Autowired
    private UnidadService unidadService;

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

    @Autowired
    private TipoAtributoClasificatorioMetadataDao tipoAtributoClasificatorioMetadataDao;

    @Override
    public void createNewTipoAtributo(Integer tipoProductoId, String nombre, String clase, Integer unidadId,
            Character metadata) {
        Assert.notNull(metadata, "Metadata information cannot be null");
        TipoProducto tipoProducto = tipoProductoService.getTipoProductoById(tipoProductoId);

        TipoAtributo tipoAtributo = null;

        switch (metadata) {
        case 'S':
            tipoAtributo = new TipoAtributoSimple();
            break;
        case 'T':
            tipoAtributo = new TipoAtributoTipado();
            break;
        case 'C':
            tipoAtributo = new TipoAtributoClasificatorio();
            break;
        case 'M':
            tipoAtributo = new TipoAtributoMultipleValores();
            break;
        default:
            throw new RuntimeException("Unknown metadata character : " + metadata);
        }

        tipoAtributo.setTipoProducto(tipoProducto);
        tipoAtributo.setNombre(nombre);

        try {
            tipoAtributo.setClase(Class.forName(clase));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Invalid class name for " + tipoAtributo + " : " + clase, e);
        }

        if (unidadId != null) {
            Unidad unidad = getUnidad(unidadId);
            tipoAtributo.setUnidad(unidad);
        }

        tipoAtributoDao.create(tipoAtributo);
    }

    @Override
    public void updateTipoAtributo(Integer tipoAtributoId, String nombre) {
        TipoAtributo tipoAtributo = getTipoAtributoById(tipoAtributoId);
        tipoAtributo.setNombre(nombre);
        tipoAtributoDao.edit(tipoAtributo);
    }

    @Override
    public void updateTipoAtributo(Integer tipoAtributoId, Integer unidadId, String clase, Character metadata) {
        TipoAtributoSimple tipoAtributo = (TipoAtributoSimple) getTipoAtributoById(tipoAtributoId);
        Unidad unidad = null;

        if (unidadId != null) {
            unidad = getUnidad(unidadId);
        }

        if (tipoAtributo.getClase() == Double.class && clase.equals("java.lang.Integer")) {
            for (Atributo atributo : tipoAtributo.getAtributos()) {
                if (atributo.getValor() != null && atributo.getValor().getUnidad() != null) {
                    int i = (int) ((Double) atributo.getValor().getUnidad()).doubleValue();
                    atributo.getValor().setUnidad(Integer.valueOf(i));
                }
            }
        }

        tipoAtributo.setUnidad(unidad);

        try {
            tipoAtributo.setClase(Class.forName(clase));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Invalid class name for " + tipoAtributo + " : " + clase, e);
        }

        if (tipoAtributo.getTipo() != metadata) {
            tipoAtributoDao.updateTipoAtributoClass(metadata, tipoAtributo);
        }

        tipoAtributoDao.edit(tipoAtributo);
    }

    @Override
    public void deleteTipoAtributo(Integer tipoAtributoId) {
        TipoAtributo tipoAtributo = getTipoAtributoById(tipoAtributoId);
        tipoAtributoDao.destroy(tipoAtributo);
    }

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

    @Override
    public List<TipoAtributo> getTiposAtributosByTipoProducto(Integer tipoProductoId) {
        TipoProducto tipoProducto = tipoProductoService.getTipoProductoById(tipoProductoId);
        return tipoAtributoDao.getTiposAtributosByTipoProducto(tipoProducto);
    }

    @Override
    public List<TipoAtributoClasif> getTipoAtributoClasificatorioMetadata() {
        return tipoAtributoClasificatorioMetadataDao.findAll();
    }

    private Unidad getUnidad(Integer unidadId) {
        return unidadService.getUnidadById(unidadId);
    }

}
