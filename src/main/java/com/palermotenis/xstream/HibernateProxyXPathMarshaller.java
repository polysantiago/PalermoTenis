/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.xstream;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.core.ReferenceByXPathMarshaller;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import org.hibernate.proxy.HibernateProxy;

/**
 *
 * @author poly
 */
public class HibernateProxyXPathMarshaller extends ReferenceByXPathMarshaller {

    public HibernateProxyXPathMarshaller(HierarchicalStreamWriter writer, ConverterLookup converterLookup, Mapper mapper, int mode) {
        super(writer, converterLookup, mapper, mode);
    }

    @Override
    public void convertAnother(Object item, Converter converter) {
        Object toConvert;
        if (HibernateProxy.class.isAssignableFrom(item.getClass())) {
            toConvert = ((HibernateProxy) item).getHibernateLazyInitializer().getImplementation();
        } else {
            toConvert = item;
        }
        super.convertAnother(toConvert, converter);
    }
}
