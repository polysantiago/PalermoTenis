package com.palermotenis.model.service.categorias;

import java.util.List;

import com.palermotenis.model.beans.Categoria;

public interface CategoriaService {

    Categoria getCategoriaById(Integer categoriaId);

    List<Categoria> getAllCategorias();

}
