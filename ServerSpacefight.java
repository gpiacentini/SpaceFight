/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverspacefight;

import java.io.*;
import java.net.*;
import java.nio.file.*;

/**
 *
 * @author giamy
 */
public class ServerSpacefight {
    
    public static void main(String[] args) {
        try (
            ServerSocket socket = new ServerSocket(8080)
        ){
            while(true){
                LogGUI logEvento = LogGUI.ricevi(socket);
                if(logEvento == null)
                    continue;
                
                logEvento.stampa();
                Files.write(Paths.get("eventoXML.xml"),(logEvento.serializzaConUTF() + "\n<!--###########-->\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } 
    }
}

