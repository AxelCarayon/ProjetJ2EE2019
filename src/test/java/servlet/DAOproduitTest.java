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
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import modele.dao.DAOproduit;
import modele.entity.ProduitEntity;
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
public class DAOproduitTest {
    
    private DAOproduit dao; // L'objet à tester
    private DataSource myDataSource; // La source de données à utiliser
    private static Connection myConnection ;
    private ProduitEntity produit;
    private int code;
	

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
	dao = new DAOproduit(myDataSource);
        
        produit = new ProduitEntity(1, "Chai", 1, 1, "10 boîtes x 20 sacs", 90.00, 39, 0, 10, 0);
        code = 1;
    }
    
    @After
    public void tearDown() throws SQLException {
        myConnection.close();		
        dao = null; // Pas vraiment utile
    }
    
    /**
     * Teste la méthode tousLesProduits
     */
    @Test
    public void tousLesProduitsTest() throws SQLException{
        assertEquals(77,dao.tousLesProduits().size());
    }
    
    /**
     * Teste la méthode afficherProduit
     */
    @Test
    public void afficherProduitTest() throws SQLException{
        assertEquals(produit,dao.afficherProduit(1));
    }
    
    /**
     * Teste la méthode modifierUnitesCommandees
     */
    @Test
    public void modifierUnitesCommandeesTest() throws SQLException{
        Boolean erreurTransaction = false;
        try{
            dao.modifierUnitesCommandees(code,40);
        }catch(Exception e){
            erreurTransaction = true;
        }
        if (erreurTransaction==false){
            fail("La transaction a été effectuée avec un stock insuffisant");
        }
        
        dao.modifierUnitesCommandees(code, 1);
        assertEquals(dao.afficherProduit(code).getUnites_en_stock(),38);
    }
    
    /**
     * Teste la méthode nomProduit
     */
    @Test
    public void nomProduitTest() throws SQLException{
        assertEquals(dao.nomProduit(code),"Chai");
    }
    
    /**
     * Teste la méthode numeroFournisseur
     */
    @Test
    public void numeroFournisseurTest() throws SQLException{
        assertEquals(dao.numeroFournisseur(code),1);
    }
    
    /**
     * Teste la méthode numeroCategorie
     */
    @Test
    public void numeroCategorieTest() throws SQLException{
        assertEquals(dao.numeroCategorie(code),1);
    }
    
    /**
     * Teste la méthode quantiteParUnite
     */
    @Test
    public void quantiteParUniteTest() throws SQLException{
        assertEquals(dao.quantiteParUnite(code),"10 boîtes x 20 sacs");
    }
    
    /**
     * Teste la méthode prixUnitaire
     */
    @Test
    public void prixUnitaireTest() throws SQLException{
        assertEquals(dao.prixUnitaire(code),90.00,0);
    }
    
    /**
     * Teste la méthode uniteEnStock
     */
    @Test
    public void uniteEnStockTest() throws SQLException{
        assertEquals(dao.uniteEnStock(code),39);
    }
    
    /**
     * Teste la méthode unitesCommandees
     */
    @Test
    public void unitesCommandeesTest() throws SQLException{
        
        assertEquals(dao.uniteCommandes(code),0);
    }
    
    /**
     * Teste la méthode niveauReaprovisionnement
     */
    @Test
    public void niveauReaprovisionnementTest() throws SQLException{
        assertEquals(dao.niveauReaprovisionnement(code),10);
    }
    
    /**
     * Teste la méthode estDisponible
     */
    @Test
    public void estIndisponibleTest() throws SQLException{
        assertFalse(dao.estIndisponible(code));
    }
    
    @Test
    public void listeProduitsCategorieDisponiblesTest() throws SQLException{
        ProduitEntity p1 = dao.afficherProduit(1);
        ProduitEntity p2 = dao.afficherProduit(2);
        ProduitEntity p4 = dao.afficherProduit(34);
        ProduitEntity p5 = dao.afficherProduit(35);
        ProduitEntity p6 = dao.afficherProduit(38);
        ProduitEntity p7 = dao.afficherProduit(39);
        ProduitEntity p8 = dao.afficherProduit(43);
        ProduitEntity p9 = dao.afficherProduit(67);
        ProduitEntity p10 = dao.afficherProduit(70);
        ProduitEntity p11 = dao.afficherProduit(75);
        ProduitEntity p12 = dao.afficherProduit(76);
        
        List<ProduitEntity> resultat = new ArrayList<ProduitEntity>();
        resultat.add(p1);
        resultat.add(p2);
        resultat.add(p4);
        resultat.add(p5);
        resultat.add(p6);
        resultat.add(p7);
        resultat.add(p8);
        resultat.add(p9);
        resultat.add(p10);
        resultat.add(p11);
        resultat.add(p12);
        
        assertEquals(resultat,dao.listeProduitsCategorieDisponible(1));
        
    }
    
    @Test
    public void listeProduitsCategorieTest() throws SQLException{
        ProduitEntity p1 = dao.afficherProduit(1);
        ProduitEntity p2 = dao.afficherProduit(2);
        ProduitEntity p3 = dao.afficherProduit(24);
        ProduitEntity p4 = dao.afficherProduit(34);
        ProduitEntity p5 = dao.afficherProduit(35);
        ProduitEntity p6 = dao.afficherProduit(38);
        ProduitEntity p7 = dao.afficherProduit(39);
        ProduitEntity p8 = dao.afficherProduit(43);
        ProduitEntity p9 = dao.afficherProduit(67);
        ProduitEntity p10 = dao.afficherProduit(70);
        ProduitEntity p11 = dao.afficherProduit(75);
        ProduitEntity p12 = dao.afficherProduit(76);
        
        List<ProduitEntity> resultat = new ArrayList<ProduitEntity>();
        resultat.add(p1);
        resultat.add(p2);
        resultat.add(p3);
        resultat.add(p4);
        resultat.add(p5);
        resultat.add(p6);
        resultat.add(p7);
        resultat.add(p8);
        resultat.add(p9);
        resultat.add(p10);
        resultat.add(p11);
        resultat.add(p12);
        
        assertEquals(resultat,dao.listeProduitsCategorie(1));
        
    }
    
    @Test
    public void ajouterProduitTest() throws SQLException{
        ProduitEntity p = new ProduitEntity(78,"machin", 1, 1, "6x25cl", 10.00, 100, 25, 10, 0);
        dao.ajouterProduit("machin", 1, 1, "6x25cl", 10.00, 100, 25, 10, false);
        assertEquals(dao.afficherProduit(78),p);
    }
    
    @Test
    public void supprimerProduitTest() throws SQLException{
        dao.ajouterProduit("machin", 1, 1, "6x25cl", 10.00, 100, 25, 10, false);
        Boolean supprime = true;
        try{
           dao.supprimerProduit(78); 
        }catch(Exception e){
            supprime = false;
        }
        if (supprime == false){
            fail();
        }
        
        
        supprime = true;
        try{
            dao.supprimerProduit(1); // un produit utilisé ailleurs
        }catch(Exception e){
            supprime = false;
        }
        if (supprime == true){
            fail();
        }
    }
    
    
    
    public static DataSource getDataSource() throws SQLException {
		org.hsqldb.jdbc.JDBCDataSource ds = new org.hsqldb.jdbc.JDBCDataSource();
		ds.setDatabase("jdbc:hsqldb:mem:testcase;shutdown=true");
		ds.setUser("sa");
		ds.setPassword("sa");
		return ds;
	}
    
}
