/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacefight;

import javafx.application.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;


/**
 *
 * @author giamy
 */
public class SpaceFight extends Application {
    
    private GameViewSpaceFight gameView;
    private Registration registrationView;
    private Scores scoresView;
    
    
    public void start (Stage primaryStage)
    {
        EventGUI.invia("AVVIO");
        
        registrationView = new Registration();
        gameView = new GameViewSpaceFight();
        scoresView = new Scores ();
        scoresView.AggiornaListaRanking(DBScores.updateRanking());
        
        VBox vboxDestra = new VBox();
        vboxDestra.getChildren().addAll(scoresView, registrationView);
        vboxDestra.setPadding(new Insets(0, 20, 0, 0));
        BorderPane root = new BorderPane();
        root.setRight(vboxDestra);
        root.setCenter(gameView);
        
        Scene scene = new Scene(root, 2350, 1200);
        
        
        registrationView.campoUsername.setOnAction((ActionEvent e) -> { root.requestFocus(); });
        gameView.setOnMousePressed((MouseEvent ev)->{gameView.requestFocus();});
        registrationView.getButton().setOnAction((ActionEvent ev)->{ButtonPlayPressed();});
        gameView.gameOverProperty().addListener((obs, gameOverLast, gameOverNow) -> {GameOver();});
        scene.setOnKeyPressed((KeyEvent e) -> {checkPressedKeyboard(e);});
        scene.setOnKeyReleased((KeyEvent e) -> {checkReleasedKeyboard(e);});
        primaryStage.setOnCloseRequest((WindowEvent we) -> {ClosedWindow();});
        root.requestFocus();
        primaryStage.setTitle("Space Fight");
        primaryStage.setScene(scene);
        primaryStage.show();
       
        StatoPassatoGioco cache = new StatoPassatoGioco(gameView, registrationView);
        cache.caricaStatoGioco(gameView, registrationView);
        gameView.updateStatus();
    }
    
   
    public void ButtonPlayPressed()
    {
        EventGUI.invia("START");
         
        boolean valido = registrationView.usernameValido();
        if(!valido){
            System.out.println("[ERR] Username non valido");
            return;
        }
        
        gameView.ifPlayButtonPressed();
        gameView.requestFocus();
    }
    
    public void checkPressedKeyboard(KeyEvent e)
    {
        gameView.PressedKeyboard(e);
    }

    public void checkReleasedKeyboard(KeyEvent e)
    {
        gameView.ReleasedKeyboard(e);
    }
    
     public void ClosedWindow()
    {
        
        StatoPassatoGioco cache = new StatoPassatoGioco(gameView, registrationView); 
        cache.salvaStatoGioco();
        EventGUI.invia("TERMINE");
    }
    
    public void GameOver()
    {
        if(gameView.isGameOver()){
            gameView.setGameOver(false);
            String username = registrationView.getCampoUsername(); 
            
            int score = gameView.getPoints(); 
            DBScores.AddRanking(new Partita(0, username, score));
            scoresView.AggiornaListaRanking(DBScores.updateRanking());
        }
    }

    public static void main(String[] args) 
    {
        launch(args);
    }
   
}
    
    