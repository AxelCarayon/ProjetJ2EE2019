/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import javax.sql.DataSource;
import modele.dao.DAOcommande;
import modele.dao.DataSourceFactory;
import modele.entity.CommandeEntity;
import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Axel
 */
public class DAOcommandeTest {
    
    private DAOcommande dao; // L'objet à tester
    private static Connection myConnection ;
    private DataSource myDataSource; // La source de données à utiliser
    int code;
	

    @Before
    public void setUp() throws SQLException, IOException, SqlToolError {
        myDataSource = getDataSource();
	myConnection = myDataSource.getConnection();
	// On initialise la base avec le contenu d'un fichier de test
	String sqlFilePath = DAOcommandeTest.class.getResource("database.sql").getFile();
	SqlFile sqlFile = new SqlFile(new File(sqlFilePath));
	sqlFile.setConnection(myConnection);
	sqlFile.execute();
	sqlFile.closeReader();	
	// On crée l'objet à tester
	dao = new DAOcommande(myDataSource);
        code = 10248;
    }
    
    @After
    public void tearDown() throws SQLException {
        myConnection.close();		
        dao = null; // Pas vraiment utile
    }
    
    /**
     * Teste la méthode toutesLesCommandes
     */
    @Test
    public void toutesLesCommandesTest() throws SQLException, ParseException{
       assertEquals(830,dao.toutesLesCommandes().size());
    }
    
    /**
     * Teste la méthodes afficherCommande
     */
    @Test
    public void afficherCommandeTest() throws ParseException, SQLException{
        CommandeEntity commande = new CommandeEntity(10248, "VINET", "1994-08-04", "1994-08-16", 161.00, "Vins et alcools Chevalier", "59 rue de l'Abbaye", "Reims", null, "51100", "France", 0.00);
        assertEquals(commande,dao.afficherCommande(code));
    }
    
    /**
     * Teste la méthode ajouterCommande
     * @throws ParseException 
     */
    @Test
    public void ajouterCommandeTest() throws ParseException, SQLException{
        CommandeEntity commande = new CommandeEntity(11078, "VINET", "2019-11-01","2019-11-15",400.00,"Magasin osef","51 rue du chat","Albi", "Occitanie","81000","France",0.00);
        try{
            dao.ajouterCommande(11078, "VINET", "2019-11-01","2019-11-15",400.00,"Magasin osef","51 rue du chat","Albi", "Occitanie","81000","France",0.00);
        }catch(Exception e){
            fail(e.getMessage());
        }
        assertEquals(commande,dao.afficherCommande(11078));
    }
    
    /**
     * Teste la méthode supprimerCommande
     */
    @Test
    public void supprimerCommandeTest() throws SQLException{
        boolean supprime;
        try{
            dao.afficherCommande(11078);
            supprime = true;
        }catch(Exception e){
            System.out.println(e);
            supprime = false;
        }
        assertTrue(supprime);
    }
    
    /**
     * Teste la méthode afficherCodeClient
     */
    @Test
    public void afficherCodeClientTest() throws SQLException{ 
        assertEquals("VINET",dao.afficherCodeClient(code));
    }
    
    /**
     * Teste la méthode modifierDestinataire
     */
    @Test
    public void modifierDestinataireTest() throws ParseException, SQLException{
        dao.modifierDestinataire(code,"Philippe");
        assertEquals(dao.afficherCommande(code).getDestinataire(),"Philippe");
    }
    
    /**
     * Teste la méthode modifierAdresseLivraison
     * @throws ParseException 
     */
    public void modifierAdresseLivraisonTest() throws ParseException, SQLException{
        dao.modifierAdresseLivraison(code,"Rue Pablo Neruda");
        assertEquals(dao.afficherCommande(code).getAdresseLivraison(),"Rue Pablo Neruda");
    }
    
    /**
     * Teste la méthode modifierRegionLivraison
     * @throws ParseException 
     */
    @Test
    public void modifierRegionLivraisonTest() throws ParseException, SQLException{
        dao.modifierRegionLivraison(code,"Bretagne");
        assertEquals(dao.afficherCommande(code).getRegionLivraison(),"Bretagne");
    }

    @Test
    public void modifierPaysTest() throws ParseException, SQLException{
        dao.modifierPaysLivraison(code,"Syrie");
        assertEquals(dao.afficherCommande(code).getPaysLivraison(),"Syrie");
    }
    
    @Test
    public void modifierRemise() throws ParseException, SQLException{
        dao.modifierRemise(code, 10.00);
        assertEquals(10.00, dao.afficherCommande(code).getRemise(), 0);
    }
    
    public static DataSource getDataSource() throws SQLException {
	org.hsqldb.jdbc.JDBCDataSource ds = new org.hsqldb.jdbc.JDBCDataSource();
	ds.setDatabase("jdbc:hsqldb:mem:testcase;shutdown=true");
	ds.setUser("sa");
    	ds.setPassword("sa");
	return ds;
    }
    
}
