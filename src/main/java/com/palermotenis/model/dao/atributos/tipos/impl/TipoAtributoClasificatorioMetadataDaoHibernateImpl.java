package com.palermotenis.model.dao.atributos.tipos.impl;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.atributos.tipos.clasif.TipoAtributoClasif;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.atributos.tipos.TipoAtributoClasificatorioMetadataDao;

@Repository("tipoAtributoClasificatorioMetadaDao")
public class TipoAtributoClasificatorioMetadataDaoHibernateImpl extends
    AbstractHibernateDao<TipoAtributoClasif, Character> implements TipoAtributoClasificatorioMetadataDao {

}
