/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacefight;

import com.thoughtworks.xstream.*;
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

/**
 *
 * @author giamy
 */
public class EventGUI implements Serializable{
    
    public final String nomeApplicazione;
    public final String ipClient;
    public final String etichettaEvento;
    public final DataFormato DataFormato;
    public final OraFormato OraFormato;
    
    public EventGUI(String evento){
        ParametriConfig istanzaConf = ParametriConfig.ottieniIstanza();
        Date date = new Date();
        SimpleDateFormat DateFormatOne = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat DateFormatTwo = new SimpleDateFormat("HH:mm:ss");
        this.etichettaEvento = evento;
        this.ipClient = istanzaConf.ipClient;
        this.nomeApplicazione = "SpaceFight";
        this.DataFormato = new DataFormato(DateFormatOne.format(date), "yyyy-MM-dd");
        this.OraFormato = new OraFormato(DateFormatTwo.format(date), "HH:mm:ss");
    }
    
    public static void invia(String evento){
        ParametriConfig istanzaConf = ParametriConfig.ottieniIstanza();
        EventGUI eventoNav = new EventGUI(evento);
        try (Socket socket = new Socket(istanzaConf.ipServer, istanzaConf.portaServer);
            DataOutputStream dataOutStream = new DataOutputStream(socket.getOutputStream());
        ){
            XStream xstream = new XStream();
            xstream.useAttributeFor(DataFormato.class, "formato");
            xstream.useAttributeFor(OraFormato.class, "formato");
            xstream.alias("EventGUI", EventGUI.class); 
            dataOutStream.writeUTF("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + xstream.toXML(eventoNav));
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
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
