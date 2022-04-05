/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacefight;

import java.sql.*;
import java.text.*;
import java.util.*;

/**
 *
 * @author giamy
 */
public class DBScores {
    
    private static Connection connessioneDB;
    private static String username = ParametriConfig.ottieniIstanza().usernameDatabase;
    private static String password = ParametriConfig.ottieniIstanza().passwordDatabase;
    private static final String database = "jdbc:mysql://localhost:3306/spacefight";
    private static int campiTabella = ParametriConfig.ottieniIstanza().numeroCampiTabella;
    private static int etaMassimaInGiorni = ParametriConfig.ottieniIstanza().etaMassimaInGiorni;
    private static PreparedStatement statementCaricamentoPartite;
    private static PreparedStatement statementInserimentoPartite;
    
    static {
        try{
            connessioneDB = DriverManager.getConnection(database, username, password);
            statementCaricamentoPartite = connessioneDB.prepareStatement("SELECT * FROM spacefight.partita WHERE DATEDIFF(CURRENT_DATE, dataPartita) <= ? ORDER BY score DESC LIMIT ?;"); 
            statementInserimentoPartite = connessioneDB.prepareStatement("INSERT INTO partita(username, score, dataPartita) VALUES (?, ?, ?);");
        } catch (SQLException e) {System.err.println(e.getMessage());} 
    }
    
    
    public static void AddRanking(Partita partita){
        try{
            SimpleDateFormat DateFormatOne = new SimpleDateFormat("yyyy-MM-dd"); 
            java.util.Date date = new java.util.Date();
            statementInserimentoPartite.setString(1, partita.getUsername());
            statementInserimentoPartite.setInt(2, partita.getScore());
            statementInserimentoPartite.setString(3, DateFormatOne.format(date));
            System.out.println("rows affected: " + statementInserimentoPartite.executeUpdate());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } 
    }
    
    public static List<Partita> updateRanking(){ 
        List<Partita> returnList = new ArrayList();
        int pos = 1;
        try{ 
            statementCaricamentoPartite.setInt(1, etaMassimaInGiorni);
            statementCaricamentoPartite.setInt(2, campiTabella);
            ResultSet result = statementCaricamentoPartite.executeQuery();  
            while (result.next()){ 
                returnList.add(new Partita(pos++, result.getString("username"), result.getInt("score")));
                
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }    
        return returnList;
    }
}   
