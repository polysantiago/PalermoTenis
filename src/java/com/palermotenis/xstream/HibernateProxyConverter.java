/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.xstream;

import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import org.hibernate.proxy.HibernateProxy;

/**
 *
 * @author poly
 */
public class HibernateProxyConverter extends ReflectionConverter {

    private ConverterLookup converterLookup;

    public HibernateProxyConverter(Mapper mapper, ReflectionProvider provider, ConverterLookup converterLookup) {
        super(mapper, provider);
        this.converterLookup = converterLookup;
    }

    /**
     * be responsible for hibernate proxy
     */
    @Override
    public boolean canConvert(Class clazz) {
        return HibernateProxy.class.isAssignableFrom(clazz);
    }

    @Override
    public void marshal(Object object, HierarchicalStreamWriter writer, MarshallingContext context) {
        Object item = ((HibernateProxy) object).getHibernateLazyInitializer().getImplementation();
        converterLookup.lookupConverterForType(item.getClass()).marshal(item, writer, context);
    }
}
