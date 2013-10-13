/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.zisco.pandora.metadata;

import org.zisco.pandora.generators.Generator;
import org.zisco.pandora.generators.GeneratorFactory;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author zisco
 */
public class Column {

    private Value value = null;
    
    @SuppressWarnings("unused")
	private Map<String,String> params = null;
    private Generator generator = null;
    
    private int type;
    
    public Column (HashMap<String,String> params) throws Exception {
        this.params = params;
        //Generator g = new RandomNumberGenerator(); //FIXME
        this.generator = GeneratorFactory.newInstance(params);
    }
     
    public Value nextValue() {
        this.value = generator.nextValue();
        return this.value;
    }
    
    public Object getValue() {
        return this.value;
    }
    
    public int getType() {
        return this.type;
    }
    
    public void setValue(Value value, int type) {
        this.type= type;
        this.value= value;
    }
}
