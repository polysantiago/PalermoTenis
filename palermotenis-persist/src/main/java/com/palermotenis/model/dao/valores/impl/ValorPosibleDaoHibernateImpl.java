package com.palermotenis.model.dao.valores.impl;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.valores.ValorPosible;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.valores.ValorPosibleDao;

@Repository("valorPosibleDao")
public class ValorPosibleDaoHibernateImpl extends AbstractHibernateDao<ValorPosible, Integer> implements
    ValorPosibleDao {

}
