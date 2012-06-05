/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.model.exceptions;

import org.hibernate.HibernateException;

/**
 *
 * @author Poly
 */
public class IllegalValueException extends HibernateException {
    public IllegalValueException(String message, Throwable cause) {
        super(message, cause);
    }
    public IllegalValueException(String message) {
        super(message);
    }

}
