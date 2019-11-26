/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
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
        
        public List<ClientEntity> tousLesClients() throws SQLException{
            List<ClientEntity> result = new LinkedList<>();

		String sql = "SELECT * FROM CLIENT";
		try (Connection connection = myDataSource.getConnection();
                     PreparedStatement stmt = connection.prepareStatement(sql)) {
                        ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
                            String code = rs.getString("CODE");
                            String societe = rs.getString("SOCIETE");
                            String contact = rs.getString("CONTACT");
                            String fonction = rs.getString("FONCTION");
                            String adresse = rs.getString("ADRESSE");
                            String ville = rs.getString("VILLE");
                            String region = rs.getString("REGION");
                            String code_postal = rs.getString("CODE_POSTAL");
                            String pays = rs.getString("PAYS");
                            String telephone = rs.getString("TELEPHONE");
                            String fax = rs.getString("FAX");
                            ClientEntity c = new ClientEntity(code,societe,contact,fonction,adresse,ville,region,code_postal,pays,telephone,fax);
                            result.add(c);
			}
		}
		return result;
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
