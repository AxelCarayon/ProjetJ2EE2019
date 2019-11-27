/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import modele.dao.DAOcategorie;
import modele.entity.CategorieEntity;
import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Axel
 */
public class DAOcategorieTest {
    
    private DAOcategorie dao; // L'objet à tester
    private static Connection myConnection ;
    private DataSource myDataSource; // La source de données à utiliser
	

    @Before
    public void setUp() throws SQLException, IOException, SqlToolError {
        myDataSource = getDataSource();
	myConnection = myDataSource.getConnection();
	// On initialise la base avec le contenu d'un fichier de test
	String sqlFilePath = DAOcategorieTest.class.getResource("database.sql").getFile();
	SqlFile sqlFile = new SqlFile(new File(sqlFilePath));
	sqlFile.setConnection(myConnection);
	sqlFile.execute();
	sqlFile.closeReader();	
	// On crée l'objet à tester
	dao = new DAOcategorie(myDataSource);
    }
    
    /**
     * Teste la méthode toutesLesCategories
     * @throws SQLException 
     */
    @Ignore
    @Test
    public void toutesLesCategoriesTest() throws SQLException {
        List<CategorieEntity> listeCatégories = dao.toutesLesCategories();
        assertEquals(8,listeCatégories);
    }
    
    /**
     * Teste la méthode afficherCategorie
     * @throws SQLException 
     */
    @Ignore
    @Test
    public void afficherCategorieTest() throws SQLException {
        CategorieEntity categorie = new CategorieEntity(1, "Boissons", "Boissons, cafés, thés, bières");
        assertEquals(categorie,dao.afficherCategorie(1));
    }
    
    /**
     * Teste la méthode afficherLibelle
     * @throws SQLException 
     */
    @Ignore
    @Test
    public void afficherLibelleTest() throws SQLException {
        assertEquals(dao.afficherLibelle(1),"Boissons");
    }
    
    /**
     * Teste la méthode afficherDescription
     * @throws SQLException 
     */
    @Ignore
    @Test
    public void afficherDescriptionTest() throws SQLException {
        assertEquals(dao.afficherDescription(1),"Boissons, cafés, thés, bières");
    }
    
    public static DataSource getDataSource() throws SQLException {
        org.hsqldb.jdbc.JDBCDataSource ds = new org.hsqldb.jdbc.JDBCDataSource();
        ds.setDatabase("jdbc:hsqldb:mem:testcase;shutdown=true");
        ds.setUser("sa");
        ds.setPassword("sa");
        return ds;
    }
    
}
