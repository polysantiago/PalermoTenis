/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.controller.results;

/**
 *
 * @author Poly
 */
public interface ImageCapable {

    public byte[] getImageInBytes();

    public String getContentType();

    public int getContentLength();

}
