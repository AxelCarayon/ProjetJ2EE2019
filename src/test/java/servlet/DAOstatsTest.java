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
import javax.sql.DataSource;
import modele.dao.DAOligne;
import modele.dao.DAOstats;
import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import static servlet.DAOligneTest.getDataSource;

/**
 *
 * @author Axel
 */
public class DAOstatsTest {

    private DAOstats dao; // L'objet à tester
    private DataSource myDataSource; // La source de données à utiliser
    private static Connection myConnection;

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
        dao = new DAOstats(myDataSource);
    }
    
    
    @Test
    public void CApourCategorieTest() throws SQLException{
        Double ca = 90.*15.+
                1317.*40.+
                90.*20.+
                75.*30.+
                22.*15.+
                70.*10.+
                22.*15.+
                75.*30.+
                90.*40.+
                95.*25.+
                90.*30.+
                90.*35.+
                90.*35.;
        
        assertEquals(ca,dao.CApourCategorie(1, "1995-01-01", "1995-02-01"),0);
    }
    
    @Test
    public void CAparPaysTest() throws SQLException{
        Double ca = 30.*18.+
                45.*40.+
                107.*60.+
                174.*21.;
        
        assertEquals(ca, dao.CAparPays("aLlEmAGne","1995-01-01", "1995-02-01"),0);
    }
    
    @Test
    public void CAparClientTest() throws SQLException{
        Double ca = 50.*6.+
                90.*15.;
        
        assertEquals(ca, dao.CAparClient("alfki", "1995-01-01", "1996-01-01"),0);
    }

    @After
    public void tearDown() throws SQLException {
        myConnection.close();
        dao = null; // Pas vraiment utile
    }

    public static DataSource getDataSource() throws SQLException {
        org.hsqldb.jdbc.JDBCDataSource ds = new org.hsqldb.jdbc.JDBCDataSource();
        ds.setDatabase("jdbc:hsqldb:mem:testcase;shutdown=true");
        ds.setUser("sa");
        ds.setPassword("sa");
        return ds;
    }

}
