package com.palermotenis.commandline.setup;

import java.util.List;

import javax.persistence.NoResultException;

import com.google.common.collect.Lists;
import com.palermotenis.model.beans.Idioma;
import com.palermotenis.model.dao.idiomas.IdiomaDao;
import com.palermotenis.model.service.idiomas.IdiomaService;

public class IdiomaMasterData implements MasterData {

    private static final Idioma SPANISH = new Idioma("es", "Spanish");
    private static final Idioma ENGLISH = new Idioma("en", "English");
    private static final Idioma PORTUGUESE = new Idioma("pr", "Portuguese");

    private static final List<Idioma> ALL_IDIOMAS = Lists.newArrayList();

    static {
        ALL_IDIOMAS.add(SPANISH);
        ALL_IDIOMAS.add(ENGLISH);
        ALL_IDIOMAS.add(PORTUGUESE);
    }

    private final IdiomaService idiomaService;
    private final IdiomaDao idiomaDao;

    public IdiomaMasterData(IdiomaService idiomaService, IdiomaDao idiomaDao) {
        this.idiomaService = idiomaService;
        this.idiomaDao = idiomaDao;
    }

    @Override
    public void createOrUpdate() {
        for (Idioma idioma : ALL_IDIOMAS) {
            try {
                idiomaService.getIdioma(idioma.getCodigo());
            } catch (NoResultException ex) {
                idiomaDao.create(idioma);
            }
        }
    }

}
