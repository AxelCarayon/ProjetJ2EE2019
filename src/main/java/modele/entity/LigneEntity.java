package modele.entity;

/**
 *
 * @author Axel
 */
public class LigneEntity {
    
    int commande; //clef étrangère
    int produit; // clef étrangère 
    int quantite;

    public LigneEntity(int commande, int produit, int quantite) {
        this.commande = commande;
        this.produit = produit;
        this.quantite = quantite;
    }

    public int getCommande() {
        return commande;
    }

    public void setCommande(int commande) {
        this.commande = commande;
    }

    public int getProduit() {
        return produit;
    }

    public void setProduit(int produit) {
        this.produit = produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.commande;
        hash = 89 * hash + this.produit;
        hash = 89 * hash + this.quantite;
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
        final LigneEntity other = (LigneEntity) obj;
        if (this.commande != other.commande) {
            return false;
        }
        if (this.produit != other.produit) {
            return false;
        }
        if (this.quantite != other.quantite) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LigneEntity{" + "commande=" + commande + ", produit=" + produit + ", quantite=" + quantite + '}';
    }
    
}
