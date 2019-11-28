package modele.dao;

import java.util.List;
import javax.sql.DataSource;
import modele.entity.CommandeEntity;
import modele.entity.LigneEntity;
import modele.entity.ProduitEntity;

/**
 *
 * @author Axel
 */
public class DAOligne {
    
    
    private final DataSource myDataSource;

    public DAOligne(DataSource dataSource) {
	this.myDataSource = dataSource;
    }
    
    /**
     * Affiche toutes les Lignes
     * @return Liste de toutes les lignes
     */
    public List<LigneEntity> toutesLesLignes(){
        //TODO
        return null;
    }
    /**
     * Affiches toutes les lignes d'une commande
     * @param commande la commande à afficher
     * @return Liste de toutes les LigneEntity d'une commande
     */
    public List<LigneEntity> afficherCommande(int commande){
        //TODO
        return null;
    }
    
    /**
     * Affiche tous les produits d'une commande
     * @param commande la commande des code produit à afficher
     * @return Liste de tout les codes produits
     */
    public List<Integer> afficherCodeProduits(int commande){
        //TODO
        return null;
    }
    
    /**
     * Affiche la quantité du produit d'une commande
     * @param commande commande voulue
     * @param produit produit de la commande voulue
     * @return quantité du produit de la commande
     */
    public int afficherQuantite(int commande, int produit){
        //TODO
        return 0;
    }
    
}
