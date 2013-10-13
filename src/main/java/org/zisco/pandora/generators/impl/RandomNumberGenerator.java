/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.zisco.pandora.generators.impl;

import org.zisco.pandora.metadata.Column;
import org.zisco.pandora.metadata.Value;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author zisco
 */
public class RandomNumberGenerator extends AbstractGenerator {

    private int minvalue=Integer.MIN_VALUE;
    private int maxvalue=Integer.MAX_VALUE;
    
    public RandomNumberGenerator (HashMap params) {
        if (params.containsKey("minvalue")) {
            minvalue = Integer.parseInt((String)params.get("minvalue"));
        }
        
        if (params.containsKey("maxvalue")) {
            maxvalue = Integer.parseInt((String)params.get("maxvalue"));
        }
            
    }
    
    public Value nextValue() {
        //System.out.println("chiamata generazione!!");
 
  /*      
        if (params.containsKey("minvalue")) {
            minvalue = Integer.parseInt((String)params.get("minvalue"));
        }
        
        if (params.containsKey("maxvalue")) {
            maxvalue = Integer.parseInt((String)params.get("maxvalue"));
        }
*/        
        Value retval = new Value();
        Random r = new Random();
        retval.setValue((Object)Integer.valueOf(r.nextInt(maxvalue-minvalue) + minvalue), org.zisco.pandora.datatypes.Type.NUMERIC);
        return retval;
    }

    
}
