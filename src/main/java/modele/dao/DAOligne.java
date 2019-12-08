package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
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
        }
        return resultat;
    }

    /**
     * Affiches toutes les lignes d'une commande
     *
     * @param commande la commande à afficher
     * @return Liste de toutes les LigneEntity d'une commande
     */
    public List afficherCommande(int commande) throws SQLException {
        List resultat = new ArrayList();
        String sql = "SELECT * FROM LIGNE WHERE COMMANDE = ?";
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
                String nom = daoProduit.nomProduit(produit);
                Double prixUnitaire = daoProduit.prixUnitaire(produit);
                Double remise = (daoCommande.afficherRemise(commande));
                Double prixTotal = (prixUnitaire * quantite) * 1.-remise;
                String nomCategorie = daoCategorie.afficherLibelle(daoProduit.numeroCategorie(produit));
                ArrayList ligne = new ArrayList();
                ligne.add(nom);
                ligne.add(prixUnitaire);
                ligne.add(remise);
                ligne.add(prixTotal);
                ligne.add(nomCategorie);
                resultat.add(ligne);
            }
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
        String sql = "SELECT * FROM LIGNE WHERE COMMANDE = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1,commande);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                resultat.add(rs.getInt("PRODUIT"));
            }
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
        String sql = "SELECT * FROM LIGNE WHERE COMMANDE = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1,commande);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                resultat = rs.getInt("QUANTITE");
            }
        }
        return resultat;
    }
    
}
