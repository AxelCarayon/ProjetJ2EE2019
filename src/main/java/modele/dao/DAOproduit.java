/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao;

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
    public List<ProduitEntity> tousLesProduits(){
        //TODO
        return null;
    }
    
    /**
     * Affiche un produit à partir d'une référence
     * @param reference la référence du produit
     * @return ProduitEntity
     */
    public ProduitEntity afficherProduit(int reference){
        //TODO
        return null;
    }
    /**
     * Met à jour le nombre d'unités commandées si le stock est suffisant et modifie
     * la disponibilité du produit si besoin et les unités commandées après la transaction
     * @param reference référence du produit
     * @param quantite le nombre de produits commandés
     * @return true si la transaction s'est correctement déroulée, false sinon.
     */
    public boolean modifierUnitesCommandees(int reference, int quantite){
        //TODO
        return false;
    }
    
    /**
     * Affiche le nom d'un produit
     * @param reference la référence du produit
     * @return String
     */
    public String nomProduit(int reference){
        //TODO
        return null;
    }
    
    /**
     * Affiche le fournisseur d'un produit
     * @param reference la référence du produit
     * @return int
     */
    public int numeroFournisseur(int reference){
        //TODO
        return 0;
    }
    
    /**
     * Affiche le numéro de catégorie d'un produit
     * @param reference la référence du produit
     * @return int
     */
    public int numeroCategorie(int reference){
        //TODO
        return 0;
    }
    
    /**
     * Affiche la quantité d'un produit
     * @param reference la référence du produit
     * @return int
     */
    public int quantiteParUnite(int reference){
        //TODO
        return 0;
    }
    
    /**
     * Affiche le prix unitaire d'un produit
     * @param reference la référence du produit
     * @return Double
     */
    public Double prixUnitaire(int reference){
        //TODO
        return null;
    }
    
    /**
     * Affiche le nombre d'unités d'un produit en stock
     * @param reference la référence du produit
     * @return int
     */
    public int uniteEnStock(int reference){
        //TODO
        return 0;
    }
    
    /**
     * Affiche le nombre d'unités commandées d'un produit
     * @param reference la référence du produit
     * @return int
     */
    public int uniteCommandes(int reference){
        //TODO
        return 0;
    }
    
    /**
     * Affiche le niveau de réprovisionnement d'un produit
     * @param reference la référence du produit
     * @return int
     */
    public int niveauReaprovisionnement(int reference){
        //TODO
        return 0;
    }
    
    /**
     * Affiche si un produit est Disponible ou non
     * @param reference la référence du produit
     * @return Boolean
     */
    public boolean estDisponible(int reference){
        //TODO
        return false;
    }
}
