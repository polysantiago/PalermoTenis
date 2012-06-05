/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.model.hibernate.types;

import com.palermotenis.model.beans.atributos.AtributoTipado;
import com.palermotenis.model.beans.valores.Valor;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.beanutils.ConvertUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.impl.SessionImpl;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;
import com.palermotenis.model.exceptions.IllegalValueException;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.hibernate.Session;

/**
 *
 * @author Poly
 */
public class ValorType implements CompositeUserType {

    public static final String[] PROPERTY_NAMES = new String[]{"valor", "tipoAtributo"};
    public static final Type[] TYPES = new Type[]{Hibernate.STRING, Hibernate.entity(TipoAtributo.class)};

    static {
        ConvertUtils.register(new IntegerConverter(), Integer.class);
        ConvertUtils.register(new DoubleConverter(), Double.class);
    }

    public String[] getPropertyNames() {
        return PROPERTY_NAMES;
    }

    public Type[] getPropertyTypes() {
        return TYPES;
    }

    public Object getPropertyValue(Object o, int i) throws HibernateException {
        Valor v = (Valor) o;
        switch (i) {
            case 0:
                return ConvertUtils.convert(o);
            case 1:
                return v.getTipoAtributo();
            default:
                throw new HibernateException("Unsupported property index " + i);
        }
    }

    public void setPropertyValue(Object o, int i, Object value) throws HibernateException {
        Valor v = (Valor) o;

        switch (i) {
            case 0:
                v.setUnidad(value);
            case 1:
                v.setTipoAtributo((TipoAtributo) value);
            default:
                throw new HibernateException("Unsupported property index " + i);
        }
    }

    public Class returnedClass() {
        return Valor.class;
    }

    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y) {
            return true;
        }
        if (x == null || y == null) {
            return false;
        }

        Valor v1 = (Valor) x;
        Valor v2 = (Valor) y;

        if (v1.getUnidad() != v2.getUnidad()) {
            return false;
        }
        return true;
    }

    public int hashCode(Object arg0) throws HibernateException {
        return arg0.hashCode();
    }

    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object atributo)
            throws HibernateException, SQLException, IllegalValueException {


        SessionImpl s = (SessionImpl) session;

        String val = (String) Hibernate.STRING.nullSafeGet(rs, names[0]);
        Integer tpId = (Integer) Hibernate.INTEGER.nullSafeGet(rs, names[1]);

        Session tmpS = s.getFactory().openTemporarySession();
        TipoAtributo ta = (TipoAtributo) tmpS.load(TipoAtributo.class, tpId);

        if (!(atributo instanceof AtributoTipado)) {
            Valor valor = new Valor(ta);

            valor.setUnidad(ConvertUtils.convert(val, ta.getClase()));
            valor.setTipoAtributo(ta);
            return valor;
        } else {
            return null;
        }
    }

    public void nullSafeSet(PreparedStatement aPreparedStatement, Object value, int index, SessionImplementor aSessionImplementor)
            throws HibernateException, SQLException, IllegalValueException {
        Valor valor;
        String val = null;
        TipoAtributo t = null;
        if (value != null) {
            valor = (Valor) value;
            val = ConvertUtils.convert(valor.getUnidad());
            t = valor.getTipoAtributo();
        }
        Hibernate.STRING.nullSafeSet(aPreparedStatement, val, index);
        Hibernate.entity(TipoAtributo.class).nullSafeSet(aPreparedStatement, t, index + 1, aSessionImplementor);
    }

    public Object deepCopy(Object aObject) throws HibernateException {
        Valor valor = (Valor) aObject;
        Valor copy = null;
        if (valor != null) {
            copy = new Valor(valor.getTipoAtributo());
            copy.setUnidad(valor.getUnidad());
        }
        return copy;
    }

    public boolean isMutable() {
        return false;
    }

    public Serializable disassemble(Object value, SessionImplementor arg1) throws HibernateException {
        return (Serializable) deepCopy(value);
    }

    public Object assemble(Serializable cached, SessionImplementor arg1, Object arg2) throws HibernateException {
        return deepCopy(cached);
    }

    public Object replace(Object original, Object target, SessionImplementor aSessionImplementor, Object aObject2)
            throws HibernateException {
        return original;
    }

}
