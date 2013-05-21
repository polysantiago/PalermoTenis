package com.palermotenis.controller.struts.actions.admin.crud;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.infrastructure.testsupport.base.BaseSpringStrutsTestCase;

public class PrecioActionTest extends BaseSpringStrutsTestCase<PrecioAction> {

    @Test
    @Transactional
    public void testCreate() {
        fail("Not yet implemented");
    }

    @Test
    @Transactional
    public void testEdit() {
        fail("Not yet implemented");
    }

    @Test
    @Transactional
    public void testDestroy() {
        fail("Not yet implemented");
    }

    @Test
    @Transactional
    public void testMove() {
        fail("Not yet implemented");
    }

    @Override
    protected String getActionName() {
        return "PrecioAction_*";
    }

    @Override
    protected String getActionNamespace() {
        return "/admin/crud";
    }

}
