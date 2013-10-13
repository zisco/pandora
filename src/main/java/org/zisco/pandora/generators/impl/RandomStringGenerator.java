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
public class RandomStringGenerator extends AbstractGenerator {

    private int length;
    private int patternLength;
    private char[] pattern = null;
    
    public RandomStringGenerator(HashMap params) {
        if (params.containsKey("length")) {
            length = Integer.parseInt((String)params.get("length"));
        }
        
    }
    
    public Value nextValue() {
        Value retval = new Value(); 
        /*
        char[] outCharArray = new char[this.length];
        for(int i=0;i<this.patternLength;i++) {
            if(pattern[i]=='A') {
                
            }
        }
        */        
        retval.setValue((Object)String.valueOf("ECCOLLArandom"), org.zisco.pandora.datatypes.Type.ALPHA);
        return retval;
    }

}
