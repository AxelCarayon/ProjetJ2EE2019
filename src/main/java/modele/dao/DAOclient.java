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
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import modele.entity.ClientEntity;

/**
 *
 * @author Axel
 */
public class DAOclient {

    private final DataSource myDataSource;

    /**
     * Construit le AO avec sa source de données
     *
     * @param dataSource la source de données à utiliser
     */
    public DAOclient(DataSource dataSource) {
        this.myDataSource = dataSource;
    }

    /**
     * @return Liste de ClientsEntity
     * @throws SQLException
     */
    public List<ClientEntity> tousLesClients() throws SQLException {
        List<ClientEntity> result = new ArrayList<>();

        String sql = "SELECT * FROM CLIENT";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String code = rs.getString("CODE");
                String societe = rs.getString("SOCIETE");
                String contact = rs.getString("CONTACT");
                String fonction = rs.getString("FONCTION");
                String adresse = rs.getString("ADRESSE");
                String ville = rs.getString("VILLE");
                String region = rs.getString("REGION");
                String code_postal = rs.getString("CODE_POSTAL");
                String pays = rs.getString("PAYS");
                String telephone = rs.getString("TELEPHONE");
                String fax = rs.getString("FAX");
                ClientEntity c = new ClientEntity(code, societe, contact, fonction, adresse, ville, region, code_postal, pays, telephone, fax);
                result.add(c);
            }
        }
        return result;
    }

    /**
     * Permet d'afficher un client dans la BDD
     *
     * @param code clef primaire d'un client
     * @return le client sous la forme ClientEntity
     */
    public ClientEntity afficherClient(String code) throws SQLException {
        ClientEntity client = null;
        String sql = "SELECT * FROM CLIENT WHERE CODE = ?";
        try (Connection myConnection = myDataSource.getConnection();
                PreparedStatement statement = myConnection.prepareStatement(sql)) {
            statement.setString(1, code); // On fixe le 1° paramètre de la requête
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    String societe = rs.getString("SOCIETE");
                    String contact = rs.getString("CONTACT");
                    String fonction = rs.getString("FONCTION");
                    String adresse = rs.getString("ADRESSE");
                    String ville = rs.getString("VILLE");
                    String region = rs.getString("REGION");
                    String code_postal = rs.getString("CODE_POSTAL");
                    String pays = rs.getString("PAYS");
                    String telephone = rs.getString("TELEPHONE");
                    String fax = rs.getString("FAX");
                    client = new ClientEntity(code, societe, contact, fonction, adresse, ville, region, code_postal, pays, telephone, fax);
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return client;
    }

    /**
     * Renvoie si un couple username/password existe dans la base de donnée
     * @param username le nom d'utilisateur entré
     * @param password le mot de passé entré
     * @return 0 si le login est incorrect/n'existe pas, 1 si le login existe et il s'agit d'un client,
     * 2 si le login existe et il s'agit de l'administrateur.
     * @throws SQLException 
     */
    public int loginExiste(String username, String password) throws SQLException {
        int resultat = 0;
        if (username.equals("admin") && password.equals("admin")) {
            resultat = 2;
        } else {
            String sql = "SELECT CONTACT,CODE FROM CLIENT WHERE CONTACT = ? AND CODE = ?";
            try (Connection myConnection = myDataSource.getConnection();
                    PreparedStatement statement = myConnection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, password);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        resultat = 1;
                    }
                }
            }

        }

        return resultat;
    }

    /**
     * Modifie le nom de la société d'un client
     *
     * @param client le code du client à modifier
     * @param societe le nouveau nom de la societe
     */
    public void modifierSociete(String client, String societe) throws SQLException {

        String sql = "UPDATE CLIENT SET SOCIETE=? WHERE CODE=?";
        try (Connection myConnection = myDataSource.getConnection();
                PreparedStatement statement = myConnection.prepareStatement(sql)) {
            statement.setString(1, societe);
            statement.setString(2, client);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Modifie le contact d'un client
     *
     * @param client le code du client à modifier
     * @param contact le nouveau contact du client
     */
    public void modifierContact(String client, String contact) {
        String sql = "UPDATE CLIENT SET CONTACT=? WHERE CODE=?";
        try (Connection myConnection = myDataSource.getConnection();
                PreparedStatement statement = myConnection.prepareStatement(sql)) {
            statement.setString(1, contact);
            statement.setString(2, client);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Modifie la fonction d'un client
     *
     * @param client le code du client à modifier
     * @param fonction le nouveau contact d'un client
     */
    public void modifierFonction(String client, String fonction) {
        String sql = "UPDATE CLIENT SET FONCTION=? WHERE CODE=?";
        try (Connection myConnection = myDataSource.getConnection();
                PreparedStatement statement = myConnection.prepareStatement(sql)) {
            statement.setString(1, fonction);
            statement.setString(2, client);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Modifie l'adresse d'un client
     *
     * @param client le code du client à modifier
     * @param adresse la nouvelle adresse du client
     */
    public void modifierAdresse(String client, String adresse) {
        String sql = "UPDATE CLIENT SET ADRESSE=? WHERE CODE=?";
        try (Connection myConnection = myDataSource.getConnection();
                PreparedStatement statement = myConnection.prepareStatement(sql)) {
            statement.setString(1, adresse);
            statement.setString(2, client);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Modifie la ville du client
     *
     * @param client le code du client à modifier
     * @param ville la nouvelle ville du client
     */
    public void modifierVille(String client, String ville) {
        String sql = "UPDATE CLIENT SET VILLE=? WHERE CODE=?";
        try (Connection myConnection = myDataSource.getConnection();
                PreparedStatement statement = myConnection.prepareStatement(sql)) {
            statement.setString(1, ville);
            statement.setString(2, client);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Modifie la région du client
     *
     * @param client le code du client à modifier
     * @param region la nouvelle région du client
     */
    public void modifierRegion(String client, String region) {
        String sql = "UPDATE CLIENT SET REGION=? WHERE CODE=?";
        try (Connection myConnection = myDataSource.getConnection();
                PreparedStatement statement = myConnection.prepareStatement(sql)) {
            statement.setString(1, region);
            statement.setString(2, client);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Modifie le code postal d'un client
     *
     * @param client le code du client à modifier
     * @param CodePostal le nouveau code postal du client
     */
    public void modifierCodePostal(String client, String codePostal) {
        String sql = "UPDATE CLIENT SET CODE_POSTAL=? WHERE CODE=?";
        try (Connection myConnection = myDataSource.getConnection();
                PreparedStatement statement = myConnection.prepareStatement(sql)) {
            statement.setString(1, codePostal);
            statement.setString(2, client);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Modifie le pays d'un client
     *
     * @param client le code du client à modifier
     * @param pays le nouveau pays du client
     */
    public void modifierPays(String client, String pays) {
        String sql = "UPDATE CLIENT SET PAYS=? WHERE CODE=?";
        try (Connection myConnection = myDataSource.getConnection();
                PreparedStatement statement = myConnection.prepareStatement(sql)) {
            statement.setString(1, pays);
            statement.setString(2, client);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Modifie le numéro de téléphone du client
     *
     * @param client le code du client à modifier
     * @param telephone le nouveau numéro de téléphone
     */
    public void modifierTelephone(String client, String telephone) {
        String sql = "UPDATE CLIENT SET TELEPHONE=? WHERE CODE=?";
        try (Connection myConnection = myDataSource.getConnection();
                PreparedStatement statement = myConnection.prepareStatement(sql)) {
            statement.setString(1, telephone);
            statement.setString(2, client);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Modifie le fax d'un client
     *
     * @param client le code du client à modifier
     * @param fax le nouveau numéro de fax
     */
    public void modifierFax(String client, String fax) {
        String sql = "UPDATE CLIENT SET FAX=? WHERE CODE=?";
        try (Connection myConnection = myDataSource.getConnection();
                PreparedStatement statement = myConnection.prepareStatement(sql)) {
            statement.setString(1, fax);
            statement.setString(2, client);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
