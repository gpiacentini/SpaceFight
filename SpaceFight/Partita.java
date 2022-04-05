/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacefight;

import javafx.beans.property.*;

/**
 *
 * @author giamy
 */
public class Partita{
    private final SimpleIntegerProperty posizione;
    private final SimpleStringProperty username;
    private final SimpleIntegerProperty score;
    
    Partita(int posizione, String username, int score){
        this.posizione = new SimpleIntegerProperty(posizione);
        this.username = new SimpleStringProperty(username);
        this.score = new SimpleIntegerProperty(score);
    }
    
    public String getUsername(){
        return username.get();
    }
    
    public int getScore(){
        return score.get();
    }
    
    public int getPosizione(){
        return posizione.get();
    }
}

