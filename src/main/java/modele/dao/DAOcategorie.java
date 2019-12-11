package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import modele.entity.CategorieEntity;

/**
 *
 * @author Axel
 */
public class DAOcategorie {

    private final DataSource myDataSource;

    /**
     * Construit le AO avec sa source de données
     *
     * @param dataSource la source de données à utiliser
     */
    public DAOcategorie(DataSource dataSource) {
        this.myDataSource = dataSource;
    }

    public List<CategorieEntity> toutesLesCategories() throws SQLException {
        List<CategorieEntity> resultat = new ArrayList();
        String sql = "SELECT * FROM CATEGORIE";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int code = rs.getInt("CODE");
                String libelle = rs.getString("LIBELLE");
                String description = rs.getString("DESCRIPTION");
                CategorieEntity c = new CategorieEntity(code, libelle, description);
                resultat.add(c);
            }
        }
        return resultat;
    }

    public CategorieEntity afficherCategorie(int code) throws SQLException {
        CategorieEntity categorie = null;
        String sql = "SELECT * FROM CATEGORIE WHERE CODE = ?";
        try (Connection myConnection = myDataSource.getConnection();
                PreparedStatement statement = myConnection.prepareStatement(sql)) {
            statement.setInt(1, code); // On fixe le 1° paramètre de la requête
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    String libelle = rs.getString("LIBELLE");
                    String description = rs.getString("DESCRIPTION");
                    categorie = new CategorieEntity(code, libelle, description);
                }
            }
        }
        return categorie;
    }

    public String afficherLibelle(int code) throws SQLException {
        String libelle = null;
        String sql = "SELECT LIBELLE FROM CATEGORIE WHERE CODE = ?";
        try (Connection myConnection = myDataSource.getConnection();
                PreparedStatement statement = myConnection.prepareStatement(sql)) {
            statement.setInt(1, code); // On fixe le 1° paramètre de la requête
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    libelle = rs.getString("LIBELLE");
                }
            }
        }
        return libelle;
    }

    public String afficherDescription(int code) throws SQLException {
        String description = null;
        String sql = "SELECT DESCRIPTION FROM CATEGORIE WHERE CODE = ?";
        try (Connection myConnection = myDataSource.getConnection();
                PreparedStatement statement = myConnection.prepareStatement(sql)) {
            statement.setInt(1, code); // On fixe le 1° paramètre de la requête
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    description = rs.getString("DESCRIPTION");
                }
            }
        }
        return description;
    }

}
