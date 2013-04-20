package com.palermotenis.controller.struts.actions;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Pago;

/**
 *
 * @author Poly
 */
public class GetPagos extends ActionSupport {
	
	private static final long serialVersionUID = 8264803144351552901L;

	private Collection<Pago> pagos;
    
    @Autowired
    private GenericDao<Pago, Integer> pagoDao;

    @Override
    public String execute(){
        pagos = pagoDao.findAll();
        return SUCCESS;
    }

    /**
     * @return the pagos
     */
    public Collection<Pago> getPagos() {
        return pagos;
    }

}
