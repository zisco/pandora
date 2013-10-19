/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.zisco.pandora;


import org.apache.log4j.BasicConfigurator;
//import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author zisco
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        //PropertyConfigurator.configure(System.getProperty("log4j.properties"));
    	BasicConfigurator.configure();        
        try {
                DataGenerator g = new DataGenerator();
                g.loadConfiguration(System.getProperty("pandora.xml"));
                g.generate();
        } catch (Exception ex) {
                ex.printStackTrace();
        }
    }

}


