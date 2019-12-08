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
        List lcommandes = new ArrayList();
        List l1 = new ArrayList();
        l1.add("Queso Cabrales");
        l1.add(105.0);
        l1.add(0.0);
        l1.add(1260.0);
        l1.add("Produit laitiers");
        List l2 = new ArrayList();
        l2.add("Singaporean Hokkien Fried Mee");
        l2.add(70.0);
        l2.add(0.0);
        l2.add(700.0);
        l2.add("Pâtes et céréales");
        List l3 = new ArrayList();
        l3.add("Mozzarella di Giovanni");
        l3.add(174.0);
        l3.add(0.0);
        l3.add(870.0);
        l3.add("Produit laitiers");
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
    
    public static DataSource getDataSource() throws SQLException {
		org.hsqldb.jdbc.JDBCDataSource ds = new org.hsqldb.jdbc.JDBCDataSource();
		ds.setDatabase("jdbc:hsqldb:mem:testcase;shutdown=true");
		ds.setUser("sa");
		ds.setPassword("sa");
		return ds;
	}
    
}
