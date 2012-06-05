/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.model.ws;

import java.io.Serializable;

/**
 *
 * @author Poly
 */
public interface CurrencyConvertor extends Serializable {
    public double ConversionRate(String from, String to);
}
