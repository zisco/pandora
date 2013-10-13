/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.zisco.pandora.endpoints;

import org.zisco.pandora.metadata.RowData;
import java.util.HashMap;

/**
 *
 * @author zisco
 */
public abstract class Destination implements Endpoint {

    public String reference = null;
    public HashMap params = null;
    
    public abstract void open() throws EndpointOpenException;
    public abstract void close() throws EndpointCloseException;
    
    public abstract void writeRow(RowData row);
    
    public void setReference(String ref) {
        this.reference=ref;
    }
    
    public String getReference() {
        return this.reference;
    }
    
    
}
