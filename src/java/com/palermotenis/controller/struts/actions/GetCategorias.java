/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Categoria;
import java.util.List;

/**
 *
 * @author Santiago
 */
public class GetCategorias extends ActionSupport {
    
    private GenericDao<Categoria, Integer> categoriaService;
    private List<Categoria> categorias;
    
    @Override
    public String execute() {
        categorias = categoriaService.findAll();
        return SUCCESS;   
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategoriaService(GenericDao<Categoria, Integer> categoriaService) {
        this.categoriaService = categoriaService;
    }    
}
