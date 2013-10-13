/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.zisco.pandora.endpoints.impl;

import org.zisco.pandora.endpoints.Destination;
import org.zisco.pandora.metadata.RowData;
import java.util.HashMap;

/**
 *
 * @author zisco
 */
public class ConsoleDestination extends Destination {

    public ConsoleDestination (HashMap params) {
        this.params = params;
    }
    
    public void open() {
        // stdout doesn't need to be opened!
    }

    public void close() {
        // stdout doesn't need to be closed!
    }

    public void writeRow(RowData row) {
        String sep = (String) params.get("colseparator");
        
        int c = row.size();
        //System.out.println("rowsize: " + c);
        String toOut = "";
        for (int i = 0; i < c ; i++) {
            toOut += row.get(i).toString() + sep;
        }
        
        System.out.println(toOut);
    }
    

}
