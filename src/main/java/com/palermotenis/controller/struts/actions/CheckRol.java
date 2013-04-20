package com.palermotenis.controller.struts.actions;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.authorities.Rol;
import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.util.StringUtility;

/**
 *
 * @author poly
 */
public class CheckRol extends ActionSupport {

	private static final long serialVersionUID = -7583788267327228266L;
	
	private String role;
    private InputStream inputStream;
    
    @Autowired
    private GenericDao<Rol, Integer> rolDao;
    

    public String check() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof Usuario)) {
            inputStream = StringUtility.getInputString("NOT_AUTHENTICATED");
        } else if (!authentication.getAuthorities().contains(rolDao.findBy("Rol", "rol", role))) {
            inputStream = StringUtility.getInputString("NOT_IN_ROLE");
        } else {
            inputStream = StringUtility.getInputString("OK");
        }
        return SUCCESS;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
