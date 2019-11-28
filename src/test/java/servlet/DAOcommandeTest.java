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
    @Ignore 
    @Test
    public void toutesLesCommandesTest(){
       List<CommandeEntity> listeCommandes = dao.toutesLesCommandes();
       assertEquals(830,listeCommandes.size());
    }
    
    /**
     * Teste la méthodes afficherCommande
     */
    @Ignore
    @Test
    public void afficherCommandeTest() throws ParseException{
        CommandeEntity commande = new CommandeEntity(10248, "VINET", "1994-08-04", "1994-08-16", 161.00, "Vins et alcools Chevalier", "59 rue de l'Abbaye", "Reims", null, "51100", "France", 0.00);
        assertEquals(commande,dao.afficherCommande(code));
    }
    
    /**
     * Teste la méthode ajouterCommande
     * @throws ParseException 
     */
    @Ignore
    @Test
    public void ajouterCommandeTest() throws ParseException{
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
    @Ignore
    @Test
    public void supprimerCommandeTest(){
        boolean supprime = false;
        dao.supprimerCommande(11078);
        try{
            dao.afficherCommande(11078);
        }catch(Exception e){
            supprime = true;
        }
        assertTrue(supprime);
    }
    
    /**
     * Teste la méthode afficherCodeClient
     */
    @Ignore
    @Test
    public void afficherCodeClientTest(){ 
        assertEquals("VINET",dao.afficherCodeClient(code));
    }
    
    /**
     * Teste la méthode modifierDestinataire
     */
    @Ignore
    @Test
    public void modifierDestinataireTest() throws ParseException{
        dao.modifierDestinataire(code,"Philippe");
        assertEquals(dao.afficherCommande(code).getDestinataire(),"Philippe");
    }
    
    /**
     * Teste la méthode modifierAdresseLivraison
     * @throws ParseException 
     */
    public void modifierAdresseLivraisonTest() throws ParseException{
        dao.modifierAdresseLivraison(code,"Rue Pablo Neruda");
        assertEquals(dao.afficherCommande(code).getAdresseLivraison(),"Rue Pablo Neruda");
    }
    
    @Ignore
    @Test
    public void modifierRegionLivraisonTest() throws ParseException{
        dao.modifierRegionLivraison(code,"Bretagne");
        assertEquals(dao.afficherCommande(code).getRegionLivraison(),"Bretagne");
    }
    
    @Ignore
    @Test
    public void modifierPaysTest() throws ParseException{
        dao.modifierPaysLivraison(code,"Syrie");
        assertEquals(dao.afficherCommande(code).getPaysLivraison(),"Syrie");
    }
    
    @Ignore
    @Test
    public void modifierRemise() throws ParseException{
        dao.modifierRemise(code, 10.00);
        assertEquals(10.00, dao.afficherCommande(code).getRemise());
    }
    
    public static DataSource getDataSource() throws SQLException {
	org.hsqldb.jdbc.JDBCDataSource ds = new org.hsqldb.jdbc.JDBCDataSource();
	ds.setDatabase("jdbc:hsqldb:mem:testcase;shutdown=true");
	ds.setUser("sa");
    	ds.setPassword("sa");
	return ds;
    }
    
}
