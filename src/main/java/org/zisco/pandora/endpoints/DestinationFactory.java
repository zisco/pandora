/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.zisco.pandora.endpoints;

import org.zisco.pandora.endpoints.impl.*;
import java.util.HashMap;
import org.apache.log4j.Logger;

/**
 *
 * @author zisco
 */
public class DestinationFactory {

    static Logger logger = Logger.getLogger(DestinationFactory.class);
    
    public static Destination newInstance(String type, HashMap params) 
    throws UnsupportedDestinationException 
    {
        
        Destination d = null;

        if(type.equals("file")) {
            d = new FileDestination(params);
        } else if(type.equals("file")) {
            d = new ConsoleDestination(params);
        } else if(type.equals("jdbc")) {
            d = new JDBCDestination(params);
        } else if(type.equals("class")) {
        // custom destination class....
            throw new UnsupportedDestinationException("destination '"+type+"' not supported.");
        } else {
        // unsupported destination
            throw new UnsupportedDestinationException("destination '"+type+"' not supported.");
        }

        String reference = (String)params.get("ref");
        if(d!= null) d.setReference(reference);
        
        return d;
    }
}
