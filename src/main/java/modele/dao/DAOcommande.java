/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao;

import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import modele.entity.ClientEntity;
import modele.entity.CommandeEntity;

/**
 *
 * @author Axel
 */
public class DAOcommande {
    
    private final DataSource myDataSource;

	/**
	 * Construit le AO avec sa source de données
	 * @param dataSource la source de données à utiliser
	 */
	public DAOcommande(DataSource dataSource) {
		this.myDataSource = dataSource;
	}
        
        public List<CommandeEntity> toutesLesCommandes(){
            //TODO
            return null;
        }
        
        public CommandeEntity afficherCommande(int numero){
            //TODO
            return null;
        }
        
        public void ajouterCommande(String code_client, String saisiLe, String envoyeLe,
    double port, String destinataire, String adresseLivraison, String villeLivraison,
    String regionLivraison, String codePostalLivraison, String paysLivraison, double remise){
            //TODO
        }
        
        public void supprimerCommande(int numero){
            //TODO
        }
        
        public ClientEntity afficherClient(){
            //TODO
            return null;
        }
        
        public void modifierDestinataire(int numero, String destinataire){
            //TODO
        }
        
        public void modifierAdresseLivraison(int numero, String adresseLivraison){
            //TODO
        }
        
        public void modifierRegionLivraison(int numero, String regionLivraison){
            //TODO
        }
        
        public void modifierCodePostalLivraison(int numero, String codePostalLivraison){
            //TODO
        }
        
        public void modifierPaysLivraison(int numero, String paysLivraison){
            //TODO
        }
        
        public void modifierRemise(int numero, double remise){
            //TODO
        }
        
    
}
