package com.palermotenis.commandline.setup;

import java.util.List;

import javax.persistence.NoResultException;

import com.google.common.collect.Lists;
import com.palermotenis.model.beans.authorities.Rol;
import com.palermotenis.model.dao.authorities.RolDao;
import com.palermotenis.model.service.authorities.RolService;

public class RolMasterData implements MasterData {

    private static final Rol ROLE_ADMIN = new Rol("ROLE_ADMIN");
    private static final Rol ROLE_SUPERVISOR = new Rol("ROLE_SUPERVISOR");
    private static final Rol ROLE_EMPLEADO = new Rol("ROLE_EMPLEADO");
    private static final Rol ROLE_CLIENTE = new Rol("ROLE_CLIENTE");

    private static final List<Rol> ALL_ROLES = Lists.newArrayList();

    static {
        ALL_ROLES.add(ROLE_ADMIN);
        ALL_ROLES.add(ROLE_SUPERVISOR);
        ALL_ROLES.add(ROLE_EMPLEADO);
        ALL_ROLES.add(ROLE_CLIENTE);
    }

    private final RolService rolService;
    private final RolDao rolDao;

    public RolMasterData(RolService rolService, RolDao rolDao) {
        this.rolService = rolService;
        this.rolDao = rolDao;
    }

    @Override
    public void createOrUpdate() {
        for (Rol rol : ALL_ROLES) {
            try {
                rolService.getRol(rol.getRol());
            } catch (NoResultException ex) {
                rolDao.create(rol);
            }
        }
    }

}
