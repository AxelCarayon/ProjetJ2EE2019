/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.SQLException;
import javax.sql.DataSource;
import modele.dao.DAOcommande;
import modele.dao.DataSourceFactory;
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
    private DataSource myDataSource; // La source de données à utiliser
	

    @Ignore
    @Before
    public void setUp() throws SQLException {
        myDataSource = DataSourceFactory.getDataSource();
        dao = new DAOcommande(myDataSource);
    }
    
}
