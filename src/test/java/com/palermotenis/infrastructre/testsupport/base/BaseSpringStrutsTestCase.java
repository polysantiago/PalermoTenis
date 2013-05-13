package com.palermotenis.infrastructre.testsupport.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.StrutsSpringJUnit4TestCase;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.opensymphony.xwork2.ActionSupport;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value =
    { "classpath:/spring/applicationContext-business.xml", "classpath:/spring/test-auxiliary-services.xml" })
public abstract class BaseSpringStrutsTestCase<T extends ActionSupport> extends StrutsSpringJUnit4TestCase<T> {

    private static final String DEFAULT_NAMESPACE = "/";

    @Test
    public void testActionMapping() {
        ActionMapping mapping = getActionMapping(getActionUrl());
        assertNotNull(mapping);
        assertEquals(getActionNamespace(), mapping.getNamespace());
        assertEquals(getActionName(), mapping.getName());
    }

    protected String getActionUrl() {
        if (StringUtils.equals(DEFAULT_NAMESPACE, getActionNamespace())) {
            return getActionNamespace() + getActionName();
        }
        return getActionNamespace() + DEFAULT_NAMESPACE + getActionName();
    }

    protected String getActionNamespace() {
        return DEFAULT_NAMESPACE;
    }

    protected String getActionName() {
        return getAction().getClass().getSimpleName();
    }

}
