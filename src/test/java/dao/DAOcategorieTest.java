/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import modele.dao.DAOcategorie;
import modele.dao.DataSourceFactory;
import modele.entity.CategorieEntity;
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
    private DataSource myDataSource; // La source de données à utiliser
	

    @Before
    public void setUp() throws SQLException {
        myDataSource = DataSourceFactory.getDataSource();
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
    
}
