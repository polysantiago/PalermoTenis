/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.model.ws;

/**
 *
 * @author Poly
 */
import javax.xml.rpc.Stub;
import org.springframework.remoting.jaxrpc.JaxRpcPortProxyFactoryBean;

public class MyJaxRpcPortProxyFactoryBean extends JaxRpcPortProxyFactoryBean {

    private static final String TIMEOUT_PROPERTY_KEY = "axis.connection.timeout";

    @Override
    protected void preparePortStub(Stub stub) {
        super.preparePortStub(stub);
        stub._setProperty(TIMEOUT_PROPERTY_KEY, new Integer(30000));
    }
}
