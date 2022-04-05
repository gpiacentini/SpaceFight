/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacefight;

import java.io.*;

/**
 *
 * @author giamy
 */
public class StatoPassatoGioco implements Serializable{
    public Navicelle ship = null;
    public Navicelle[] shipEnemy = null;
    public String username = "";
    public int points = 0;
    public boolean giocoIniziato = false;
    
    public StatoPassatoGioco(GameViewSpaceFight gameview, Registration registration){
        ship = gameview.getShip();
        shipEnemy = gameview.getShipEnemy();
        username = registration.campoUsername.getText();
        points = gameview.getPoints();
        giocoIniziato = gameview.isGiocoIniziato();
    }
    
    public void caricaStatoGioco(GameViewSpaceFight gameview, Registration registration){ 
        StatoPassatoGioco cache;
        
        try (ObjectInputStream objectInStream = new ObjectInputStream(new FileInputStream("cache.bin"))) {
            cache = (StatoPassatoGioco)objectInStream.readObject();
            System.out.println("Carica stato effettuata");
        } catch (FileNotFoundException ex) {
                System.out.println("Non trovo il file");
                cache = null;
        } catch (ClassNotFoundException | IOException ex) {
                System.err.println(ex.getMessage());
                cache = null;
        }
        
        if(cache == null){
            return;
        }
        
        gameview.setPoints(cache.points);
        registration.campoUsername.setText(cache.username);
        gameview.setGiocoIniziato(cache.giocoIniziato);
        gameview.setShip(cache.ship);
        gameview.setShipEnemy(cache.shipEnemy);
        
        
    }
    
    
    public void salvaStatoGioco(){
        
        try (ObjectOutputStream objectOutStream = new ObjectOutputStream(new FileOutputStream("cache.bin"))) { // (03)
            objectOutStream.writeObject(this);
            System.out.println("Scrittura stato effettuata");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}

