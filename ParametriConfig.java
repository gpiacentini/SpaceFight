/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacefight;

import com.thoughtworks.xstream.*;
import java.io.*;
import javax.xml.*;
import javax.xml.parsers.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;
import org.w3c.dom.*;
import org.xml.sax.*;

/**
 *
 * @author giamy
 */
public class ParametriConfig implements Serializable{
    public String ipClient;
    public String ipServer;
    public int portaServer;
    public String usernameDatabase;
    public String passwordDatabase;
    public int numeroCampiTabella;
    public int etaMassimaInGiorni;
    
    private static ParametriConfig istanzaParametriConfig;
    
    private ParametriConfig(){}
    
    
    public static ParametriConfig ottieniIstanza(){
        if(istanzaParametriConfig == null){
            try {
                istanzaParametriConfig = caricaParametriConfig();
            } catch (FileNotFoundException e) {
                System.err.println(e.getMessage());
            }
        }
        return istanzaParametriConfig;
    }
    
    private static ParametriConfig caricaParametriConfig() throws FileNotFoundException{
        valida();
        XStream xstream = new XStream();
        
      
        FileInputStream fileInput = new FileInputStream("configurazione.xml");
        xstream.alias("ParametriConfig", ParametriConfig.class);
        return (ParametriConfig) xstream.fromXML(fileInput);
    }
    
    private static void valida(){
        try{
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Document doc = docBuilder.parse(new File("configurazione.xml"));
            Schema schema = schemaFactory.newSchema(new StreamSource(new File("configurazione.xsd")));
            schema.newValidator().validate(new DOMSource(doc));
        } catch(ParserConfigurationException | SAXException | IOException e) {
            if(e instanceof SAXException)
                System.err.println("Errore di validazione: " + e.getMessage());
            else 
                System.err.println(e.getMessage());
        }
    }
}

