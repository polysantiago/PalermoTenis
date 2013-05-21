package com.palermotenis.commandline.setup;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.google.common.collect.Lists;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoSimple;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoClasificatorio;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoMultipleValores;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoTipado;
import com.palermotenis.model.beans.atributos.tipos.clasif.TipoAtributoClasif;
import com.palermotenis.model.dao.atributos.tipos.TipoAtributoClasificatorioMetadataDao;

public class TipoAtributoClasificatorioMetadataMasterData implements MasterData {

    private static final TipoAtributoClasif C = new TipoAtributoClasif(
        'C',
        "Clasificatorio",
        TipoAtributoClasificatorio.class.getSimpleName(),
        "Son los más complejos. También una extensión de  tipado, se utilizan para diferenciar los productos entre sí aunque esto no quiera decir que cada diferenciación representa un nuevo producto (ergo, no tienen un precio diferente). Físicamente, cada producto con un valor clasificatorio diferente, significa un producto físico diferente y esto hace que sean diferenciados también al momento de contabilizar el stock de ese producto. El más claro ejemplo, es el talle en el calzado. Cada calzado tiene el mismo valor y las mismas características, pero si varía su talle, el stock para ese determinado talle debe contabilizarse aparte de otros calzados con diferente talle; a su vez, el cliente deberá elegir el talle de calzado que desea antes de efectuar una compra o un pedido. Los valores clasificatorios deben ser seleccionados de una lista de valores posibles.");
    private static final TipoAtributoClasif M = new TipoAtributoClasif(
        'M',
        "Múltiples Valores",
        TipoAtributoMultipleValores.class.getSimpleName(),
        "Son una extensión de tipado con la única diferencia que pueden seleccionarse más de un valor de la lista de valores posibles.");
    private static final TipoAtributoClasif S = new TipoAtributoClasif(
        'S',
        "Simple",
        TipoAtributoSimple.class.getSimpleName(),
        "Se utiliza para valores simples, como pueden ser números o texto variable (no son fijos ni repetitivos) y también para los valores binarios (verdadero o falso).");
    private static final TipoAtributoClasif T = new TipoAtributoClasif(
        'T',
        "Tipado",
        TipoAtributoTipado.class.getSimpleName(),
        "Se utiliza para valores que deben ser seleccionados de una lista. No puede seleccionarse otro valor que no esté contenido en la lista de valores posibles y sólo puede seleccionarse UN valor de la lista. También se puede interpretar como tipificado.");

    private static final List<TipoAtributoClasif> ALL_TIPOS_ATRIBUTOS_CLASIF = Lists.newArrayList();

    static {
        ALL_TIPOS_ATRIBUTOS_CLASIF.add(C);
        ALL_TIPOS_ATRIBUTOS_CLASIF.add(M);
        ALL_TIPOS_ATRIBUTOS_CLASIF.add(S);
        ALL_TIPOS_ATRIBUTOS_CLASIF.add(T);
    }

    private final TipoAtributoClasificatorioMetadataDao tipoAtributoClasificatorioMetadataDao;

    public TipoAtributoClasificatorioMetadataMasterData(
            TipoAtributoClasificatorioMetadataDao tipoAtributoClasificatorioMetadataDao) {
        this.tipoAtributoClasificatorioMetadataDao = tipoAtributoClasificatorioMetadataDao;
    }

    @Override
    public void createOrUpdate() {
        for (TipoAtributoClasif tipoAtributoClasif : ALL_TIPOS_ATRIBUTOS_CLASIF) {
            try {
                TipoAtributoClasif dbInstance = tipoAtributoClasificatorioMetadataDao.load(tipoAtributoClasif
                    .getClasif());
                dbInstance.setNombre(tipoAtributoClasif.getNombre());
                dbInstance.setClase(tipoAtributoClasif.getClase());
                dbInstance.setDescripcion(tipoAtributoClasif.getDescripcion());
                tipoAtributoClasificatorioMetadataDao.edit(dbInstance);
            } catch (EntityNotFoundException ex) {
                tipoAtributoClasificatorioMetadataDao.create(tipoAtributoClasif);
            }
        }
    }

}
