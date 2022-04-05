/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacefight;

/**
 *
 * @author giamy
 */
public class Lasers {
    private double Xposition;
    private double Yposition;
    private int laserKind; 
    private double velocityX;
    private double velocityY;
    private boolean isThere; 
    
    public Lasers(double posX, double posY, double velX, double velY, int tipo, boolean active){
        Xposition = posX;
        Yposition = posY;
        velocityX = velX;
        velocityY = velY;
        laserKind = tipo;
        isThere = active;
    }

    public double getPosizioneX() {
        return Xposition;
    }

    public double getPosizioneY() {
        return Yposition;
    }

    public double getVelocitaX() {
        return velocityX;
    }

    public double getVelocitaY() {
        return velocityY;
    }

    public int getLaser() {
        return laserKind;
    }

    public void setPosizioneX(double posizioneX) {
        this.Xposition = posizioneX;
    }

    public void setPosizioneY(double posizioneY) {
        this.Yposition = posizioneY;
    }

    public void setVelocitaX(double velocitaX) {
        this.velocityX = velocitaX;
    }

    public void setVelocitaY(double velocitaY) {
        this.velocityY = velocitaY;
    }

    public boolean isAttivo() {
        return isThere;
    }

    public void setAttivo(boolean attivo) {
        this.isThere = attivo;
    }
      
}
