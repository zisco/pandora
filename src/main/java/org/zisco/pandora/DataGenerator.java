/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zisco.pandora;

import org.zisco.pandora.endpoints.Destination;
import org.zisco.pandora.endpoints.DestinationFactory;
import org.zisco.pandora.endpoints.Source;
import org.zisco.pandora.endpoints.UnsupportedDestinationException;
import org.zisco.pandora.metadata.Column;
import org.zisco.pandora.metadata.Dataset;
import org.zisco.pandora.metadata.RowData;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.log4j.Logger;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.*;

/**
 *
 * @author zisco
 */
public class DataGenerator {

    static Logger logger = Logger.getLogger(DataGenerator.class);
    
    Document configuration = null;
    XPath xPath = null;
    Map<String, Destination> destinations = null;
    Map<String, Source> sources = null;
    Map<String, Dataset> datasets = null;

    public DataGenerator() {
        destinations = new HashMap<String, Destination>();
        sources = new HashMap<String, Source>();
        datasets = new HashMap<String, Dataset>();
    }

    public DataGenerator(String filename) {
        loadConfiguration(filename);
    }

    public DataGenerator(Document c) {
        setConfiguration(c);
    }

    private void setConfiguration(Document c) {
        this.configuration = c;
    }

    public Document getConfiguration() {
        return this.configuration;
    }

    public void loadConfiguration(String filename) {
        logger.info("load configuration from file '"+filename + "'");
        try {
            DOMParser p = new DOMParser();
            p.parse(filename);
            setConfiguration(p.getDocument());
        } catch (Exception e) {
        //FIXME
        }
    //logger.log(Level.DEBUG, "end");
    }

    public void generate() throws XPathExpressionException, Exception {
        initGeneration();
        //-------------------------------------------------
        // DO THE WORK!!!!
        logger.debug("start generating data... ");

        //FIXME: parallel...
        // for each dataset: sample rows and write to the destination!....
        Iterator di = datasets.keySet().iterator();
        while (di.hasNext()) {
            Dataset item = (Dataset) datasets.get(di.next());
            //open destination
            Destination dx = item.getDestination();
            //logger.info("generating data for dataset '"+ item.getParams().get("ref").toString()+"' to destination '"+item.getDestination().getReference()+"'");
            dx.open();
            int numrows = Integer.parseInt((String)item.getParams().get("numrows"));
            logger.info("generating data for dataset '"+item.getRerefence()+"' to destination '"+dx.getReference()+"' ["+numrows+" entries]");
            for (int gg = 0 ; gg < numrows ; gg++) {
                RowData data = item.nextValues();
                dx.writeRow(data);
            }
            dx.close();
        }
        logger.info("all data generated.");
    }

    private void scanSources() throws XPathExpressionException {
        // scan sources
        // FIXME
    }
    
    private void scanDestinations() throws XPathExpressionException {
        // scan destinations
        logger.info("setup destinations");
        String xPathDestionations = "/datagenerator/global/destinations/destination";
        NodeList destinationNodes = (NodeList)xPath.evaluate(xPathDestionations, configuration, XPathConstants.NODESET);

        int ndest = destinationNodes.getLength();
        logger.debug("found " + ndest+ " destinations:");
        for (int i = 0; i < ndest; i++) {
            try {
                HashMap params = fetchParams((Element) destinationNodes.item(i));
                String reference = destinationNodes.item(i).getAttributes().getNamedItem("ref").getTextContent();
                String type = destinationNodes.item(i).getAttributes().getNamedItem("type").getTextContent();
                logger.debug(i + " - " + reference + " - " + type);
                NodeList destinationParamsNodes = (NodeList) xPath.evaluate("param", destinationNodes.item(i), XPathConstants.NODESET);
                int nparamsdest = destinationParamsNodes.getLength();
                HashMap<String, String> destParams = new HashMap<String, String>();
                String paramName="";
                String paramValue = "";
                for (int ip = 0; ip < nparamsdest; ip++) {
                    paramName = destinationParamsNodes.item(ip).getAttributes().getNamedItem("name").getTextContent();
                    paramValue = destinationParamsNodes.item(ip).getAttributes().getNamedItem("value").getTextContent();
                    destParams.put(paramName, paramValue);
                    logger.trace("destination param '"+paramName+"' set to '"+paramValue+"'");
                }
                Destination d = (Destination) DestinationFactory.newInstance(type,destParams);
                d.setReference(reference);
                destinations.put(reference, (Destination) DestinationFactory.newInstance(type,destParams));
            } catch (UnsupportedDestinationException ex) {
                logger.error(ex.getMessage());
            }
        }
    }
    
    private void scanDatasets() throws XPathExpressionException, Exception {
        // scan datasets
        logger.info("setup datasets");
        NodeList datasetNodes = (NodeList) xPath.evaluate("/datagenerator/dataset", configuration, XPathConstants.NODESET);
        
        int nds = datasetNodes.getLength();
        for (int i = 0; i < nds; i++) {
            // for each dataset:
            Dataset d = new Dataset();
            // fetch reference and params
            HashMap params = fetchParams((Element) datasetNodes.item(i));
            String reference = datasetNodes.item(i).getAttributes().getNamedItem("ref").getTextContent();
            d.setParams(params);
            d.setReference(reference);

            //fecth and set destination:
            String destRef = (String) xPath.evaluate("@destination", datasetNodes.item(i), XPathConstants.STRING);
            Destination dest = destinations.get(destRef);
            
            //fecth and set column
            NodeList columnsNodes = (NodeList) xPath.evaluate("column", datasetNodes.item(i), XPathConstants.NODESET);
            int ncol = columnsNodes.getLength();

            d.setDestination(dest);
            for (int j = 0; j < ncol; j++) {
                HashMap colparams = fetchParams((Element) columnsNodes.item(j));
                Column c = new Column(colparams);
                d.addColumn(c);
            }
            
            //add the dataset to the datasets
            datasets.put((String) reference, (Dataset) d);
        }
    }
    
    private void initGeneration() throws XPathExpressionException, Exception{
        xPath = XPathFactory.newInstance().newXPath();
        scanSources();
        scanDestinations();
        scanDatasets();
    }
    
    private HashMap fetchParams(Element d) {
        HashMap<String, String> p = new HashMap<String, String>();
        NamedNodeMap attrs = d.getAttributes();
        int a = attrs.getLength();
        for (int i = 0; i < a; i++) {
            p.put(attrs.item(i).getNodeName(), (String) attrs.item(i).getTextContent());
            logger.trace("fetchParams [" + d.getTagName() + "] --- attribute: " +attrs.item(i).getNodeName() + "--- value: "+(String) attrs.item(i).getTextContent());
        }
        return p;
    }

    public static void main(String args[]) {
        try {
            DataGenerator g = new DataGenerator();
            g.loadConfiguration("/zisco/codalizia/java/datagenerator/datagenerator-config-test1.xml");
            g.generate();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            
        }

    }
}
