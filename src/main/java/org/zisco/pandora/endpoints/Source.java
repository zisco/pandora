/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.zisco.pandora.endpoints;

/**
 *
 * @author zisco
 */
public abstract class Source implements Endpoint {

    String reference = null;
    
    public abstract void open();
    public abstract void close();
    
    public void setReference(String ref) {
        this.reference=ref;
    }
    
    public String getReference() {
        return this.reference;
    }
    
    
}
