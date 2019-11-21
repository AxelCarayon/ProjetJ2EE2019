/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao;

import java.util.List;
import javax.sql.DataSource;
import modele.entity.CategorieEntity;
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
    
    public List<ProduitEntity> tousLesProduits(){
        //TODO
        return null;
    }
    
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
    
    public String nomProduit(int reference){
        //TODO
        return null;
    }
    
    public int numeroFournisseur(int reference){
        //TODO
        return 0;
    }
    
    public CategorieEntity categorie(int reference){
        //TODO
        return null;
    }
    
    public int quantiteParUnite(int reference){
        //TODO
        return 0;
    }
    
    public Double prixUnitaire(int reference){
        //TODO
        return null;
    }
    
    public int uniteEnStock(int reference){
        //TODO
        return 0;
    }
    
    public int uniteCommandes(int reference){
        //TODO
        return 0;
    }
    
    public int niveauReaprovisionnement(int reference){
        //TODO
        return 0;
    }
    
    public boolean estDisponible(int reference){
        //TODO
        return false;
    }
}
