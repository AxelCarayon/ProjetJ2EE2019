/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import modele.dao.DAOclient;
import modele.dao.DataSourceFactory;
import modele.entity.ClientEntity;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Axel
 */
public class DAOclientTest {
    
    private DAOclient dao; // L'objet à tester
    private DataSource myDataSource; // La source de données à utiliser
    private String code; //Le code client qu'on utilise dans les jeux de tests
	

    @Before
    public void setUp() throws SQLException {
        myDataSource = DataSourceFactory.getDataSource();
        dao = new DAOclient(myDataSource);
        code = "ALFKI";
    }
    
    /**
     * Teste la méthode tousLesClients
     * @throws SQLException 
     */
    @Test
    public void testToutLesClientsTest() throws SQLException {
        List<ClientEntity> listeclients = dao.tousLesClients();
        assertEquals(91,listeclients.size());
    }
    
    /**
     * teste la méthode afficherClient
     * @throws SQLException 
     */
    @Test
    public void afficherClientTest() throws SQLException {
        ClientEntity client = new ClientEntity("ALFKI", "Alfreds Futterkiste", "Maria Anders", "Représentant(e)", "Obere Str. 57", "Berlin", null, "12209", "Allemagne", "030-0074321", "030-0076545");
        assertEquals(client,dao.afficherClient(code));
    }
    
    /**
     * teste la méthode modifierSociete
     * @throws SQLException 
     */
    @Test
    public void modifierSocieteTest() throws SQLException{
        String ancien = dao.afficherClient(code).getSociete();
        String nouveau = "Université Champollion";
        dao.modifierSociete(code,nouveau);
        ClientEntity client = dao.afficherClient(code);
        assertEquals(nouveau,client.getSociete());
        dao.modifierSociete(code,ancien);
    }
    
    /**
     * teste la méthode modifierContact
     * @throws SQLException 
     */
    @Test
    public void modifierContactTest() throws SQLException{
        String ancien = dao.afficherClient(code).getContact();
        String nouveau = "Dupont Dupond";
        dao.modifierContact(code, nouveau);
        ClientEntity client = dao.afficherClient(code);
        assertEquals(nouveau,client.getContact());
        dao.modifierContact(code,ancien);
    }
    
    /**
     * teste la méthode modifierFonction
     * @throws SQLException 
     */
    @Test
    public void modifierFonctionTest() throws SQLException{
        String ancien = dao.afficherClient(code).getFonction();
        String nouveau = "Directeur";
        dao.modifierFonction(code, nouveau);
        ClientEntity client = dao.afficherClient(code);
        assertEquals(nouveau,client.getFonction());
        dao.modifierFonction(code, ancien);
    }
    
    /**
     * teste la méthode modifierAdresse
     * @throws SQLException 
     */
    @Test
    public void modifierAdresseTest() throws SQLException{
        String ancien = dao.afficherClient(code).getAdresse();
        String nouveau = "15 rue machin";
        dao.modifierAdresse(code, nouveau);
        ClientEntity client = dao.afficherClient(code);
        assertEquals(nouveau,client.getAdresse());
        dao.modifierAdresse(code, ancien);
    }
    
    /**
     * Test méthode modifierVille
     * @throws SQLException 
     */
    @Test
    public void modifierVilleTest() throws SQLException{
        String ancien = dao.afficherClient(code).getVille();
        String nouveau = "Albi";
        dao.modifierVille(code, nouveau);
        ClientEntity client = dao.afficherClient(code);
        assertEquals(nouveau,client.getVille());
        dao.modifierVille(code, ancien);
    }
    
    /**
     * Test méthode modifierRégion
     * @throws SQLException 
     */
    @Test
    public void modifierRegionTest() throws SQLException{
        String ancien = dao.afficherClient(code).getRegion();
        String nouveau = "Tarn";
        dao.modifierRegion(code,nouveau);
        ClientEntity client = dao.afficherClient(code);
        assertEquals(nouveau,client.getRegion());
        dao.modifierRegion(code, ancien);
    }
    
    /**
     * Teste méthode modifierCodePostal
     * @throws SQLException 
     */
    @Test
    public void modifierCodePostalTest() throws SQLException{
        String ancien = dao.afficherClient(code).getCode_postal();
        String nouveau = "81380";
        dao.modifierCodePostal(code, nouveau);
        ClientEntity client = dao.afficherClient(code);
        assertEquals(nouveau,client.getCode_postal());
        dao.modifierCodePostal(code, ancien);
    }
    /**
     * Test méthode modifierVille
     * @throws SQLException 
     */
    @Test
    public void modifierPaysTest() throws SQLException{
        String ancien = dao.afficherClient(code).getPays();
        String nouveau = "France";
        dao.modifierPays(code, nouveau);
        ClientEntity client = dao.afficherClient(code);
        assertEquals(nouveau,client.getPays());
        dao.modifierPays(code, ancien);
    }
    
    /**
     * Test méthode modifierTelephone
     * @throws SQLException 
     */
    @Test
    public void modifierTelephoneTest() throws SQLException{
        String ancien = dao.afficherClient(code).getPays();
        String nouveau = "0102030405";
        dao.modifierTelephone(code, nouveau);
        ClientEntity client = dao.afficherClient(code);
        assertEquals(nouveau,client.getTelephone());
        dao.modifierTelephone(code, ancien);
    }
    
    /**
     * Teste méthode modifierFax
     * @throws SQLException 
     */
    @Test
    public void modifierFaxTest() throws SQLException{
        String ancien = dao.afficherClient(code).getFax();
        String nouveau = "0102030405";
        dao.modifierFax(code, nouveau);
        ClientEntity client = dao.afficherClient(code);
        assertEquals(nouveau,client.getFax());
        dao.modifierFax(code,ancien);
    }   
}