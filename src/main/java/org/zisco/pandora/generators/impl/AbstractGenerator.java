/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.zisco.pandora.generators.impl;

import org.zisco.pandora.generators.*;
import org.zisco.pandora.metadata.Column;
import org.zisco.pandora.metadata.Value;
import java.util.HashMap;

/**
 *
 * @author zisco
 */
public abstract class AbstractGenerator implements Generator {

    HashMap params;
    
    public void setParams(HashMap params) {
        this.params = params;
    }
    
    public HashMap getParams() {
        return this.params;
    }
    
    public abstract Value nextValue();
    
    
    
}
