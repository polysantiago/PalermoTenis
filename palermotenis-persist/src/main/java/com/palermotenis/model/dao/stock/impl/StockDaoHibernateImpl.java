package com.palermotenis.model.dao.stock.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.dao.AbstractHibernateDao;
import com.palermotenis.model.dao.stock.StockDao;

@Repository("stockDao")
public class StockDaoHibernateImpl extends AbstractHibernateDao<Stock, Integer> implements StockDao {

    private static final String ORDER_BY_CLAUSE_START = " ORDER BY ";
    private static final Pattern BAD_QUERY_PATTERN = Pattern.compile("[^\\p{ASCII}]*");

    @Override
    public List<Stock> getAllStocks(int maxResults, int firstResult, Map<String, Boolean> orderBy) throws SQLException {
        return getNamedQueryOrderedBy("Stock.findAll", maxResults, firstResult, orderBy).getResultList();
    }

    @Override
    public List<Stock> getStocksByModelo(Modelo modelo, int maxResults, int firstResult, Map<String, Boolean> orderBy)
            throws SQLException {
        return getNamedQueryOrderedBy("Stock.findByModelo", maxResults, firstResult, orderBy).setParameter("modelo",
            modelo).getResultList();
    }

    @Override
    public List<Stock> getStocksByTipoProducto(TipoProducto tipoProducto, int maxResults, int firstResult,
            Map<String, Boolean> orderBy) throws SQLException {
        return getNamedQueryOrderedBy("Stock.findByTipoProducto", maxResults, firstResult, orderBy).setParameter(
            "tipoProducto", tipoProducto).getResultList();
    }

    @Override
    public List<Stock> getStocksByTipoProductoAndMarca(TipoProducto tipoProducto, Marca marca, int maxResults,
            int firstResult, Map<String, Boolean> orderBy) throws SQLException {
        return getNamedQueryOrderedBy("Stock.findByTipoProducto,Marca", maxResults, firstResult, orderBy)
            .setParameter("tipoProducto", tipoProducto)
            .setParameter("marca", marca)
            .getResultList();
    }

    private String getNamedQueryString(String queryName) throws SQLException {
        String queryString = getEntityManager()
            .createNamedQuery(queryName)
            .unwrap(org.hibernate.Query.class)
            .getQueryString();
        if (BAD_QUERY_PATTERN.matcher(queryString).matches()) {
            throw new SQLException("Bad query string");
        }
        return queryString;
    }

    private TypedQuery<Stock> getNamedQueryOrderedBy(String queryName, int maxResults, int firstResult,
            Map<String, Boolean> orderBy) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder(ORDER_BY_CLAUSE_START);

        List<String> clauses = Lists.newArrayList();
        for (Entry<String, Boolean> entry : orderBy.entrySet()) {
            String columnName = entry.getKey();
            Boolean ascending = entry.getValue();
            clauses.add(new StringBuilder(columnName).append(ascending ? " ASC" : " DESC").toString());
        }
        Joiner.on(", ").appendTo(stringBuilder, clauses);

        return getEntityManager()
            .createQuery(getNamedQueryString(queryName) + stringBuilder.toString(), Stock.class)
            .setMaxResults(maxResults)
            .setFirstResult(firstResult);
    }

}
