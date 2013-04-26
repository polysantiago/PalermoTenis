package com.palermotenis.model.dao.stock;

import org.springframework.stereotype.Repository;

import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.dao.AbstractHibernateDao;

@Repository("stockDao")
public class StockDaoHibernateImpl extends AbstractHibernateDao<Stock, Integer> implements StockDao {

}
