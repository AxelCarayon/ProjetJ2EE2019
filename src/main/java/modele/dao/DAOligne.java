package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import modele.entity.LigneCommandeEntity;
import modele.entity.LigneEntity;

/**
 *
 * @author Axel
 */
public class DAOligne {

    private final DataSource myDataSource;

    public DAOligne(DataSource dataSource) {
        this.myDataSource = dataSource;
    }

    /**
     * Affiche toutes les Lignes
     *
     * @return Liste de toutes les lignes
     */
    public List<LigneEntity> toutesLesLignes() throws SQLException {
        List<LigneEntity> resultat = new ArrayList<LigneEntity>();
        String sql = "SELECT * FROM LIGNE";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int code = rs.getInt("COMMANDE");
                int produit = rs.getInt("PRODUIT");
                int quantite = rs.getInt("QUANTITE");
                LigneEntity l = new LigneEntity(code, produit, quantite);
                resultat.add(l);
            }
        }catch(Exception e){
            throw e;
        }
        return resultat;
    }

    /**
     * Affiches toutes les lignes d'une commande
     *
     * @param commande la commande à afficher
     * @return Liste de toutes les LigneEntity d'une commande
     */
    public List<LigneCommandeEntity> afficherCommande(int commande) throws SQLException {
        List<LigneCommandeEntity> resultat = new ArrayList();
        String sql = "SELECT PRODUIT,QUANTITE FROM LIGNE WHERE COMMANDE = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1,commande);
            ResultSet rs = stmt.executeQuery();
            DAOproduit daoProduit = new DAOproduit(this.myDataSource);
            DAOcategorie daoCategorie = new DAOcategorie(this.myDataSource);
            DAOcommande daoCommande = new DAOcommande(this.myDataSource);
            while (rs.next()) {
                int produit = rs.getInt("PRODUIT");
                int quantite = rs.getInt("QUANTITE");
                LigneEntity ligne = new LigneEntity(commande,produit,quantite);
                String nom = daoProduit.nomProduit(produit);
                Double prixUnitaire = daoProduit.prixUnitaire(produit);
                Double remise = (daoCommande.afficherRemise(commande));
                Double prixTotal = (prixUnitaire * quantite) * 1.-remise;
                String nomCategorie = daoCategorie.afficherLibelle(daoProduit.numeroCategorie(produit));
                LigneCommandeEntity ligneCommande = new LigneCommandeEntity(ligne,nom,prixUnitaire,remise,prixTotal,nomCategorie);
                resultat.add(ligneCommande);
            }
        }catch(Exception e){
            throw e;
        }
        return resultat;
    }

    /**
     * Affiche tous les produits d'une commande
     *
     * @param commande la commande des code produit à afficher
     * @return Liste de tout les codes produits
     */
    public List<Integer> afficherCodeProduits(int commande) throws SQLException {
        List<Integer> resultat = new ArrayList<Integer>();
        String sql = "SELECT PRODUIT FROM LIGNE WHERE COMMANDE = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1,commande);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                resultat.add(rs.getInt("PRODUIT"));
            }
        }catch(Exception e){
            throw e;
        }
        return resultat;
    }

    /**
     * Affiche la quantité du produit d'une commande
     *
     * @param commande commande voulue
     * @param produit produit de la commande voulue
     * @return quantité du produit de la commande
     */
    public int afficherQuantite(int commande, int produit) throws SQLException {
        int resultat = 0;
        String sql = "SELECT QUANTITE FROM LIGNE WHERE COMMANDE = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1,commande);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                resultat = rs.getInt("QUANTITE");
            }
        }catch(Exception e){
            throw e;
        }
        return resultat;
    }
    
    /**
     * Ajoute une ligne d'une commande
     * @param commande le numéro de la commande
     * @param produit le produit de la ligne
     * @param quantite la quantité du produit
     * @throws SQLException 
     */
    public void ajouterLigne(int commande, int produit, int quantite) throws SQLException{
        String sql = "INSERT INTO LIGNE VALUES (?,?,?)";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1,commande);
            stmt.setInt(2,produit);
            stmt.setInt(3,quantite);
            stmt.executeUpdate();
        }catch(Exception e){
            throw e;
        }
    }
    
    /**
     * Supprime la ligne d'une commande
     * @param commande le numéro de la commande
     * @param produit le produit de la ligne
     * @param quantite la quantité du produit
     * @throws SQLException 
     */
    public void supprimerLigne(int commande,int produit) throws SQLException{
        String sql = "DELETE FROM LIGNE WHERE COMMANDE = ? AND PRODUIT = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1,commande);
            stmt.setInt(2,produit);
            stmt.executeUpdate();
        }catch(Exception e){
            throw e;
        }
    }
    
    /**
     * Modifier la quantité de la ligne d'une commande
     * @param commande la commande de la ligne à modifier
     * @param produit le produit de la ligne à modifier
     * @param quantite la nouvelle quantité
     * @throws SQLException 
     */
    public void modifierQuantiteLigne(int commande, int produit, int quantite) throws SQLException{
        String sql = "UPDATE LIGNE SET QUANTITE = ? WHERE COMMANDE = ? AND PRODUIT = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1,quantite);
            stmt.setInt(2,commande);
            stmt.setInt(3,produit);
            stmt.executeUpdate();
        }catch(Exception e){
            throw e;
        }
    }
    
}
