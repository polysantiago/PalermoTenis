package com.palermotenis.controller.struts.actions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.Categoria;
import com.palermotenis.model.service.categorias.CategoriaService;

public class GetCategorias extends ActionSupport {

    private static final long serialVersionUID = -6301824235569113278L;

    private List<Categoria> categorias;

    @Autowired
    private CategoriaService categoriaService;

    @Override
    public String execute() {
        return SUCCESS;
    }

    public List<Categoria> getCategorias() {
        if (categorias == null) {
            categorias = categoriaService.getAllCategorias();
        }
        return categorias;
    }
}
