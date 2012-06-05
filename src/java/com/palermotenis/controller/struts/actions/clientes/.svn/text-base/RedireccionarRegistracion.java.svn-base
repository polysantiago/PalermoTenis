/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.clientes;

import com.opensymphony.xwork2.ActionSupport;
import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.SavedRequest;

/**
 *
 * @author Poly
 */
public class RedireccionarRegistracion extends ActionSupport implements SessionAware {

    private String email;
    private String contrasenia;
    private Map<String, Object> session;
    private SavedRequest savedRequest;
    private AuthenticationManager authenticationManager;

    private String redirectUrl;


    @Override
    public String execute() {

        savedRequest = (SavedRequest) session.get(DefaultSavedRequest.SPRING_SECURITY_SAVED_REQUEST_KEY);
        Authentication auth = new UsernamePasswordAuthenticationToken(email, contrasenia);
        Authentication result = authenticationManager.authenticate(auth);
        SecurityContextHolder.getContext().setAuthentication(result);

        if(savedRequest != null){
            session.put("PREVIOUS_PAGE", savedRequest.getRedirectUrl());
            redirectUrl = savedRequest.getRedirectUrl();
        } else {
            redirectUrl = "/";
        }

        return SUCCESS;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
}
