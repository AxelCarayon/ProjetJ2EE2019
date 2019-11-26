package modele.dao;

import java.util.List;
import javax.sql.DataSource;
import modele.entity.CategorieEntity;
import modele.entity.ProduitEntity;

/**
 *
 * @author Axel
 */
public class DAOcategorie {
    
    private final DataSource myDataSource;

	/**
	 * Construit le AO avec sa source de données
	 * @param dataSource la source de données à utiliser
	 */
	public DAOcategorie(DataSource dataSource) {
		this.myDataSource = dataSource;
	}
        
        public List<CategorieEntity> toutesLesCategories(){
            //TODO
            return null;
        }
        
        public CategorieEntity afficherCategorie(){
            //TODO
            return null;
        }
        
        public ProduitEntity afficherProduit(int code){
            //TODO
            return null;
        }
        
        public String afficherLibelle(int code){
            //TODO
            return null;
        }
        
        public String afficherDescription(int code){
            //TODO
            return null;
        }
       
}
