/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import static servlet.DAOclientTest.getDataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;
import modele.dao.DAOligne;
import modele.entity.LigneCommandeEntity;
import modele.entity.LigneEntity;
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
public class DAOligneTest {
    
    private DAOligne dao; // L'objet à tester
    private DataSource myDataSource; // La source de données à utiliser
    private static Connection myConnection ;
	

    @Before
    public void setUp() throws SQLException, IOException, SqlToolError {
        myDataSource = getDataSource();
	myConnection = myDataSource.getConnection();
	// On initialise la base avec le contenu d'un fichier de test
	String sqlFilePath = DAOclientTest.class.getResource("database.sql").getFile();
	SqlFile sqlFile = new SqlFile(new File(sqlFilePath));
	sqlFile.setConnection(myConnection);
	sqlFile.execute();
	sqlFile.closeReader();	
	// On crée l'objet à tester
	dao = new DAOligne(myDataSource);
    }
    
    @After
    public void tearDown() throws SQLException {
        myConnection.close();		
        dao = null; // Pas vraiment utile
    }
    
    /**
     * Teste la méthode afficherCommande
     */
    @Test
    public void afficherCommandesTest() throws SQLException{
        List<LigneCommandeEntity> lcommandes = new ArrayList<>();
        LigneCommandeEntity l1 = new LigneCommandeEntity(new LigneEntity(10248,11,12),"Queso Cabrales",105.0,0.0,1260.0,"Produit laitiers");
        LigneCommandeEntity l2 = new LigneCommandeEntity(new LigneEntity(10248,42,10),"Singaporean Hokkien Fried Mee",70.0,0.0,700.0,"Pâtes et céréales");
        LigneCommandeEntity l3 = new LigneCommandeEntity(new LigneEntity(10248,72,5),"Mozzarella di Giovanni",174.0,0.0,870.0,"Produit laitiers");
        lcommandes.add(l1);
        lcommandes.add(l2);
        lcommandes.add(l3);
        assertEquals(lcommandes,dao.afficherCommande(10248));
    }
    
    /**
     * Teste la méthode toutesLesLignes
     */
    @Test
    public void toutesLesLignesTest() throws SQLException{
        assertEquals(dao.toutesLesLignes().size(),2155);
    }
    
    /**
     * Teste la méthode afficherCodeProduits
     */
    @Test
    public void afficherCodeProduitsTest() throws SQLException{
        List<Integer> lProduits = new ArrayList<Integer>();
        lProduits.add(11);
        lProduits.add(42);
        lProduits.add(72);
        assertEquals(lProduits,dao.afficherCodeProduits(10248));
    }
    
    /**
     * Teste la méthode afficherQuantite
     */
    @Test
    public void afficherQuantiteTest() throws SQLException{
        assertEquals(12,dao.afficherQuantite(10248, 11));
    }
    
    @Test
    public void ajouterLigneTest() throws SQLException{
        dao.ajouterLigne(10248,2,3);
        assertEquals(dao.toutesLesLignes().size(),2156);
    }
    
    @Test
    public void supprimerLigneTest() throws SQLException{
        dao.supprimerLigne(10248, 11, 12);
        assertEquals(dao.toutesLesLignes().size(),2154);
    }
    
    public static DataSource getDataSource() throws SQLException {
		org.hsqldb.jdbc.JDBCDataSource ds = new org.hsqldb.jdbc.JDBCDataSource();
		ds.setDatabase("jdbc:hsqldb:mem:testcase;shutdown=true");
		ds.setUser("sa");
		ds.setPassword("sa");
		return ds;
	}
    
}
