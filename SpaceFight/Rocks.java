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
public class Rocks {
    
    private final double Xposition;
    private final double Yposition;
     
    private final int rockType; // 0 Base, 1 Meteorite
    
    private final double widthRock;
    private final double heightRock;
    
    public Rocks(double posX, double posY, int type, double width, double height){
        Xposition = posX;
        Yposition = posY;
        rockType = type;
        widthRock = width;
        heightRock = height;
    }

    public double getPosizioneX() {
        return Xposition;
    }

    public double getPosizioneY() {
        return Yposition;
    }

    public int getTipoRock() {
        return rockType;
    }

    public double getLarghezzaRock() {
        return widthRock;
    }

    public double getAltezzaRock() {
        return heightRock;
    }
    
    public boolean controllaCollisione(double x, double y, double lato, double velX, double velY){
        
        // (02)
        double right = x + lato + velX;
        double left = x + velX;
        double up = y + velY;
        double down = y + lato + velY;
        
        double rightRock = Xposition + widthRock;
        double leftRock = Xposition;
        double upRock = Yposition;
        double downRock = Yposition + heightRock;
        
        // (03)
        if(((right >= leftRock && left < leftRock) || (right >= rightRock && left < rightRock)) &&
                ((down > upRock && down < downRock) || (up > upRock && up < downRock)))
            return true;
        if(((up < upRock && down >= upRock) || (up < downRock && down >= downRock)) && 
                ((left < rightRock && left > leftRock) || (right > leftRock && right < rightRock)))
            return true;
        return (left > leftRock && right < rightRock && up > upRock && down < downRock);
    }
}

