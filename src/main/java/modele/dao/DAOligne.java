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
    
    public List<LigneEntity> toutesLesLignes(){
        //TODO
        return null;
    }
    
    public CommandeEntity afficherCommande(){
        //TODO
        return null;
    }
    
    public ProduitEntity afficherProduit(){
        //TODO
        return null;
    }
    
    public int afficherQuantite(int commande, int produit){
        //TODO
        return 0;
    }
    
}
