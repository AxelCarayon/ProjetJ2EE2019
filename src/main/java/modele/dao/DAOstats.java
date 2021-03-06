/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

/**
 *
 * @author Axel
 */
public class DAOstats {

    public static final int BOISSONS = 1;
    public static final int CONDIMENTS = 2;
    public static final int DESSERTS = 3;
    public static final int PRODUIT_LAITIERS = 4;
    public static final int PATES_ET_CEREALES = 5;
    public static final int VIANDES = 6;
    public static final int PRODUIT_SECS = 7;
    public static final int POISSONS_ET_FRUITS_DE_MER = 8;
    private final DataSource myDataSource;

    public DAOstats(DataSource dataSource) {
        this.myDataSource = dataSource;
    }

    /**
     * Prends une catégorie et une date de début et de fin et renvoie le CA
     *
     * @param categorie la catégorie
     * @param dateDebut la date de début sous format "AAAA-MM-JJ"
     * @param dateFin la date de fin sous format "AAAA-MM-JJ"
     * @return
     */
    public double CApourCategorie(int categorie, String dateDebut, String dateFin) throws SQLException {
        double ca = 0.0;
        String sql = "SELECT PRIX_UNITAIRE,QUANTITE,REMISE FROM COMMANDE "
                + "INNER JOIN LIGNE ON COMMANDE.NUMERO = LIGNE.COMMANDE "
                + "INNER JOIN PRODUIT ON PRODUIT.REFERENCE = LIGNE.PRODUIT "
                + "WHERE PRODUIT.CATEGORIE = ? "
                + "AND COMMANDE.SAISIE_LE >= ? "
                + "AND COMMANDE.SAISIE_LE <= ? ";
        
        
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1,categorie);
            stmt.setString(2,dateDebut);
            stmt.setString(3, dateFin);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Double prix_unitaire = rs.getDouble("PRIX_UNITAIRE");
                int quantite = rs.getInt("QUANTITE");
                double remise = 1. - rs.getInt("REMISE");
                
                ca += (prix_unitaire * quantite)*remise;
            }
        }catch(Exception e){
            throw e;
        }
        
        return ca;
        
    }
    
    /**
     * Prend un pays, une date de début et de fin et renvoie son CA sur cette période
     * @param pays le client ou l'on souhaite voir le CA
     * @param dateDebut la date de début sous format "AAAA-MM-JJ"
     * @param dateFin la date de fin sous format "AAAA-MM-JJ"
     * @return Le CA sous forme de Double
     * @throws SQLException 
     */
    public double CAparPays(String pays, String dateDebut, String dateFin) throws SQLException{
        double ca = 0.0;
        String sql = "SELECT PRIX_UNITAIRE,QUANTITE,REMISE FROM COMMANDE "
                + "INNER JOIN LIGNE ON COMMANDE.NUMERO = LIGNE.COMMANDE "
                + "INNER JOIN PRODUIT ON PRODUIT.REFERENCE = LIGNE.PRODUIT "
                + "WHERE COMMANDE.PAYS_LIVRAISON = ? "
                + "AND COMMANDE.SAISIE_LE >= ? "
                + "AND COMMANDE.SAISIE_LE <= ? ";
        
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1,pays);
            stmt.setString(2,dateDebut);
            stmt.setString(3, dateFin);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Double prix_unitaire = rs.getDouble("PRIX_UNITAIRE");
                int quantite = rs.getInt("QUANTITE");
                double remise = 1. - rs.getInt("REMISE");
                
                ca += (prix_unitaire * quantite)*remise;
            }
        }catch(Exception e){
            throw e;
        }
        
        return ca;
    }
    
    /**
     * Prend un client, une date de début et de fin et renvoie son CA sur cette période
     * @param client le client ou l'on souhaite voir le CA
     * @param dateDebut la date de début sous format "AAAA-MM-JJ"
     * @param dateFin la date de fin sous format "AAAA-MM-JJ"
     * @return Le CA sous forme de Double
     * @throws SQLException 
     */
    public double CAparClient(String client,String dateDebut, String dateFin) throws SQLException{
        double ca = 0.0;
        String sql = "SELECT PRIX_UNITAIRE,QUANTITE,REMISE FROM COMMANDE "
                + "INNER JOIN LIGNE ON COMMANDE.NUMERO = LIGNE.COMMANDE "
                + "INNER JOIN PRODUIT ON PRODUIT.REFERENCE = LIGNE.PRODUIT "
                + "WHERE COMMANDE.CLIENT = ? "
                + "AND COMMANDE.SAISIE_LE >= ? "
                + "AND COMMANDE.SAISIE_LE <= ? ";
        
        
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1,client);
            stmt.setString(2,dateDebut);
            stmt.setString(3, dateFin);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Double prix_unitaire = rs.getDouble("PRIX_UNITAIRE");
                int quantite = rs.getInt("QUANTITE");
                double remise = 1. - rs.getInt("REMISE");
                
                ca += (prix_unitaire * quantite)*remise;
            }
        }catch(Exception e){
            throw e;
        }
        
        return ca;
    }
    
    /**
     * Renvoie le CA de chaque pays pour une période donnée
     * @param dateDebut début de la période
     * @param dateFin fin de la période
     * @return HashMap<Code du client,CA du client>
     * @throws SQLException 
     */
    public Map<String,Double> CAtousLesPays(String dateDebut, String dateFin) throws SQLException {
        List<String> pays = new ArrayList<String>();
        String sql = "SELECT DISTINCT(PAYS_LIVRAISON) FROM COMMANDE WHERE " +
                     "SAISIE_LE >= ? " +
                     "AND SAISIE_LE <= ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, dateDebut);
            stmt.setString(2, dateFin);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                pays.add(rs.getString("PAYS_LIVRAISON"));
            }
        }catch(Exception e){
            throw e;
        }
        
        Map<String,Double> resultat = new HashMap();
        for (int i=0;i<pays.size();i++){
            resultat.put(pays.get(i),this.CAparPays(pays.get(i), dateDebut, dateFin));
        }
        return resultat;
    }
    
    /**
     * Renvoie le CA de toutes les catégories sans une période donnée
     * @param dateDebut date de début
     * @param dateFin date de fin 
     * @return HashMap<Nom de la categorie,le CA>
     * @throws SQLException 
     */
    public Map<String,Double> CAtoutesLesCategories(String dateDebut, String dateFin) throws SQLException {
        List<Integer> categorie = new ArrayList<Integer>();
        List<String> libelle = new ArrayList<String>();
        String sql = "SELECT CODE,LIBELLE FROM CATEGORIE";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                categorie.add(rs.getInt("CODE"));
                libelle.add(rs.getString("LIBELLE"));
            }
        }catch(Exception e){
            throw e;
        }
        
        Map<String,Double> resultat = new HashMap();
        for (int i=0;i<categorie.size();i++){
            resultat.put(libelle.get(i),this.CApourCategorie(categorie.get(i), dateDebut, dateFin));
        }
        return resultat;
    }
    
    /**
     * Renvoie la liste de tous les pays qui ont passés une commande.
     * @return List<String>
     * @throws SQLException 
     */
    public List<String> tousLesPays() throws SQLException{
        List<String> resultat = new ArrayList<>();
        String sql = "SELECT DISTINCT PAYS_LIVRAISON FROM COMMANDE";
        
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                resultat.add(rs.getString("PAYS_LIVRAISON"));
            }
        }catch(Exception e){
            throw e;
        }
        
        return resultat;
    }

}
