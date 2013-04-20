/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.model.hibernate.types;

import com.mysql.jdbc.PreparedStatement;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoTipado;
import com.palermotenis.model.beans.valores.Valor;
import com.palermotenis.model.exceptions.IllegalValueException;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.beanutils.ConvertUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.impl.SessionImpl;
import org.hibernate.type.Type;

/**
 *
 * @author Poly
 */
public class ValorPosibleType extends ValorType {

    public static final Type[] POSSIBLE_TYPES = new Type[]{Hibernate.STRING, Hibernate.entity(TipoAtributoTipado.class)};

    @Override
    public Type[] getPropertyTypes() {
        return POSSIBLE_TYPES;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object atributo)
            throws HibernateException, SQLException, IllegalValueException {


        SessionImpl s = (SessionImpl) session;

        String val = (String) Hibernate.STRING.nullSafeGet(rs, names[0]);
        Integer tpId = (Integer) Hibernate.INTEGER.nullSafeGet(rs, names[1]);

        TipoAtributoTipado ta = (TipoAtributoTipado) s.get(TipoAtributo.class, tpId);

        Valor valor = new Valor(ta);

        valor.setUnidad(ConvertUtils.convert(val, ta.getClase()));
        valor.setTipoAtributo(ta);

        return valor;
    }

    public void nullSafeSet(PreparedStatement aPreparedStatement, Object value, int index, SessionImplementor aSessionImplementor)
            throws HibernateException, SQLException, IllegalValueException {
        Valor valor;
        if (value == null) {
            throw new IllegalValueException("El valor no puede ser nulo");
        } else {
            valor = (Valor) value;
        }

        String val = ConvertUtils.convert(valor);
        Hibernate.STRING.nullSafeSet(aPreparedStatement, val, index);
        Hibernate.entity(TipoAtributoTipado.class).nullSafeSet(aPreparedStatement, valor.getTipoAtributo(), index + 1, aSessionImplementor);
    }
}
