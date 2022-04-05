/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacefight;

import java.util.*;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
/**
 *
 * @author giamy
 */
public class Scores extends VBox{
    private final TableView<Partita> tabellaVisualeScores = new TableView<>();
    private final Label labelScores;
    private final TableColumn colonnaPosizione;
    private final TableColumn colonnaUsername;
    private final TableColumn colonnaScore;
    private ObservableList<Partita> observablePartita;
    
    
    Scores(){
        labelScores = new Label("SCORES");
        labelScores.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        labelScores.setPadding(new Insets(0, 0, 10, 90));
        colonnaPosizione = new TableColumn("#");
        colonnaPosizione.setCellValueFactory(new PropertyValueFactory<>("posizione"));
        colonnaPosizione.setStyle( "-fx-alignment: CENTER;");
        
        colonnaUsername = new TableColumn("Username");
        colonnaUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colonnaUsername.setStyle( "-fx-alignment: CENTER;");
        
        colonnaScore = new TableColumn("Score");
        colonnaScore.setCellValueFactory(new PropertyValueFactory<>("score"));
        colonnaScore.setStyle( "-fx-alignment: CENTER;");
        
        observablePartita = FXCollections.observableArrayList();
        tabellaVisualeScores.setItems(observablePartita);
        tabellaVisualeScores.getColumns().addAll(colonnaPosizione, colonnaUsername, colonnaScore);
        tabellaVisualeScores.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        getChildren().addAll(labelScores, tabellaVisualeScores);
        setPadding(new Insets(40, 20, 20, 20));
    }
    
    
    public void AggiornaListaRanking(List<Partita> gameList){  
        observablePartita.clear();
        observablePartita.addAll(gameList);
    }
}

