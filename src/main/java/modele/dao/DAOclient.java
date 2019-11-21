/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao;

import java.util.List;
import javax.sql.DataSource;
import modele.entity.ClientEntity;

/**
 *
 * @author Axel
 */
public class DAOclient {
    
    
    private final DataSource myDataSource;

	/**
	 * Construit le AO avec sa source de données
	 * @param dataSource la source de données à utiliser
	 */
	public DAOclient(DataSource dataSource) {
		this.myDataSource = dataSource;
	}
        
        public List<ClientEntity> tousLesClients(){
            //TODO
            return null;
        }
        
        public ClientEntity afficherClient(String code){
            //TODO
            return null;
        }
        
        public void modifierSociete(String client, String Societe){
            //TODO
        }
        
        public void modifierContact(String client, String contact){
            //TODO
        }
        
        public void modifierFonction(String client, String fonction){
            //TODO
        }
        public void modifierAdresse(String client, String adresse){
            //TODO
        }
        
        public void modifierVille(String client, String ville){
            //TODO
        }
        
        public void modifierRegion(String client, String region){
            //TODO
        }
        
        public void modifierCodePostal(String client, String CodePostal){
            //TODO
        }
        
        public void modifierPays(String client, String pays){
            //TODO
        }
        
        public void modifierTelephone(String client, String telephone){
            //TODO
        }
        
        public void modifierFax(String client, String fax){
            //TODO
        }
    
}
