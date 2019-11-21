package modele.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Axel
 */
public class CommandeEntity {
    
    int numero;
    String code_client; //clef étrangère
    Date saisiLe;
    Date envoyeLe;
    double port;
    String destinataire;
    String adresseLivraison;
    String villeLivraison;
    String regionLivraison;
    String codePostalLivraison;
    String paysLivraison;
    double remise;

    public CommandeEntity(int numero, String code_client, String saisiLe, String envoyeLe, double port, String destinataire, String adresseLivraison, String villeLivraison, String regionLivraison, String codePostalLivraison, String paysLivraison, double remise) throws ParseException {
        this.numero = numero;
        this.code_client = code_client;
        SimpleDateFormat formatageSaisie = new SimpleDateFormat("yyyy-MM-dd");
        this.saisiLe = formatageSaisie.parse(saisiLe);
        SimpleDateFormat formatageEnvoi = new SimpleDateFormat("yyyy-MM-dd");
        this.envoyeLe = formatageEnvoi.parse(envoyeLe);
        this.port = port;
        this.destinataire = destinataire;
        this.adresseLivraison = adresseLivraison;
        this.villeLivraison = villeLivraison;
        this.regionLivraison = regionLivraison;
        this.codePostalLivraison = codePostalLivraison;
        this.paysLivraison = paysLivraison;
        this.remise = remise;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCode_client() {
        return code_client;
    }

    public void setCode_client(String code_client) {
        this.code_client = code_client;
    }

    public Date getSaisiLe() {
        return saisiLe;
    }

    public void setSaisiLe(Date saisiLe) {
        this.saisiLe = saisiLe;
    }

    public Date getEnvoyeLe() {
        return envoyeLe;
    }

    public void setEnvoyeLe(Date envoyeLe) {
        this.envoyeLe = envoyeLe;
    }

    public double getPort() {
        return port;
    }

    public void setPort(double port) {
        this.port = port;
    }

    public String getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(String destinataire) {
        this.destinataire = destinataire;
    }

    public String getAdresseLivraison() {
        return adresseLivraison;
    }

    public void setAdresseLivraison(String adresseLivraison) {
        this.adresseLivraison = adresseLivraison;
    }

    public String getVilleLivraison() {
        return villeLivraison;
    }

    public void setVilleLivraison(String villeLivraison) {
        this.villeLivraison = villeLivraison;
    }

    public String getRegionLivraison() {
        return regionLivraison;
    }

    public void setRegionLivraison(String regionLivraison) {
        this.regionLivraison = regionLivraison;
    }

    public String getCodePostalLivraison() {
        return codePostalLivraison;
    }

    public void setCodePostalLivraison(String codePostalLivraison) {
        this.codePostalLivraison = codePostalLivraison;
    }

    public String getPaysLivraison() {
        return paysLivraison;
    }

    public void setPaysLivraison(String paysLivraison) {
        this.paysLivraison = paysLivraison;
    }

    public double getRemise() {
        return remise;
    }

    public void setRemise(double remise) {
        this.remise = remise;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.numero;
        hash = 89 * hash + Objects.hashCode(this.code_client);
        hash = 89 * hash + Objects.hashCode(this.saisiLe);
        hash = 89 * hash + Objects.hashCode(this.envoyeLe);
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.port) ^ (Double.doubleToLongBits(this.port) >>> 32));
        hash = 89 * hash + Objects.hashCode(this.destinataire);
        hash = 89 * hash + Objects.hashCode(this.adresseLivraison);
        hash = 89 * hash + Objects.hashCode(this.villeLivraison);
        hash = 89 * hash + Objects.hashCode(this.regionLivraison);
        hash = 89 * hash + Objects.hashCode(this.codePostalLivraison);
        hash = 89 * hash + Objects.hashCode(this.paysLivraison);
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.remise) ^ (Double.doubleToLongBits(this.remise) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CommandeEntity other = (CommandeEntity) obj;
        if (this.numero != other.numero) {
            return false;
        }
        if (Double.doubleToLongBits(this.port) != Double.doubleToLongBits(other.port)) {
            return false;
        }
        if (Double.doubleToLongBits(this.remise) != Double.doubleToLongBits(other.remise)) {
            return false;
        }
        if (!Objects.equals(this.code_client, other.code_client)) {
            return false;
        }
        if (!Objects.equals(this.destinataire, other.destinataire)) {
            return false;
        }
        if (!Objects.equals(this.adresseLivraison, other.adresseLivraison)) {
            return false;
        }
        if (!Objects.equals(this.villeLivraison, other.villeLivraison)) {
            return false;
        }
        if (!Objects.equals(this.regionLivraison, other.regionLivraison)) {
            return false;
        }
        if (!Objects.equals(this.codePostalLivraison, other.codePostalLivraison)) {
            return false;
        }
        if (!Objects.equals(this.paysLivraison, other.paysLivraison)) {
            return false;
        }
        if (!Objects.equals(this.saisiLe, other.saisiLe)) {
            return false;
        }
        if (!Objects.equals(this.envoyeLe, other.envoyeLe)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CommandeEntity{" + "numero=" + numero + ", code_client=" + code_client + ", saisiLe=" + saisiLe + ", envoyeLe=" + envoyeLe + ", port=" + port + ", destinataire=" + destinataire + ", adresseLivraison=" + adresseLivraison + ", villeLivraison=" + villeLivraison + ", regionLivraison=" + regionLivraison + ", codePostalLivraison=" + codePostalLivraison + ", paysLivraison=" + paysLivraison + ", remise=" + remise + '}';
    }
    
}
