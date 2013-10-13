/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.zisco.pandora.generators;

import org.zisco.pandora.generators.impl.PatternStringGenerator;
import org.zisco.pandora.generators.impl.RandomNumberGenerator;
import org.zisco.pandora.generators.impl.RandomStringGenerator;
import java.util.HashMap;

/**
 *
 * @author zisco
 */
public class GeneratorFactory {
    HashMap parameters = null;
    
    public GeneratorFactory(HashMap params) {
        this.parameters = params;
        
    }
    
    public static Generator newInstance(HashMap params) throws Exception {
        Generator g = null;
        
        String format = (String)params.get("format");
        
        if(format.equals("numeric")) {
            g = new RandomNumberGenerator(params);
        } else if (format.equals("stringpattern")) {
            g = new PatternStringGenerator(params);
        } else {
            //unsupported...
            throw new Exception("unsupported generator... '" + format +"'");
        }
        return g;
    }

}
