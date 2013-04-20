/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.xstream;

import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.core.ReferenceByXPathMarshallingStrategy;
import com.thoughtworks.xstream.core.TreeMarshaller;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

/**
 *
 * @author poly
 */
public class XStreamMarshallingStrategy extends ReferenceByXPathMarshallingStrategy {

    public XStreamMarshallingStrategy(int mode) {
        super(mode);
    }

    protected TreeMarshaller createMarshallingContext(HierarchicalStreamWriter writer, ConverterLookup converterLookup, Mapper mapper) {
        return new HibernateProxyXPathMarshaller(writer, converterLookup, mapper, RELATIVE);
    }
}
