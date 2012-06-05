/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.model.beans.valores;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author Poly
 */
@Entity
@DiscriminatorValue("M")
public class ValorMultiple extends ValorPosible {

}
