/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.zisco.pandora.endpoints.impl;

import org.zisco.pandora.datatypes.Type;
import org.zisco.pandora.endpoints.*;
import org.zisco.pandora.metadata.RowData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author zisco
 */
public class JDBCDestination extends Destination {
    static Logger logger = Logger.getLogger(JDBCDestination.class);
    
    
    Connection conn;
    String driverClass= null;
    String connectionString= null;
    String username= null;
    String password= null;
    String table= null;
    
    
/*
    <param name="driverClass" value="com.mysql.jdbc.Driver"/>
    <param name="connectionString" value="jdbc:mysql://localhost/scratchpad"/>
    <param name="username" value="zisco"/>
    <param name="password" value="zisco"/>
    <param name="table" value="table1"/>

 */    
    
    public JDBCDestination(HashMap params) {
        this.params = params;
        driverClass= (String) params.get("driverClass");
        connectionString= (String) params.get("connectionString");
        username= (String) params.get("username");
        password= (String) params.get("password");
        table= (String) params.get("table");
        logger.trace("driverClass:" + driverClass);
        logger.trace("connectionString:" + connectionString);
        logger.trace("username:" + username);
        logger.trace("password:" + password);
        logger.trace("table:" + table);
    }
    
    public void open() 
    throws EndpointOpenException
    {
        try {
            logger.debug("open jdbc destination using driverClass:" + driverClass);
            Class.forName(driverClass).newInstance();
            this.conn = DriverManager.getConnection(connectionString, username, password);
        } catch (SQLException ex) {
            //FIXME
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            //FIXME
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            //FIXME
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            //FIXME
            ex.printStackTrace();
        }
    }

    @Override
    public void close() 
    {
        try {
            this.conn.close();
        } catch (SQLException ex) {
            //FIXME
        }
    }

    @Override
    public void writeRow(RowData row) {
        try {

            String sql = "insert into " + this.table + " values (";

            int d = row.size();
            
            for (int i=0;i<d;i++){
                Object val = row.get(i).getValue();
                int type = row.get(i).getType();
                if(i>0) sql += ", ";
                
                switch(type) {
                    case Type.ALPHA:
                        sql += "'"+((String)row.get(i).getValue())+"'";
                        break;
                    case Type.NUMERIC:
                        sql += ((Integer)row.get(i).getValue()).toString();
                        
                        break;
                    default:
                        break;
                }
                
            }
            
            sql += ")";

            Statement t = conn.createStatement();

            t.execute(sql);

            
        } catch (SQLException ex) {
            //FIXME
        }
    }

}
