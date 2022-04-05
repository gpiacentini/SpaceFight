/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacefight;

import javafx.animation.*;

import javafx.beans.property.*;
import javafx.scene.*;
import javafx.util.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.shape.*;

/**
 *
 * @author giamy
 */
public class GameViewSpaceFight extends Group{
    
    final private static int defaultVel = 3;
    final private static int defaultVelLaser = 8;
    private static final Image background = new Image("resources/space&station.png");
    private static final Image shipImage = new Image("resources/playerShip2_blue.png");
    private static final Image shipEnemyBadImage = new Image("resources/playerShip2_red.png");
    private static final Image shipEnemyWorseImage = new Image("resources/playerShip1_red.png");
    private static final Image rockImage = new Image("resources/meteorBrown_big4.png");
    private ImageView spriteShip;
    private ImageView[] spriteEnemy;
    private Circle spriteLaser;    
    private Circle[] spriteLaserEnemy;
    private final AnimationTimer gameTimer;
    private boolean animation;
    final private Label titleGame;
    final private Label labelPunteggio;
    public ImageView sfondoGioco;    
    private int points;
    private Navicelle ship;
    private Navicelle[] shipEnemy;
    private Rocks[] rock;
    private Rocks baseGiocatore;
    private Lasers laserGiocatore;
    private Lasers[] laserNemico;
    private final BooleanProperty gameOver = new SimpleBooleanProperty(); 
    private boolean giocoIniziato = false;
    private boolean left, right, up, down;
    
    
    public GameViewSpaceFight()
    {
        labelPunteggio = new Label("Punteggio : " + points);
        titleGame = new Label("SPACE FIGHT");
        titleGame.setLayoutX(870);
        titleGame.setLayoutY(-70);
        titleGame.setStyle("-fx-font-size: 45px; -fx-font-weight: bold;");
        labelPunteggio.setLayoutX(10);
        labelPunteggio.setLayoutY(-40);
        labelPunteggio.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        sfondoGioco = new ImageView();
        sfondoGioco.setImage(background);
    
        animation = false;    
        shipEnemy = new Navicelle[5];
        spriteEnemy = new ImageView[5];
        laserNemico = new Lasers[5];
        spriteLaserEnemy = new Circle[5];
        points = 0;
        
        gameTimer = new AnimationTimer() 
        {
            @Override
            public void handle(long arg0) 
            {
                handleAnimTimer();
            }
        };
        getChildren().addAll(titleGame, labelPunteggio, sfondoGioco);
    }
    
    public void ifPlayButtonPressed(){
        giocoIniziato = true;
        
       
        if(getChildren().contains(spriteShip))
            getChildren().remove(spriteShip);
        
        if(getChildren().contains(spriteLaser))
            getChildren().remove(spriteLaser);
        
        for(int i=0; i<5; i++){
            if(getChildren().contains(spriteEnemy[i]))
                getChildren().remove(spriteEnemy[i]);
            if(getChildren().contains(spriteLaserEnemy[i]))
                getChildren().remove(spriteLaserEnemy[i]);
        }
        
        if(animation == true)  
            gameTimer.stop();
        
        startGame();
     
        animation = true;
        gameTimer.start();
    }
    
    
    private void startGame(){
        double widthRock = rockImage.getWidth();
        double heightRock = rockImage.getHeight();
        points = 0;
        labelPunteggio.setText("Punteggio : " + points);
        baseGiocatore = new Rocks(900, 1000, 0,  widthRock, heightRock);
        
        left = right = up = down = false;
        
        ship = new Navicelle(500, 1000, 10, 0);
        ship.setDirezione(3);
        laserGiocatore = new Lasers(-1, -1, 0, 0, 0, false);
        spriteShip = new ImageView("resources/playerShip2_blue.png");
        spriteShip.setX(ship.getPosizioneX());
        spriteShip.setY(ship.getPosizioneY());
        getChildren().add(spriteShip);
        
        for(int i=0; i<5; i++){
            laserNemico[i] = new Lasers(-1, -1, 0, 0, i, false);
            shipEnemy[i] = new Navicelle(-10, -10, 10, 1);
            shipEnemy[i].setDirezione(6);
            spriteEnemy[i] = new ImageView();
            spriteEnemy[i].setX(shipEnemy[i].getPosizioneX());
            spriteEnemy[i].setY(shipEnemy[i].getPosizioneY());
        }
        
        rock = new Rocks[6];
         
        rock[0] = new Rocks (256.0,234.0,1,widthRock, widthRock);
        rock[1] = new Rocks (718.0,203.0,1,widthRock, widthRock); 
        rock[2] = new Rocks (1367.0,258.0,1,widthRock, widthRock);
        rock[3] = new Rocks (552.0,686.0,1,widthRock, widthRock);
        rock[4] = new Rocks (934.0,463.0,1,widthRock, widthRock);
        rock[5] = new Rocks (1481.0,687.0,1,widthRock, widthRock);
    }
    
    public void handleAnimTimer()
    {
        boolean collisioneRock = checkRockCollision(ship.getPosizioneX(), ship.getPosizioneY(), shipImage.getWidth(), ship.getVelocitaX(), ship.getVelocitaY());
        int collisioneShip = checkShipCollision(ship.getPosizioneX(), ship.getPosizioneY(), shipImage.getWidth(),ship.getVelocitaX(), ship.getVelocitaY());
        boolean hitGameOver = false; 
        
        
        if(collisioneShip != -1)
            manageGameOver();
        updatePositionShipPlayer(collisioneRock);
        

        for(int i=0; i<5; i++){
            hitGameOver = manageUpdatePositionEnemy(i, hitGameOver);

            if(laserNemico[i].isAttivo()){ 
                collisioneRock = checkRockCollision(laserNemico[i].getPosizioneX(), laserNemico[i].getPosizioneY(), spriteLaserEnemy[i].getRadius(), laserNemico[i].getVelocitaX(), laserNemico[i].getVelocitaY());
                boolean collisioneShipPlayer = checkCollisionShipPlayer(laserNemico[i].getPosizioneX(), laserNemico[i].getPosizioneY(), spriteLaserEnemy[i].getRadius(), laserNemico[i].getVelocitaX(), laserNemico[i].getVelocitaY());
                hitGameOver = checkCollisionBase(laserNemico[i].getPosizioneX(), laserNemico[i].getPosizioneY(), spriteLaserEnemy[i].getRadius(), laserNemico[i].getVelocitaX(), laserNemico[i].getVelocitaY()); 
                updatePositionLaserEnemy(i, hitGameOver, collisioneRock, collisioneShipPlayer, collisioneShip);
            }
        }

        manageCollisionLaserShip(hitGameOver, collisioneRock, collisioneShip);
    }
    
    public void updateCollisionLaserEnemy(boolean hitGameOver, boolean collisioneRock, int collisioneShip){
        if(laserGiocatore.isAttivo()){
            collisioneRock = checkRockCollision(laserGiocatore.getPosizioneX(), laserGiocatore.getPosizioneY(), spriteLaser.getRadius(), laserGiocatore.getVelocitaX(), laserGiocatore.getVelocitaY());
            collisioneShip = checkShipCollision(laserGiocatore.getPosizioneX(), laserGiocatore.getPosizioneY(), spriteLaser.getRadius(), laserGiocatore.getVelocitaX(), laserGiocatore.getVelocitaY());
            hitGameOver = checkCollisionBase(laserGiocatore.getPosizioneX(), laserGiocatore.getPosizioneY(), spriteLaser.getRadius(), laserGiocatore.getVelocitaX(), laserGiocatore.getVelocitaY()); 

            if(hitGameOver)
                manageGameOver();
           
            else if(!collisioneRock && (collisioneShip == -1) && (laserGiocatore.getPosizioneX() - spriteLaser.getRadius() + laserGiocatore.getVelocitaX()) > 4
                && (laserGiocatore.getPosizioneX() + laserGiocatore.getVelocitaX()) < background.getWidth() - spriteLaser.getRadius()*2 - 4
                    && (laserGiocatore.getPosizioneY() + laserGiocatore.getVelocitaY()) > 6
                && (laserGiocatore.getPosizioneY() + laserGiocatore.getVelocitaY()) < background.getHeight() - spriteLaser.getRadius()*2 - 4){

                laserGiocatore.setPosizioneX(laserGiocatore.getPosizioneX() + laserGiocatore.getVelocitaX());
                spriteLaser.setCenterX(laserGiocatore.getPosizioneX() + laserGiocatore.getVelocitaX());
                laserGiocatore.setPosizioneY(laserGiocatore.getPosizioneY() + laserGiocatore.getVelocitaY());
                spriteLaser.setCenterY(laserGiocatore.getPosizioneY() + laserGiocatore.getVelocitaY());
            } else {
                laserGiocatore.setAttivo(false);
                getChildren().remove(spriteLaser);
                if(collisioneShip != -1){
                    points++;
                    labelPunteggio.setText("Punteggio : " + points);
                    shipEnemy[collisioneShip].setInGioco(false);
                    shipEnemy[collisioneShip].setPosizioneX(-10);
                    shipEnemy[collisioneShip].setPosizioneY(-10);
                    getChildren().remove(spriteEnemy[collisioneShip]); 
                }
            }   
        }
    }
    
    private boolean checkRockCollision(double x, double y, double width, double velX, double velY)
    {
        boolean boom;
        
         
        for(int i=0; i<=5; i++){
            boom = rock[i].controllaCollisione(x, y, width, velX, velY);
            if(boom)
                return true;
        }
        
        boom = baseGiocatore.controllaCollisione(x, y, width, velX, velY);
        
        return boom;
    }
    
    
     
    private int checkShipCollision(double x, double y, double width,double velX, double velY)
    {
        boolean boom;
        
        for(int i=0; i<5; i++){
            boom = shipEnemy[i].Collisione(x, y, width,velX, velY, shipImage.getWidth(), shipImage.getHeight());
            if(boom)
                return i;
        }
        
        return -1;
    }
    
    private boolean checkCollisionShipPlayer(double x, double y, double width,double velX, double velY){
        return ship.Collisione(x, y, width,velX, velY, shipImage.getWidth(), shipImage.getHeight());
    }
    
    private boolean checkCollisionBase(double x, double y, double width,double velX, double velY){
        return baseGiocatore.controllaCollisione(x, y, width,velX, velY);
    }
    
    private void manageGameOver()
    {
       gameTimer.stop();
        animation = false;
        giocoIniziato = false;
        setGameOver(true);
    }
    
    public BooleanProperty gameOverProperty() {
        return gameOver;
    }
    
    public boolean isGameOver() {
        return gameOver.get();
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver.set(gameOver);
    }
    
    public void updatePositionShipPlayer(boolean impact)
    {                    
        if(!impact && (ship.getPosizioneX() + ship.getVelocitaX()) >= 0 
            && (ship.getPosizioneX() + ship.getVelocitaX()) <= background.getWidth() - shipImage.getWidth()){
            double x = ship.getPosizioneX();
            double velX = ship.getVelocitaX();
            ship.setPosizioneX(x + velX);
            spriteShip.setX(x + velX);                        
        }

        if(!impact && (ship.getPosizioneY() + ship.getVelocitaY()) >= 0
            && (ship.getPosizioneY() + ship.getVelocitaY()) <= background.getHeight() - shipImage.getHeight()){
            double y = ship.getPosizioneY();
            double velY = ship.getVelocitaY();
            ship.setPosizioneY(y + velY);
            spriteShip.setY(y + velY); 
        }
    }
    
    public void Directions(int direzione){
        RotateTransition rt = new RotateTransition(Duration.millis(1), spriteShip);
        if(direzione == 0){
            ship.setDirezione(0);//verso l'alto
            rt.setFromAngle(270);
        } else if(direzione == 3){
            ship.setDirezione(3);//verso destra
            rt.setFromAngle(0);
        } else if(direzione == 6){
            ship.setDirezione(6);//verso il basso
            rt.setFromAngle(90);
        } else if(direzione == 9){ //verso sinistra
            ship.setDirezione(9);
            rt.setFromAngle(180);
        }  
        rt.play();
    }
    
    public void updateStatus(){
        double widthRock = rockImage.getWidth();
        double heightRock = rockImage.getHeight();
        
        if(giocoIniziato == true){          
            labelPunteggio.setText("Punteggio : " + points);
            
            
            baseGiocatore = new Rocks(1000 , 1000, 0,  widthRock, heightRock);
            laserGiocatore = new Lasers(-1, -1, 0, 0, 0, false);
            spriteShip = new ImageView("resources/playerShip2_blue.png");
            spriteShip.setX(ship.getPosizioneX());
            spriteShip.setY(ship.getPosizioneY());
            Directions(ship.getDirezione());
            getChildren().add(spriteShip);
            
            for(int i=0; i<5; i++){
                laserNemico[i] = new Lasers(-1, -1, 0, 0, i, false);
                spriteEnemy[i] = new ImageView();
                if(shipEnemy[i].isInGioco()){
                    spriteEnemy[i].setImage(getProperImage(shipEnemy[i].getShipType()));
                    getChildren().add(spriteEnemy[i]);
                }
                spriteEnemy[i].setX(shipEnemy[i].getPosizioneX());
                spriteEnemy[i].setY(shipEnemy[i].getPosizioneY());
            }
            rock = new Rocks[6];
        
            rock[0] = new Rocks (256.0,234.0,1,widthRock, widthRock);
            rock[1] = new Rocks (718.0,203.0,1,widthRock, widthRock); 
            rock[2] = new Rocks (1367.0,258.0,1,widthRock, widthRock);
            rock[3] = new Rocks (552.0,686.0,1,widthRock, widthRock);
            rock[4] = new Rocks (934.0,463.0,1,widthRock, widthRock);
            rock[5] = new Rocks (1481.0,687.0,1,widthRock, widthRock);
        
            animation = true;
            gameTimer.start();
        }
    }
    
    public boolean manageUpdatePositionEnemy(int i, boolean hitGameOver)
    {
        double widthRock = rockImage.getWidth();
        if(shipEnemy[i].isInGioco()){
            hitGameOver = checkRockCollision(shipEnemy[i].getPosizioneX(), shipEnemy[i].getPosizioneY(), shipImage.getWidth(), shipEnemy[i].getVelocitaX(), shipEnemy[i].getVelocitaY());
            if(hitGameOver)
                manageGameOver();

            int shoot = updatePositionEnemyShip(i);
            updatePositionLaserEnemy(shoot, i);
        } else {
            int timer = shipEnemy[i].getTimerRinascita();
            if(timer == 0){
                shipEnemy[i].setTimerRinascita();
                shipEnemy[i].setInGioco(true);
                shipEnemy[i].setTipo();
                shipEnemy[i].setDirezione(6);
                shipEnemy[i].setPosizioneX(33 + widthRock*4*i);
                shipEnemy[i].setPosizioneY(7);
                spriteEnemy[i] = new ImageView();
                spriteEnemy[i].setImage(getProperImage(shipEnemy[i].getShipType()));
                spriteEnemy[i].setX(7 + widthRock*4*i);
                spriteEnemy[i].setY(7);
                getChildren().add(spriteEnemy[i]);
            }
        }
        return hitGameOver;
    }
    
    public void manageCollisionLaserShip (boolean hitGameOver, boolean collisioneRock, int collisioneShip){
        if(laserGiocatore.isAttivo()){
            collisioneRock = checkRockCollision(laserGiocatore.getPosizioneX(), laserGiocatore.getPosizioneY(), spriteLaser.getRadius(), laserGiocatore.getVelocitaX(), laserGiocatore.getVelocitaY());
            collisioneShip = checkShipCollision(laserGiocatore.getPosizioneX(), laserGiocatore.getPosizioneY(), spriteLaser.getRadius(), laserGiocatore.getVelocitaX(), laserGiocatore.getVelocitaY());
            hitGameOver = checkCollisionBase(laserGiocatore.getPosizioneX(), laserGiocatore.getPosizioneY(), spriteLaser.getRadius(), laserGiocatore.getVelocitaX(), laserGiocatore.getVelocitaY()); 

            if(hitGameOver)
                manageGameOver();
           
            else if(!collisioneRock && (collisioneShip == -1) && (laserGiocatore.getPosizioneX() - spriteLaser.getRadius() + laserGiocatore.getVelocitaX()) > 4
                && (laserGiocatore.getPosizioneX() + laserGiocatore.getVelocitaX()) < background.getWidth() - spriteLaser.getRadius()*2 - 4
                    && (laserGiocatore.getPosizioneY() + laserGiocatore.getVelocitaY()) > 6
                && (laserGiocatore.getPosizioneY() + laserGiocatore.getVelocitaY()) < background.getHeight() - spriteLaser.getRadius()*2 - 4){

                laserGiocatore.setPosizioneX(laserGiocatore.getPosizioneX() + laserGiocatore.getVelocitaX());
                spriteLaser.setCenterX(laserGiocatore.getPosizioneX() + laserGiocatore.getVelocitaX());
                laserGiocatore.setPosizioneY(laserGiocatore.getPosizioneY() + laserGiocatore.getVelocitaY());
                spriteLaser.setCenterY(laserGiocatore.getPosizioneY() + laserGiocatore.getVelocitaY());
            } else {
                laserGiocatore.setAttivo(false);
                getChildren().remove(spriteLaser);
                if(collisioneShip != -1){
                    points++;
                    labelPunteggio.setText("Punteggio : " + points);
                    shipEnemy[collisioneShip].setInGioco(false);
                    shipEnemy[collisioneShip].setPosizioneX(-10);
                    shipEnemy[collisioneShip].setPosizioneY(-10);
                    getChildren().remove(spriteEnemy[collisioneShip]); 
                }
            }   
        }
    }
    
    public Image getProperImage(int shipType){
        
        if(shipType == 1)
            return shipEnemyBadImage;
        else
            return shipEnemyWorseImage;
    }
    
    public void updatePositionLaserEnemy(int i, boolean hitGameOver, boolean collisioneRock, boolean collisioneShipPlayer, int collisioneShip){
        if(hitGameOver || collisioneShipPlayer){
                    manageGameOver();
        }

        else if(!collisioneRock && (collisioneShip == -1) && (laserNemico[i].getPosizioneX() - spriteLaserEnemy[i].getRadius() + laserNemico[i].getVelocitaX()) > 4
            && (laserNemico[i].getPosizioneX() + laserNemico[i].getVelocitaX()) < background.getWidth() - spriteLaserEnemy[i].getRadius()*2 - 4
                && (laserNemico[i].getPosizioneY() + laserNemico[i].getVelocitaY()) > 6
            && (laserNemico[i].getPosizioneY() + laserNemico[i].getVelocitaY()) < background.getHeight() - spriteLaserEnemy[i].getRadius()*2 - 4){

            laserNemico[i].setPosizioneX(laserNemico[i].getPosizioneX() + laserNemico[i].getVelocitaX());
            spriteLaserEnemy[i].setCenterX(laserNemico[i].getPosizioneX() + laserNemico[i].getVelocitaX());
            laserNemico[i].setPosizioneY(laserNemico[i].getPosizioneY() + laserNemico[i].getVelocitaY());
            spriteLaserEnemy[i].setCenterY(laserNemico[i].getPosizioneY() + laserNemico[i].getVelocitaY());
        } else {
            laserNemico[i].setAttivo(false);
            getChildren().remove(spriteLaserEnemy[i]);
        }
    }
    
    public int updatePositionEnemyShip(int i){
        int shoot = -1;
        double x = shipEnemy[i].getPosizioneX();
        double width = background.getWidth()/2;
        double y = shipEnemy[i].getPosizioneY();
        shoot = canIShoot(shipEnemy[i].getDirezione(), x, y);
        
        if(y + shipEnemy[i].getVelocitaY() <=  1000){ 
            shipEnemy[i].setVelocitaY(defaultVel);
            double velY = shipEnemy[i].getVelocitaY();
            shipEnemy[i].setPosizioneY(y + velY);
            spriteEnemy[i].setY(y + velY);   
        } else if(x + shipEnemy[i].getVelocitaX() <=  width){ 
            RotateTransition rt = new RotateTransition(Duration.millis(1), spriteEnemy[i]);
            rt.setFromAngle(270);
            rt.play();
            shipEnemy[i].setDirezione(3);
            shipEnemy[i].setVelocitaY(0);
            shipEnemy[i].setVelocitaX(defaultVel);
            double velX = shipEnemy[i].getVelocitaX();
            shipEnemy[i].setPosizioneX(x + velX);
            spriteEnemy[i].setX(x + velX);
        } else if(x + shipEnemy[i].getVelocitaX() >=  width){ 
            RotateTransition rt = new RotateTransition(Duration.millis(1), spriteEnemy[i]);
            rt.setFromAngle(90);
            rt.play();
            shipEnemy[i].setDirezione(9);
            shipEnemy[i].setVelocitaY(0);
            shipEnemy[i].setVelocitaX(-defaultVel);
            double velX = shipEnemy[i].getVelocitaX();
            shipEnemy[i].setPosizioneX(x + velX);
            spriteEnemy[i].setX(x + velX);
        }
        
        return shoot;
    }
    
   
    public void updatePositionLaserEnemy(int shoot, int i){
        if(shoot == 1 && !laserNemico[i].isAttivo()){
            
            int dir = shipEnemy[i].getDirezione();
            double x = shipEnemy[i].getPosizioneX() + shipImage.getWidth()/2;
            double y = shipEnemy[i].getPosizioneY() + shipImage.getHeight()/2;

            laserNemico[i].setPosizioneX(x);
            laserNemico[i].setPosizioneY(y);
            laserNemico[i].setAttivo(true);
            spriteLaserEnemy[i] = new Circle(x, y, 6);
            spriteLaserEnemy[i].setFill(javafx.scene.paint.Color.RED);
            getChildren().add(spriteLaserEnemy[i]);
            if(dir == 0){
                laserNemico[i].setVelocitaY(-defaultVelLaser);
                laserNemico[i].setVelocitaX(0);
            }
            if(dir == 3){ 
                laserNemico[i].setVelocitaX(defaultVelLaser);
                laserNemico[i].setVelocitaY(0);
            }
            if(dir == 6){
                laserNemico[i].setVelocitaY(defaultVelLaser);
                laserNemico[i].setVelocitaX(0);
            }
            if(dir == 9){
                laserNemico[i].setVelocitaX(-defaultVelLaser);
                laserNemico[i].setVelocitaY(0);
            }
        }
    }
    
     private int canIShoot(int dir, double enemyX, double enemyY){ 
        double posX = ship.getPosizioneX();
        double posY = ship.getPosizioneY();
        double minDistance = 25;
        int probabilitySparo = (int)(50*Math.random());
        
        if(probabilitySparo != 1)
            return 0;
        probabilitySparo = (int)(3*Math.random());
        if(dir == 6){
            if(posX <= enemyX + minDistance && posX >= enemyX - minDistance && posY > enemyY)
                return 1;
        } else if(dir == 3){
            if(posY <= enemyY + minDistance && posY >= enemyY - minDistance && posX > enemyX)
                return 1;
            else {  
                if(probabilitySparo == 1)
                    return 1;
            }
        } else if(dir == 9){
            if(posY <= enemyY + minDistance && posY >= enemyY - minDistance && posX < enemyX)
                return 1;
            else{ 
                if(probabilitySparo == 1)
                    return 1;
            }
        }
        return 0;
    }
    
    public void PressedKeyboard(KeyEvent e){
        if(giocoIniziato == false)
            return;        
        
        RotateTransition rt = new RotateTransition(Duration.millis(1), spriteShip);
        
        if (e.getCode() == KeyCode.A) {
            ship.setVelocitaX(-defaultVel);
            ship.setDirezione(9);
            spriteShip.setX(ship.getPosizioneX()-ship.getVelocitaX());
            left = true;
            rt.setFromAngle(180);            
        } else if(e.getCode() == KeyCode.D){
            ship.setVelocitaX(defaultVel);
            ship.setDirezione(3);
            spriteShip.setX(ship.getPosizioneX()+ship.getVelocitaX());
            right = true;
            rt.setFromAngle(0);
        } else if(e.getCode() == KeyCode.S){
            ship.setVelocitaY(defaultVel);
            ship.setDirezione(6);
            spriteShip.setY(ship.getPosizioneY()+ship.getVelocitaY());
            down = true;
            rt.setFromAngle(90);
        } else if(e.getCode() == KeyCode.W){
            ship.setVelocitaY(-defaultVel);
            ship.setDirezione(0);
            spriteShip.setY(ship.getPosizioneY()-ship.getVelocitaY());
            up = true;
            rt.setFromAngle(270);
        } else if(e.getCode() == KeyCode.SPACE){
            generateLaserShip();
        }
        rt.play();
    }
    
    public void ReleasedKeyboard(KeyEvent e){
        if(giocoIniziato == false)
            return;
        if (e.getCode() == KeyCode.A) {
            ship.setVelocitaX(0);
            left = false;
            manageDirections(9);
        } else if(e.getCode() == KeyCode.D){
            ship.setVelocitaX(0);
            right = false;
            manageDirections(3);
        } else if(e.getCode() == KeyCode.S){
            ship.setVelocitaY(0);
            down = false;
            manageDirections(6);
        } else if(e.getCode() == KeyCode.W){
            ship.setVelocitaY(0);        
            up = false;
            manageDirections(0);
        } 
    }
    
    public void manageDirections(int direzioneTastoLasciato){
        RotateTransition rt = new RotateTransition(Duration.millis(1), spriteShip);
        if(direzioneTastoLasciato == ship.getDirezione()){
            if(up){
                ship.setDirezione(0);
                rt.setFromAngle(270);
            } else if(right){
                ship.setDirezione(3);
                rt.setFromAngle(0);
            } else if(down){
                ship.setDirezione(6);
                rt.setFromAngle(90);
            } else if(left){
                ship.setDirezione(9);
                rt.setFromAngle(180);
            }  
            rt.play();
        }
    }
    
   private void generateLaserShip(){
        if(laserGiocatore.isAttivo() == false){ 
            int dir = ship.getDirezione();
            double x = ship.getPosizioneX() + shipImage.getWidth()/2;
            double y = ship.getPosizioneY() + shipImage.getHeight()/2;
            
            laserGiocatore.setPosizioneX(x);
            laserGiocatore.setPosizioneY(y);
            laserGiocatore.setAttivo(true);
            spriteLaser = new Circle(x, y, 6);
            spriteLaser.setFill(javafx.scene.paint.Color.RED);
            getChildren().add(spriteLaser);
            if(dir == 0){
                laserGiocatore.setVelocitaY(-defaultVelLaser);
                laserGiocatore.setVelocitaX(0);
            }
            if(dir == 3){ 
                laserGiocatore.setVelocitaX(defaultVelLaser);
                laserGiocatore.setVelocitaY(0);
            }
            if(dir == 6){
                laserGiocatore.setVelocitaY(defaultVelLaser);
                laserGiocatore.setVelocitaX(0);
            }
            if(dir == 9){
                laserGiocatore.setVelocitaX(-defaultVelLaser);
                laserGiocatore.setVelocitaY(0);
            }
        }
    }
     
    public int getPoints()
    {
        return points;
    }    

    public void setPoints(int points) {
        this.points = points;
    }
    
    public Navicelle getShip() {
        return ship;
    }

    public void setShip(Navicelle ship) {
        this.ship = ship;
    }

    public Navicelle[] getShipEnemy() {
        return shipEnemy;
    }

    public void setShipEnemy(Navicelle[] shipEnemy) {
        this.shipEnemy = shipEnemy;
    }

    public boolean isGiocoIniziato() {
        return giocoIniziato;
    }

    public void setGiocoIniziato(boolean giocoIniziato) {
        this.giocoIniziato = giocoIniziato;
    }
    
}
