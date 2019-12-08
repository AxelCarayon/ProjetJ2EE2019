/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.entity;

import java.util.Objects;

/**
 *
 * @author Axel
 */
public class LigneCommandeEntity {
    
    LigneEntity ligne;
    String nom;
    Double prixUnitaire;
    Double remise;
    Double prixTotal;
    String categorie;

    public LigneCommandeEntity(LigneEntity ligne, String nom, Double prixUnitaire, Double remise, Double prixTotal, String categorie) {
        this.ligne = ligne;
        this.nom = nom;
        this.prixUnitaire = prixUnitaire;
        this.remise = remise;
        this.prixTotal = prixTotal;
        this.categorie = categorie;
    }

    public LigneEntity getLigne() {
        return ligne;
    }

    public String getNom() {
        return nom;
    }

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public Double getRemise() {
        return remise;
    }

    public Double getPrixTotal() {
        return prixTotal;
    }

    public String getCategorie() {
        return categorie;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.ligne);
        hash = 43 * hash + Objects.hashCode(this.nom);
        hash = 43 * hash + Objects.hashCode(this.prixUnitaire);
        hash = 43 * hash + Objects.hashCode(this.remise);
        hash = 43 * hash + Objects.hashCode(this.prixTotal);
        hash = 43 * hash + Objects.hashCode(this.categorie);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LigneCommandeEntity other = (LigneCommandeEntity) obj;
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.categorie, other.categorie)) {
            return false;
        }
        if (!Objects.equals(this.ligne, other.ligne)) {
            return false;
        }
        if (!Objects.equals(this.prixUnitaire, other.prixUnitaire)) {
            return false;
        }
        if (!Objects.equals(this.remise, other.remise)) {
            return false;
        }
        if (!Objects.equals(this.prixTotal, other.prixTotal)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LigneCommandeEntity{" + "ligne=" + ligne + ", nom=" + nom + ", prixUnitaire=" + prixUnitaire + ", remise=" + remise + ", prixTotal=" + prixTotal + ", categorie=" + categorie + '}';
    }
    
}
