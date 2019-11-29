/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import modele.entity.ClientEntity;
import modele.entity.CommandeEntity;

/**
 *
 * @author Axel
 */
public class DAOcommande {

    private final DataSource myDataSource;

    /**
     * Construit le AO avec sa source de données
     *
     * @param dataSource la source de données à utiliser
     */
    public DAOcommande(DataSource dataSource) {
        this.myDataSource = dataSource;
    }

    /**
     * Va chercher dans la BDD la liste de toutes les commandes
     *
     * @return List<CommandeEntity>
     */
    public List<CommandeEntity> toutesLesCommandes() throws SQLException, ParseException {
        List<CommandeEntity> resultat = new ArrayList<CommandeEntity>();
        String sql = "SELECT * FROM COMMANDE";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int code = rs.getInt("NUMERO");
                String code_client = rs.getString("CLIENT");
                String saisiLe = rs.getString("SAISIE_LE");
                String envoyeLe = rs.getString("ENVOYEE_LE");
                double port = rs.getDouble("PORT");
                String destinataire = rs.getString("DESTINATAIRE");
                String adresseLivraison = rs.getString("ADRESSE_LIVRAISON");
                String villeLivraison = rs.getString("VILLE_LIVRAISON");
                String regionLivraison = rs.getString("REGION_LIVRAISON");
                String codePostalLivraison = rs.getString("CODE_POSTAL_LIVRAIS");
                String paysLivraison = rs.getString("PAYS_LIVRAISON");
                double remise = rs.getDouble("REMISE");

                CommandeEntity c = new CommandeEntity(code, code_client, saisiLe, envoyeLe, port,
                        destinataire, adresseLivraison, villeLivraison, regionLivraison,
                        codePostalLivraison, paysLivraison, remise);
                resultat.add(c);
            }
        } catch (SQLException e) {
            throw e;

        }
        return resultat;
    }

    /**
     * Affiche une commande
     *
     * @param numero le numéro de la commande
     * @return CommandeEntity
     */
    public CommandeEntity afficherCommande(int numero) throws ParseException, SQLException {
        CommandeEntity resultat = null;
        String sql = "SELECT * FROM COMMANDE WHERE NUMERO = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, numero);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String code_client = rs.getString("CLIENT");
                String saisiLe = rs.getString("SAISIE_LE");
                String envoyeLe = rs.getString("ENVOYEE_LE");
                double port = rs.getDouble("PORT");
                String destinataire = rs.getString("DESTINATAIRE");
                String adresseLivraison = rs.getString("ADRESSE_LIVRAISON");
                String villeLivraison = rs.getString("VILLE_LIVRAISON");
                String regionLivraison = rs.getString("REGION_LIVRAISON");
                String codePostalLivraison = rs.getString("CODE_POSTAL_LIVRAIS");
                String paysLivraison = rs.getString("PAYS_LIVRAISON");
                double remise = rs.getDouble("REMISE");

                CommandeEntity c = new CommandeEntity(numero, code_client, saisiLe, envoyeLe, port,
                        destinataire, adresseLivraison, villeLivraison, regionLivraison,
                        codePostalLivraison, paysLivraison, remise);
                resultat = c;
            }
        } catch (SQLException e) {
            throw e;
        }
        return resultat;
    }

    /**
     * Ajoute une nouvelle commande dans la bdd
     *
     * @param numero le numéro de la nouvelle commande
     * @param code_client le nouveau code client
     * @param saisiLe date de saisie de la commande
     * @param envoyeLe date d'envoi de la commande
     * @param port port de la commande
     * @param destinataire destinataire de la commande
     * @param adresseLivraison adresse de livraison de la commande
     * @param villeLivraison ville de livraison de la commande
     * @param regionLivraison region de livraison de la commande
     * @param codePostalLivraison code postal de livraison de la commande
     * @param paysLivraison pays de livraison de la commande
     * @param remise % de remise de la commande
     */
    public void ajouterCommande(int numero, String code_client, String saisiLe, String envoyeLe,
            double port, String destinataire, String adresseLivraison, String villeLivraison,
            String regionLivraison, String codePostalLivraison, String paysLivraison, double remise) throws SQLException {
        String sql = "INSERT INTO COMMANDE VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, numero);
            stmt.setString(2, code_client);
            stmt.setString(3, saisiLe);
            stmt.setString(4, envoyeLe);
            stmt.setDouble(5, port);
            stmt.setString(6, destinataire);
            stmt.setString(7, adresseLivraison);
            stmt.setString(8, villeLivraison);
            stmt.setString(9, regionLivraison);
            stmt.setString(10, codePostalLivraison);
            stmt.setString(11, paysLivraison);
            stmt.setDouble(12, remise);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Supprime une commande de la BDD
     *
     * @param numero numero de la commande à supprimer
     */
    public void supprimerCommande(int numero) throws SQLException {
        String sql = "DELETE FROM COMMANDE WHERE NUMERO = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, numero);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Affiche le code client lié à une commande
     *
     * @numero le numéro de la commande
     * @return ClientEntity
     */
    public String afficherCodeClient(int numero) throws SQLException {
        String resultat = null;
        String sql = "SELECT * FROM COMMANDE WHERE NUMERO = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, numero);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                resultat = rs.getString("CLIENT");
            }
        } catch (SQLException e) {
            throw e;
        }
        return resultat;
    }

    /**
     * Modifie le destinataire d'une commande
     *
     * @param numero numero de la commande à modifier
     * @param destinataire nouveau destinataire
     */
    public void modifierDestinataire(int numero, String destinataire) throws SQLException {
        String sql = "UPDATE COMMANDE SET DESTINATAIRE = ? WHERE NUMERO = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, destinataire);
            stmt.setInt(2, numero);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Modifie l'adresse de livraison de la commande
     *
     * @param numero numero de la commande à modifier
     * @param adresseLivraison nouvelle adresse de livraison
     */
    public void modifierAdresseLivraison(int numero, String adresseLivraison) throws SQLException {
        String sql = "UPDATE COMMANDE SET ADRESSE_LIVRAISON = ? WHERE NUMERO = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, adresseLivraison);
            stmt.setInt(2, numero);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Modifie la région de livraison de la commande
     *
     * @param numero numero de la commande à modifier
     * @param regionLivraison nouvelle region de livraison
     */
    public void modifierRegionLivraison(int numero, String regionLivraison) throws SQLException {
        String sql = "UPDATE COMMANDE SET REGION_LIVRAISON = ? WHERE NUMERO = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, regionLivraison);
            stmt.setInt(2, numero);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Modifie le code postal de livraison d'une commande
     *
     * @param numero numéro de la commande à modfier
     * @param codePostalLivraison nouveau code postal
     */
    public void modifierCodePostalLivraison(int numero, String codePostalLivraison) throws SQLException {
        String sql = "UPDATE COMMANDE SET CODE_POSTAL_LIVRAIS = ? WHERE NUMERO = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, codePostalLivraison);
            stmt.setInt(2, numero);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Modifie le pays de livraison d'une commande
     *
     * @param numero numéro de la commande à modifier
     * @param paysLivraison nouveau pays de livraison
     */
    public void modifierPaysLivraison(int numero, String paysLivraison) throws SQLException {
        String sql = "UPDATE COMMANDE SET PAYS_LIVRAISON = ? WHERE NUMERO = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, paysLivraison);
            stmt.setInt(2, numero);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Modifie le taux de remise d'une commande
     *
     * @param numero numero de la commande à modifier
     * @param remise % de remise de la commande
     */
    public void modifierRemise(int numero, double remise) throws SQLException {
        String sql = "UPDATE COMMANDE SET REMISE = ? WHERE NUMERO = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, remise);
            stmt.setInt(2, numero);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

}
