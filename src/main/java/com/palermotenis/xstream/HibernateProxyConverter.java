package com.palermotenis.xstream;

import org.hibernate.proxy.HibernateProxy;

import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

public class HibernateProxyConverter extends ReflectionConverter {

    private final ConverterLookup converterLookup;

    public HibernateProxyConverter(Mapper mapper, ReflectionProvider provider, ConverterLookup converterLookup) {
        super(mapper, provider);
        this.converterLookup = converterLookup;
    }

    /**
     * be responsible for hibernate proxy
     */
    @Override
    @SuppressWarnings("rawtypes")
    public boolean canConvert(Class clazz) {
        return HibernateProxy.class.isAssignableFrom(clazz);
    }

    @Override
    public void marshal(Object object, HierarchicalStreamWriter writer, MarshallingContext context) {
        Object item = ((HibernateProxy) object).getHibernateLazyInitializer().getImplementation();
        converterLookup.lookupConverterForType(item.getClass()).marshal(item, writer, context);
    }
}
