package com.palermotenis.controller.struts.actions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Categoria;

/**
 *
 * @author Santiago
 */
public class GetCategorias extends ActionSupport {
    
	private static final long serialVersionUID = -6301824235569113278L;

	private List<Categoria> categorias;
    
    @Autowired
    private GenericDao<Categoria, Integer> categoriaDao;
    
    @Override
    public String execute() {
        categorias = categoriaDao.findAll();
        return SUCCESS;   
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }   
}
