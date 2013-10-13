/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.zisco.pandora.generators.impl;

import org.zisco.pandora.metadata.Value;
import java.util.HashMap;

/**
 *
 * @author zisco
 */
public class IntegerSequenceGenerator extends AbstractGenerator {
    
    private int startValue;
    private int stopValue;
    private int increment;
    private boolean cycle;
    private int currValue;
    
    public IntegerSequenceGenerator(HashMap p) {
        this.params = p;
        this.startValue = 0;
    }
    
    public Value nextValue() {
        
        Value v = new Value();
        
        
        
        return v;
    }

}
