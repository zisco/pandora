/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zisco.pandora.endpoints.impl;

import org.zisco.pandora.endpoints.Destination;
import org.zisco.pandora.metadata.RowData;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import org.apache.log4j.Logger;

/**
 *
 * @author zisco
 */
public class FileDestination extends Destination {

    static Logger logger = Logger.getLogger(FileDestination.class);
    
    BufferedOutputStream w = null;
    File f = null;
    String filename = null;

    String linesep = "\n";
    String colsep = "\t";
    
    public FileDestination(HashMap params) {
        this.params = params;
        
        //colsep = (String) params.get("colseparator");
        //linesep = (String) params.get("lineseparator");
        
        logger.debug("colseparator: '"+colsep+"'");
        logger.debug("lineseparator: '"+linesep+"'");

    }
    
    public void close() {
        logger.info("closing destination '"+filename+"'");
        try {
            if (w.equals(null) || w == null) {
                w.flush();
                w.close();
            }
        } catch (Exception ex) {
                ex.printStackTrace();
        }
    }

    public void open() {
        filename = (String) params.get("output");
        boolean append = false;
        if (this.params.containsKey("mode")) {
            String mode=(String)this.params.get("mode");
            if (mode.equals("append")) {
                append = true;
            }
        }

        try {
            logger.debug("opening file destination to file '"+filename+"'");
            w = new BufferedOutputStream(new FileOutputStream(filename, append));
        } catch (Exception e) {
        //FIXME
        }
    }

    public void writeRow(RowData row) {
        int c = row.size();
        if(c==0) return;
        String toOut = row.get(0).toString();
        for (int i = 1; i < c ; i++) {
            toOut += colsep + row.get(i).toString() ;
        }
        toOut += linesep; //FIXME
        try {
            w.write(toOut.getBytes());
            w.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
