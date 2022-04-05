/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverspacefight;

import com.thoughtworks.xstream.*;
import java.io.*;
import java.net.*;
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
public class LogGUI implements Serializable{
    public String nomeApplicazione;
    public String ipClient;
    public String etichettaEvento;
    public final DataFormato DataFormato = null;
    public final OraFormato OraFormato = null;
    
    public LogGUI(){}
    
    public String serializzaConUTF(){
        XStream xstream = new XStream();
        xstream.useAttributeFor(DataFormato.class, "formato");
        xstream.useAttributeFor(OraFormato.class, "formato");
        xstream.alias("EventGUI", LogGUI.class);    
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + xstream.toXML(this);
    }
    
    
    private static void valida(String eventXML){ 
        try{
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Document doc = docBuilder.parse(new ByteArrayInputStream(eventXML.getBytes("UTF-8")));
            Schema schema = schemaFactory.newSchema(new StreamSource(new File("eventoXML.xsd")));
            schema.newValidator().validate(new DOMSource(doc));
        } catch(ParserConfigurationException | SAXException | IOException e) {
            if(e instanceof SAXException)
                System.err.println("Errore di validazione: " + e.getMessage());
            else 
                System.err.println(e.getMessage());
        }
    }
    
    
    public static LogGUI ricevi(ServerSocket serverSocket){ 
        try (
            Socket socket = serverSocket.accept();
            DataInputStream dataInStream = new DataInputStream(socket.getInputStream())) {
            String eventXML = dataInStream.readUTF();
            valida(eventXML);
            XStream xstream = new XStream();
            xstream.useAttributeFor(DataFormato.class, "formato"); 
            xstream.useAttributeFor(OraFormato.class, "formato");
            xstream.alias("EventGUI", LogGUI.class);
            return (LogGUI)xstream.fromXML(eventXML);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }
    
    public void stampa(){
        System.out.println("Nome Applicazione: " + this.nomeApplicazione);
        System.out.println("Ip Client: " + this.ipClient);
        System.out.println("Data (" + this.DataFormato.formato +") : " + this.DataFormato.data);
        System.out.println("Ora (" + this.OraFormato.formato +"): " + this.OraFormato.ora);
        System.out.println("Evento: " + this.etichettaEvento);
        System.out.println("***********************************");
    }
}

class DataFormato implements Serializable {
    public String data;
    public String formato; 
    public DataFormato(String dataIn, String formatoIn) { data = dataIn; formato = formatoIn; }
}
    
class OraFormato implements Serializable {
    public String ora;
    public String formato; 
    public OraFormato(String oraIn, String formatoIn) { ora = oraIn; formato = formatoIn; }
}

