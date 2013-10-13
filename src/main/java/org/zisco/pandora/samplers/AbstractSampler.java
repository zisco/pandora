/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.zisco.pandora.samplers;

import org.zisco.pandora.metadata.Dataset;
import java.util.HashMap;

/**
 *
 * @author zisco
 */
public abstract class AbstractSampler implements Sampler {

    HashMap params;
    
    public abstract Dataset sample();
    
    public void setParams(HashMap params) {
        this.params = params;
    }
    
    public HashMap getParams() {
        return this.params;
    }
}
