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
public class PatternStringGenerator extends AbstractGenerator {

    private int patternLength;
    private char[] pattern = null;
    
    public PatternStringGenerator(HashMap params) {
        if (params.containsKey("pattern")) {
            pattern = ((String)params.get("pattern")).toCharArray();
            patternLength = pattern.length;
        }

    } 
    
    public Value nextValue() {
        Value retval = new Value(); 
        char[] outCharArray = new char[this.patternLength];
        for(int i=0;i<this.patternLength;i++) {
            if(pattern[i]=='£') {
                outCharArray[i] = (char) ('A' + (char) ((int)(Math.random() * 26))); //FIXME: escape for literals!...
            } else if(pattern[i]=='@') {
                outCharArray[i] = (char) ('a' + (char) ((int)(Math.random() * 26)));
            } else if(pattern[i]=='%') {
                outCharArray[i] = (char) ('0' + (char) ((int)(Math.random() * 10)));
            } else {
                outCharArray[i] = pattern[i];
            }
        }
        
        retval.setValue((Object)String.copyValueOf(outCharArray), org.zisco.pandora.datatypes.Type.ALPHA);
        return retval;
    }

}
