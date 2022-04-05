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
public class Navicelle implements Serializable{
    
    private final static int defaultVelEnemyBad = 2;
    private final static int defaultVelEnemyWorse = 3;
    public double posizioneX;
    public double posizioneY; 
    public double velocitaX;
    public double velocitaY;
    public double rotation;
    public int direzione; 
    public int shipType;  
    public int timerRinascita;
    public int limiteTimer;
    public int contatoreRinascite;
    public boolean inGioco;
    public int probabilitaWorseShip = 7;
    
    
    public Navicelle(double posX, double posY, int dir, int tipoTank){
        posizioneX = posX;
        posizioneY = posY;
        direzione = dir;
        this.shipType = tipoTank; //o Giocatore, 1 Nemico
        this.rotation = 0;
        velocitaX = velocitaY = 0;
        
        
        if(shipType == 1 || shipType == 2){
            limiteTimer = (int)(800 * Math.random()) + 200;
            timerRinascita = limiteTimer;
            contatoreRinascite = 1;
            inGioco = false;
        }
    }
     
    public void setTipo(){    
        int rand = (int)(Math.random()*probabilitaWorseShip);//Math.random ritorna un valore casuale tra 0(incluso) e 1 (incluso)
        if(rand == 0)
            shipType = 2;
        else 
            shipType = 1;
        
        if(probabilitaWorseShip != 0)
            probabilitaWorseShip --;
        
    }
    
    public double getPosizioneX() {
        return posizioneX;
    }

    public void setPosizioneX(double posizioneX) {
        this.posizioneX = posizioneX;
    }

    public double getPosizioneY() {
        return posizioneY;
    }

    public void setPosizioneY(double posizioneY) {
        this.posizioneY = posizioneY;
    }
    
    public int getDirezione() {
        return direzione;
    }
    
    public double getRotation () {
        return rotation;
    }

    public void setDirezione(int direzione) {
        this.direzione = direzione;
    }

    public double getVelocitaX() {
        return velocitaX;
    }
    
    
    public double getVelocitaY() {
        return velocitaY;
    }
    
    public int getShipType(){
        return shipType;
    }

    public void setVelocitaX(double velocitaX) {
        if(velocitaX != 0 && shipType == 1)
            this.velocitaX = defaultVelEnemyBad * (velocitaX/3);
        else if(velocitaX != 0 && shipType == 2)
            this.velocitaX = defaultVelEnemyWorse * (velocitaX/3);
        else
            this.velocitaX = velocitaX;
    }

    public void setVelocitaY(double velocitaY) {
        if(velocitaY != 0 && shipType == 1)
            this.velocitaY = defaultVelEnemyBad * (velocitaY/3);
        else if(velocitaY != 0 && shipType == 2)
            this.velocitaY = defaultVelEnemyWorse * (velocitaY/3);
        else
            this.velocitaY = velocitaY;
    }

    public boolean isInGioco() {
        return inGioco;
    }

    public void setInGioco(boolean inGioco) {
        this.inGioco = inGioco;
    }
    
    public boolean Collisione (double x, double y, double side, double velX, double velY, double width, double height)
    {
        double right = x + side + velX;
        double left = x + velX;
        double up = y + velY;
        double down = y + side + velY;
        
        double rightRock = posizioneX + width;
        double leftRock = posizioneX;
        double upRock = posizioneY;
        double downRock = posizioneY + height;
        
        if(((right >= leftRock && left < leftRock) || (right >= rightRock && left < rightRock)) 
                && ((down > upRock && down < downRock) || (up > upRock && up < downRock)))
            return true;
        if(((up < upRock && down >= upRock) || (up < downRock && down >= downRock)) 
                && ((left < rightRock && left > leftRock) || (right > leftRock && right < rightRock)))
            return true;
        return (left > leftRock && right < rightRock && up > upRock && down < downRock); 
    }
    
    public int getTimerRinascita() {
        return timerRinascita--;
    }
    
    public void setTimerRinascita(){ 
        contatoreRinascite++;
        timerRinascita = (int)((limiteTimer/contatoreRinascite)*1.5);
    }
    
}
