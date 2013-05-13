package com.palermotenis.model.service.authorities.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.authorities.Rol;
import com.palermotenis.model.dao.authorities.RolDao;
import com.palermotenis.model.service.authorities.RolService;

@Service("rolService")
@Transactional(propagation = Propagation.REQUIRED)
public class RolServiceImpl implements RolService {

    @Autowired
    private RolDao rolDao;

    @Override
    public Rol getRol(String role) {
        return rolDao.findBy("Rol", "rol", role);
    }

}
