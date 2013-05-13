package com.palermotenis.xstream;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.springframework.oxm.MarshallingFailureException;
import org.springframework.oxm.UncategorizedMappingException;
import org.springframework.oxm.UnmarshallingFailureException;
import org.springframework.oxm.XmlMappingException;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.StaxUtils;
import org.xml.sax.ContentHandler;
import org.xml.sax.ext.LexicalHandler;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.ConverterMatcher;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.thoughtworks.xstream.io.xml.QNameMap;
import com.thoughtworks.xstream.io.xml.SaxWriter;
import com.thoughtworks.xstream.io.xml.StaxWriter;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public class XStreamMarshaller extends XStream {

    /**
     * The default encoding used for stream access: UTF-8.
     */
    public static final String DEFAULT_ENCODING = "UTF-8";
    private HierarchicalStreamDriver streamDriver;
    private String encoding = DEFAULT_ENCODING;
    private Class<?>[] supportedClasses;
    private ClassLoader classLoader;

    @Override
    protected MapperWrapper wrapMapper(MapperWrapper mapperWrapper) {
        return new HibernateMapper(mapperWrapper);
    }

    protected void marshalXmlEventWriter(Object graph, XMLEventWriter eventWriter) throws XmlMappingException {
        ContentHandler contentHandler = StaxUtils.createContentHandler(eventWriter);
        marshalSaxHandlers(graph, contentHandler, null);
    }

    protected void marshalXmlStreamWriter(Object graph, XMLStreamWriter streamWriter) throws XmlMappingException {
        try {
            marshal(graph, new StaxWriter(new QNameMap(), streamWriter));
        } catch (XMLStreamException ex) {
            throw convertXStreamException(ex, true);
        }
    }

    protected void marshalOutputStream(Object graph, OutputStream outputStream) throws XmlMappingException, IOException {
        marshalWriter(graph, new OutputStreamWriter(outputStream, this.encoding));
    }

    protected void marshalSaxHandlers(Object graph, ContentHandler contentHandler, LexicalHandler lexicalHandler)
            throws XmlMappingException {

        SaxWriter saxWriter = new SaxWriter();
        saxWriter.setContentHandler(contentHandler);
        marshal(graph, saxWriter);
    }

    protected void marshalWriter(Object graph, Writer writer) throws XmlMappingException, IOException {
        if (streamDriver != null) {
            marshal(graph, streamDriver.createWriter(writer));
        } else {
            marshal(graph, new CompactWriter(writer));
        }
    }

    public boolean supports(Class<?> clazz) {
        if (ObjectUtils.isEmpty(this.supportedClasses)) {
            return true;
        } else {
            for (Class<?> supportedClass : this.supportedClasses) {
                if (supportedClass.isAssignableFrom(clazz)) {
                    return true;
                }
            }
            return false;
        }
    }

    protected XmlMappingException convertXStreamException(Exception ex, boolean marshalling) {
        if (ex instanceof StreamException || ex instanceof CannotResolveClassException
                || ex instanceof ConversionException) {
            if (marshalling) {
                return new MarshallingFailureException("XStream marshalling exception", ex);
            } else {
                return new UnmarshallingFailureException("XStream unmarshalling exception", ex);
            }
        } else {
            // fallback
            return new UncategorizedMappingException("Unknown XStream exception", ex);
        }
    }

    public void setConverters(ConverterMatcher[] converters) {
        for (int i = 0; i < converters.length; i++) {
            if (converters[i] instanceof Converter) {
                registerConverter((Converter) converters[i], i);
            } else if (converters[i] instanceof SingleValueConverter) {
                registerConverter((SingleValueConverter) converters[i], i);
            } else {
                throw new IllegalArgumentException("Invalid ConverterMatcher [" + converters[i] + "]");
            }
        }
    }

    public void setAliases(Map<String, ?> aliases) throws ClassNotFoundException {
        for (Map.Entry<String, ?> entry : aliases.entrySet()) {
            String alias = entry.getKey();
            Object value = entry.getValue();
            Class<?> type;
            if (value instanceof Class) {
                type = (Class<?>) value;
            } else if (value instanceof String) {
                String s = (String) value;
                type = ClassUtils.forName(s, classLoader);
            } else {
                throw new IllegalArgumentException("Unknown value [" + value + "], expected String or Class");
            }
            alias(alias, type);
        }
    }

    public void setFieldAliases(Map<String, String> aliases) throws ClassNotFoundException, NoSuchFieldException {
        for (Map.Entry<String, String> entry : aliases.entrySet()) {
            String alias = entry.getValue();
            String field = entry.getKey();
            int idx = field.lastIndexOf('.');
            if (idx != -1) {
                String className = field.substring(0, idx);
                Class<?> clazz = ClassUtils.forName(className, classLoader);
                String fieldName = field.substring(idx + 1);
                aliasField(alias, clazz, fieldName);
            } else {
                throw new IllegalArgumentException("Field name [" + field + "] does not contain '.'");
            }
        }
    }

    public void setOmittedFields(Map<Class<?>, String> omittedFields) {
        for (Map.Entry<Class<?>, String> entry : omittedFields.entrySet()) {
            String[] fields = StringUtils.commaDelimitedListToStringArray(entry.getValue());
            for (String field : fields) {
                omitField(entry.getKey(), field);
            }
        }
    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public void setStreamDriver(HierarchicalStreamDriver streamDriver) {
        this.streamDriver = streamDriver;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setSupportedClasses(Class<?>[] supportedClasses) {
        this.supportedClasses = supportedClasses;
    }
}
