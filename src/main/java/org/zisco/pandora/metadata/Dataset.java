/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.zisco.pandora.metadata;

import org.zisco.pandora.endpoints.Destination;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author zisco
 */
public class Dataset {
    RowMetadata cols = null;
    RowData values = null;
    HashMap params = null;
    
    Destination destination = null;
    private String reference;
    
    public Dataset() {
        cols = new RowMetadata();
        values = new RowData();
    }
    
    public void setParams(HashMap params) {
        this.params = params;
    }
    
    public HashMap getParams() {
        return this.params;
    }
    
    public void addColumn(Column c) {
        cols.add(c);
    }
    
    public Vector getColumns() {
        return cols;
    }
    
    public int getColumnsCount(){
        return cols.size();
    }
    
    public Column getColumn(int index) {
        Column c = null;
        try{
            c =  (Column)cols.elementAt(index);
        } catch (Exception e) {
            //FIXME
        }
        return c;
    }
    
    public Vector<Value> getValues() {
        return this.values;
    }
    
    public RowData nextValues() {
        RowData v = new RowData();
        Iterator<Column> i = cols.iterator();
        while(i.hasNext()) {
            Column c = (Column)i.next();
            v.add(c.nextValue());
        }
        this.values = v;
        return this.values;
    }
    
    public Destination getDestination() {
        return this.destination;
    }
    
    public void setDestination(Destination d) {
        this.destination= d;
    }
    
    public void setReference (String reference) {
        this.reference = reference;
    }
    
    public String getRerefence() {
        return this.reference;
    }
    
}

