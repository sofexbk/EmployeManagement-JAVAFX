package com.example.projet.metier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoFactory {
    private static Connection connexion=null;
    private static void seConnecter() {
        try{
            System.out.println("Download Driver...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver ok");
            String url="jdbc:mysql://localhost:3306/projet_entreprise";
            connexion =DriverManager.getConnection(url,"root","");
            System.out.println("connected succesfully");
        }catch (ClassNotFoundException ex){
            System.out.println("Download driver Failed!!");
        }catch (SQLException e) {
            System.out.println("prb with connection url , login, pwd"+e.getMessage());
        }
    }
    public static Connection getConnexion() {
        try {
            if (connexion == null || connexion.isClosed()) {
                seConnecter();
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception appropriately
        }
        return connexion;
    }


}
