package modele.entity;

import java.util.Objects;

/**
 *
 * @author Axel
 */
public class ProduitEntity {
    
    int reference; //clef primaire
    String nom;
    int fournisseur;
    int categorie; //clef étrangère
    String quantite_par_unite;
    Double prix_unitaire;
    int unites_en_stock;
    int unites_commandes;
    int niveau_reaprovis;
    boolean indisponible;

    public ProduitEntity(int reference, String nom, int fournisseur, int categorie, String quantite_par_unite, Double prix_unitaire, int unites_en_stock, int unites_commandes, int niveau_reaprovis, int indisponible) {
        this.reference = reference;
        this.nom = nom;
        this.fournisseur = fournisseur;
        this.categorie = categorie;
        this.quantite_par_unite = quantite_par_unite;
        this.prix_unitaire = prix_unitaire;
        this.unites_en_stock = unites_en_stock;
        this.unites_commandes = unites_commandes;
        this.niveau_reaprovis = niveau_reaprovis;
        if (indisponible ==1){
            this.indisponible = true;
        }
        else{
            this.indisponible = false;
        }
    }

    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(int fournisseur) {
        this.fournisseur = fournisseur;
    }

    public int getCategorie() {
        return categorie;
    }

    public void setCategorie(int categorie) {
        this.categorie = categorie;
    }

    public String getQuantite_par_unite() {
        return quantite_par_unite;
    }

    public void setQuantite_par_unite(String quantite_par_unite) {
        this.quantite_par_unite = quantite_par_unite;
    }

    public Double getPrix_unitaire() {
        return prix_unitaire;
    }

    public void setPrix_unitaire(Double prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
    }

    public int getUnites_en_stock() {
        return unites_en_stock;
    }

    public void setUnites_en_stock(int unites_en_stock) {
        this.unites_en_stock = unites_en_stock;
    }

    public int getUnites_commandes() {
        return unites_commandes;
    }

    public void setUnites_commandes(int unites_commandes) {
        this.unites_commandes = unites_commandes;
    }

    public int getNiveau_reaprovis() {
        return niveau_reaprovis;
    }

    public void setNiveau_reaprovis(int niveau_reaprovis) {
        this.niveau_reaprovis = niveau_reaprovis;
    }

    public boolean isIndisponible() {
        return indisponible;
    }

    public void setIndisponible(boolean indisponible) {
        this.indisponible = indisponible;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + this.reference;
        hash = 31 * hash + Objects.hashCode(this.nom);
        hash = 31 * hash + this.fournisseur;
        hash = 31 * hash + this.categorie;
        hash = 31 * hash + Objects.hashCode(this.quantite_par_unite);
        hash = 31 * hash + Objects.hashCode(this.prix_unitaire);
        hash = 31 * hash + this.unites_en_stock;
        hash = 31 * hash + this.unites_commandes;
        hash = 31 * hash + this.niveau_reaprovis;
        hash = 31 * hash + (this.indisponible ? 1 : 0);
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
        final ProduitEntity other = (ProduitEntity) obj;
        if (this.reference != other.reference) {
            return false;
        }
        if (this.fournisseur != other.fournisseur) {
            return false;
        }
        if (this.categorie != other.categorie) {
            return false;
        }
        if (this.unites_en_stock != other.unites_en_stock) {
            return false;
        }
        if (this.unites_commandes != other.unites_commandes) {
            return false;
        }
        if (this.niveau_reaprovis != other.niveau_reaprovis) {
            return false;
        }
        if (this.indisponible != other.indisponible) {
            return false;
        }
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.quantite_par_unite, other.quantite_par_unite)) {
            return false;
        }
        if (!Objects.equals(this.prix_unitaire, other.prix_unitaire)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProduitEntity{" + "reference=" + reference + ", nom=" + nom + ", fournisseur=" + fournisseur + ", categorie=" + categorie + ", quantite_par_unite=" + quantite_par_unite + ", prix_unitaire=" + prix_unitaire + ", unites_en_stock=" + unites_en_stock + ", unites_commandes=" + unites_commandes + ", niveau_reaprovis=" + niveau_reaprovis + ", indisponible=" + indisponible + '}';
    }
    
}
