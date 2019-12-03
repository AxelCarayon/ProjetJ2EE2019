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
import java.util.List;
import javax.sql.DataSource;
import modele.entity.ProduitEntity;

/**
 *
 * @author Axel
 */
public class DAOproduit {
    
    private final DataSource myDataSource;

    public DAOproduit(DataSource dataSource) {
	this.myDataSource = dataSource;
    }
    
    /**
     * Affiche la liste de tous les produits
     * @return List<ProduitEntity>
     */
    public List<ProduitEntity> tousLesProduits() throws SQLException{
        List<ProduitEntity> resultat = new ArrayList<ProduitEntity>();
        String sql = "SELECT * FROM PRODUIT";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int reference = rs.getInt("REFERENCE");
                String nom = rs.getString("NOM");
                int fournisseur = rs.getInt("FOURNISSEUR");
                int categorie = rs.getInt("CATEGORIE");
                String quantite_par_unite = rs.getString("QUANTITE_PAR_UNITE");
                Double prix_unitaire = rs.getDouble("PRIX_UNITAIRE");
                int unite_en_stock = rs.getInt("UNITES_EN_STOCK");
                int unite_commandees = rs.getInt("UNITES_COMMANDEES");
                int niveau_de_reappro = rs.getInt("NIVEAU_DE_REAPPRO");
                int indisponible = rs.getInt("INDISPONIBLE");
                ProduitEntity p = new ProduitEntity(reference,nom,fournisseur,
                categorie,quantite_par_unite,prix_unitaire,unite_en_stock,
                unite_commandees,niveau_de_reappro,indisponible);
                resultat.add(p);
            }
        }catch(Exception e){
            throw e;
        }
        return resultat;
    }
    
    /**
     * Affiche un produit à partir d'une référence
     * @param reference la référence du produit
     * @return ProduitEntity
     */
    public ProduitEntity afficherProduit(int reference) throws SQLException{
        ProduitEntity resultat = null;
        String sql = "SELECT * FROM PRODUIT WHERE REFERENCE = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, reference);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String nom = rs.getString("NOM");
                int fournisseur = rs.getInt("FOURNISSEUR");
                int categorie = rs.getInt("CATEGORIE");
                String quantite_par_unite = rs.getString("QUANTITE_PAR_UNITE");
                Double prix_unitaire = rs.getDouble("PRIX_UNITAIRE");
                int unite_en_stock = rs.getInt("UNITES_EN_STOCK");
                int unite_commandees = rs.getInt("UNITES_COMMANDEES");
                int niveau_de_reappro = rs.getInt("NIVEAU_DE_REAPPRO");
                int indisponible = rs.getInt("INDISPONIBLE");
                ProduitEntity p = new ProduitEntity(reference,nom,fournisseur,
                categorie,quantite_par_unite,prix_unitaire,unite_en_stock,
                unite_commandees,niveau_de_reappro,indisponible);
                resultat = p;
            }
        }catch(Exception e){
            throw e;
        }
        return resultat;
    }
    /**
     * Met à jour le nombre d'unités commandées si le stock est suffisant et modifie
     * la disponibilité du produit si besoin et les unités commandées après la transaction
     * @param reference référence du produit
     * @param quantite le nombre de produits commandés
     * @return true si la transaction s'est correctement déroulée, false sinon.
     */
    public void modifierUnitesCommandees(int reference, int quantite) throws SQLException{
        
        if (quantite<1){
            throw new IllegalArgumentException("La quantité a modifier ne peut être nulle ou négative");
        } 
        
        String sql = "UPDATE PRODUIT SET UNITES_COMMANDEES = UNITES_COMMANDEES+?, "
                + "UNITES_EN_STOCK = UNITES_EN_STOCK - ?, INDISPONIBLE = ? WHERE REFERENCE=?" ;
        try (Connection myConnection = myDataSource.getConnection();
            PreparedStatement statement = myConnection.prepareStatement(sql)) {
            
            int indisponible = 0;
            
            ProduitEntity produit = this.afficherProduit(reference);
            if (produit.getUnites_en_stock()-quantite < 0){
                throw new SQLException("Impossible de modifier l'unité commandée : pas assez d'unités en stock.");
            }
            if (produit.getUnites_en_stock()-quantite == 0){
                indisponible = 1;
            }
            statement.setInt(1, quantite);
            statement.setInt(2,quantite);
            statement.setInt(3,indisponible);
            statement.setInt(4,reference);
            statement.executeUpdate();
        }catch(Exception e){
            throw e;
        }
    }
    
    /**
     * Affiche le nom d'un produit
     * @param reference la référence du produit
     * @return String
     */
    public String nomProduit(int reference) throws SQLException{
        String resultat = null;
        String sql = "SELECT * FROM PRODUIT WHERE REFERENCE = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setInt(1,reference);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                resultat = rs.getString("NOM");
            }
        }catch(Exception e){
            throw e;
        }
        return resultat;
    }
    
    /**
     * Affiche le fournisseur d'un produit
     * @param reference la référence du produit
     * @return int
     */
    public int numeroFournisseur(int reference) throws SQLException{
        int resultat = 0;
        String sql = "SELECT * FROM PRODUIT WHERE REFERENCE = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setInt(1,reference);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                resultat = rs.getInt("FOURNISSEUR");
            }
        }catch(Exception e){
            throw e;
        }
        return resultat;
    }
    
    /**
     * Affiche le numéro de catégorie d'un produit
     * @param reference la référence du produit
     * @return int
     */
    public int numeroCategorie(int reference) throws SQLException{
        int resultat = 0;
        String sql = "SELECT * FROM PRODUIT WHERE REFERENCE = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setInt(1,reference);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                resultat = rs.getInt("CATEGORIE");
            }
        }catch(Exception e){
            throw e;
        }
        return resultat;
    }
    
    /**
     * Affiche la quantité d'un produit
     * @param reference la référence du produit
     * @return int
     */
    public String quantiteParUnite(int reference) throws SQLException{
        String resultat = null;
        String sql = "SELECT * FROM PRODUIT WHERE REFERENCE = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setInt(1,reference);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                resultat = rs.getString("QUANTITE_PAR_UNITE");
            }
        }catch(Exception e){
            throw e;
        }
        return resultat;
    }
    
    /**
     * Affiche le prix unitaire d'un produit
     * @param reference la référence du produit
     * @return Double
     */
    public Double prixUnitaire(int reference) throws SQLException{
        Double resultat = 0.00;
        String sql = "SELECT * FROM PRODUIT WHERE REFERENCE = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setInt(1,reference);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                resultat = rs.getDouble("PRIX_UNITAIRE");
            }
        }catch(Exception e){
            throw e;
        }
        return resultat;
    }
    
    /**
     * Affiche le nombre d'unités d'un produit en stock
     * @param reference la référence du produit
     * @return int
     */
    public int uniteEnStock(int reference) throws SQLException{
        int resultat = 0;
        String sql = "SELECT * FROM PRODUIT WHERE REFERENCE = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setInt(1,reference);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                resultat = rs.getInt("UNITES_EN_STOCK");
            }
        }catch(Exception e){
            throw e;
        }
        return resultat;
    }
    
    /**
     * Affiche le nombre d'unités commandées d'un produit
     * @param reference la référence du produit
     * @return int
     */
    public int uniteCommandes(int reference) throws SQLException{
        int resultat = 0;
        String sql = "SELECT * FROM PRODUIT WHERE REFERENCE = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setInt(1,reference);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                resultat = rs.getInt("UNITES_COMMANDEES");
            }
        }catch(Exception e){
            throw e;
        }
        return resultat;
    }
    
    /**
     * Affiche le niveau de réprovisionnement d'un produit
     * @param reference la référence du produit
     * @return int
     */
    public int niveauReaprovisionnement(int reference) throws SQLException{
        int resultat = 0;
        String sql = "SELECT * FROM PRODUIT WHERE REFERENCE = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setInt(1,reference);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                resultat = rs.getInt("NIVEAU_DE_REAPPRO");
            }
        }catch(Exception e){
            throw e;
        }
        return resultat;
    }
    
    /**
     * Affiche si un produit est Disponible ou non
     * @param reference la référence du produit
     * @return Boolean
     */
    public boolean estIndisponible(int reference) throws SQLException{
        boolean resultat = true;
        String sql = "SELECT * FROM PRODUIT WHERE REFERENCE = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setInt(1,reference);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("INDISPONIBLE") == 0){
                    resultat = false;
                }
                else resultat = true;
            }
        }catch(Exception e){
            throw e;
        }
        return resultat;
    }
}
