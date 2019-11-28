/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static dao.DAOclientTest.getDataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;
import modele.dao.DAOclient;
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
    @Ignore
    @Test
    public void afficherCommandesTest(){
        List<LigneEntity> lcommandes = new LinkedList();
        LigneEntity l1 = new LigneEntity(10248,11,12);
        LigneEntity l2 = new LigneEntity(10248,42,10);
        LigneEntity l3 = new LigneEntity(10248,72,5);
        lcommandes.add(l1);
        lcommandes.add(l2);
        lcommandes.add(l3);
        assertEquals(lcommandes,dao.afficherCommande(10248));
    }
    
    /**
     * Teste la méthode toutesLesLignes
     */
    @Ignore
    @Test
    public void toutesLesLignesTest(){
        assertEquals(dao.toutesLesLignes().size(),2155);
    }
    
    /**
     * Teste la méthode afficherCodeProduits
     */
    @Ignore
    @Test
    public void afficherCodeProduitsTest(){
        List<Integer> lProduits = new LinkedList<Integer>();
        lProduits.add(11);
        lProduits.add(42);
        lProduits.add(72);
        assertEquals(lProduits,dao.afficherCodeProduits(10248));
    }
    
    /**
     * Teste la méthode afficherQuantite
     */
    @Ignore
    @Test
    public void afficherQuantiteTest(){
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
