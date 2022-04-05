/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacefight;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import static javafx.scene.layout.VBox.*;

/**
 *
 * @author giamy
 */
public class Registration extends VBox{
    final private Button playButton; 
    final private Label titleLabel;
    final private Label usernameLabel;
    public TextField campoUsername;
    
    public Registration(){
        playButton = new Button("START"); 
        playButton.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-background-color: orangered; -fx-text-fill: white;");
        setMargin(playButton, new Insets(10, 10, 10, 95));
        
        campoUsername = new TextField();
        titleLabel = new Label("LOG IN");
        titleLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        titleLabel.setPadding(new Insets(0, 0, 0, 90));
        
        usernameLabel = new Label("Username");
        usernameLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        usernameLabel.setPadding(new Insets(10, 10, 10, 100));
        
        getChildren().addAll(titleLabel, usernameLabel, campoUsername, playButton);
        setPadding(new Insets(0, 20, 20, 20)); 
    }
    
    
    public boolean usernameValido(){ 
  
        if(campoUsername.getText().equals("")){ 
            
            return false;
        }
        return true;
    }
    
    public String getCampoUsername(){
        return campoUsername.getText();
    }
    
    public void setCampoUsername(String s){
        campoUsername.setText(s);
    }
    
    public Button getButton(){ 
        return playButton;
    }
}
