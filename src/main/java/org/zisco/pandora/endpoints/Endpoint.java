/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.zisco.pandora.endpoints;

/**
 *
 * @author zisco
 */
public interface Endpoint {
    public void open() throws EndpointOpenException; 
    public void close() throws EndpointCloseException;
    public void setReference(String ref);
    public String getReference();
}
