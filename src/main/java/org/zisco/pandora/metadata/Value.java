/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.zisco.pandora.metadata;

/**
 *
 * @author zisco
 */
public class Value {
    Object value;
    int type;
    
    public String toString() {
        return value.toString();
        
    }
    
    public void setValue(Object value, int type) {
        this.value = value;
        this.type = type;
    }
    
    public int getType() {
        return this.type;
    }
    
    public Object getValue() {
        return this.value;
    }
    
    
}
