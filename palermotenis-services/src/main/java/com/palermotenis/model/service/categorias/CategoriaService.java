package com.palermotenis.model.service.categorias;

import java.util.List;

import javax.persistence.NoResultException;

import com.palermotenis.model.beans.Categoria;

public interface CategoriaService {

    void createNewCategoria(String codigo, String nombre);

    Categoria getCategoriaByCodigo(String codigo) throws NoResultException;

    Categoria getCategoriaById(Integer categoriaId);

    List<Categoria> getAllCategorias();

}
