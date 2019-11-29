package modele.dao;

import javax.sql.DataSource;

/**
 *
 * @author Axel
 */
public class DataSourceFactory {

    public enum DriverType {
        embedded, server
    };

    // Choic du type de driver : embedded ou serveur
    private static final DriverType TYPE = DriverType.server;

    /**
     * Renvoie la source de données (server ou embbeded)
     *
     * @return la source de données
     */
    public static DataSource getDataSource() {
        DataSource result;

        org.apache.derby.jdbc.EmbeddedDataSource es = new org.apache.derby.jdbc.EmbeddedDataSource();
        es.setCreateDatabase("create");
        es.setDatabaseName("embedded_sample");
        result = es;

        return result;
    }

}
