package com.palermotenis.model.hibernate.types;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.TypeHelper;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.impl.SessionImpl;
import org.hibernate.impl.TypeLocatorImpl;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.hibernate.type.TypeResolver;
import org.hibernate.usertype.CompositeUserType;

import com.palermotenis.model.beans.atributos.AtributoTipado;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoSimple;
import com.palermotenis.model.beans.valores.Valor;
import com.palermotenis.model.exceptions.IllegalValueException;

public class ValorType implements CompositeUserType {

    private static final TypeHelper TYPE_HELPER = new TypeLocatorImpl(new TypeResolver());

    private static final String[] PROPERTY_NAMES = new String[]
        { "valor", "tipoAtributo" };
    private static final Type[] TYPES = new Type[]
        { StandardBasicTypes.STRING, TYPE_HELPER.entity(TipoAtributoSimple.class) };

    static {
        ConvertUtils.register(new IntegerConverter(), Integer.class);
        ConvertUtils.register(new DoubleConverter(), Double.class);
    }

    @Override
    public String[] getPropertyNames() {
        return PROPERTY_NAMES;
    }

    @Override
    public Type[] getPropertyTypes() {
        return TYPES;
    }

    @Override
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

    @Override
    public void setPropertyValue(Object o, int i, Object value) throws HibernateException {
        Valor v = (Valor) o;

        switch (i) {
        case 0:
            v.setUnidad(value);
        case 1:
            v.setTipoAtributo((TipoAtributoSimple) value);
        default:
            throw new HibernateException("Unsupported property index " + i);
        }
    }

    @Override
    public Class<?> returnedClass() {
        return Valor.class;
    }

    @Override
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

    @Override
    public int hashCode(Object arg0) throws HibernateException {
        return arg0.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object atributo)
            throws HibernateException, SQLException, IllegalValueException {

        SessionImpl s = (SessionImpl) session;

        String val = StandardBasicTypes.STRING.nullSafeGet(rs, names[0], session);
        Integer tpId = StandardBasicTypes.INTEGER.nullSafeGet(rs, names[1], session);

        Session tmpS = s.getFactory().openTemporarySession();
        TipoAtributoSimple ta = (TipoAtributoSimple) tmpS.load(TipoAtributoSimple.class, tpId);

        if (!(atributo instanceof AtributoTipado)) {
            Valor valor = new Valor(ta);

            valor.setUnidad(ConvertUtils.convert(val, ta.getClase()));
            valor.setTipoAtributo(ta);
            return valor;
        }
        return null;
    }

    @Override
    public void nullSafeSet(PreparedStatement aPreparedStatement, Object value, int index,
            SessionImplementor aSessionImplementor) throws HibernateException, SQLException, IllegalValueException {
        Valor valor;
        String val = null;
        TipoAtributoSimple tipoAtributo = null;
        if (value != null) {
            valor = (Valor) value;
            val = ConvertUtils.convert(valor.getUnidad());
            tipoAtributo = valor.getTipoAtributo();
        }
        StandardBasicTypes.STRING.nullSafeSet(aPreparedStatement, val, index, aSessionImplementor);
        TYPE_HELPER.entity(TipoAtributoSimple.class).nullSafeSet(aPreparedStatement, tipoAtributo, index + 1,
            aSessionImplementor);
    }

    @Override
    public Object deepCopy(Object aObject) throws HibernateException {
        Valor valor = (Valor) aObject;
        Valor copy = null;
        if (valor != null) {
            copy = new Valor(valor.getTipoAtributo());
            copy.setUnidad(valor.getUnidad());
        }
        return copy;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value, SessionImplementor arg1) throws HibernateException {
        return (Serializable) deepCopy(value);
    }

    @Override
    public Object assemble(Serializable cached, SessionImplementor arg1, Object arg2) throws HibernateException {
        return deepCopy(cached);
    }

    @Override
    public Object replace(Object original, Object target, SessionImplementor aSessionImplementor, Object aObject2)
            throws HibernateException {
        return original;
    }

}
