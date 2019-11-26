package modele.entity;

import java.util.Objects;

/**
 *
 * @author Axel
 */
public class ClientEntity {
    
    String code; //clef primaire
    String societe;
    String contact;
    String fonction;
    String adresse;
    String ville;
    String region;
    String code_postal; //string car des codes postaux ont des lettres
    String pays;
    String telephone; // string pour les tirets/points dans les nums
    String fax; //idem que telephone

    
    public ClientEntity(String code, String societe, String contact, String fonction, String adresse, String ville, String region, String code_postal, String pays, String telephone, String fax) {
        this.code = code;
        this.societe = societe;
        this.contact = contact;
        this.fonction = fonction;
        this.adresse = adresse;
        this.ville = ville;
        this.region = region;
        this.code_postal = code_postal;
        this.pays = pays;
        this.telephone = telephone;
        this.fax = fax;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSociete() {
        return societe;
    }

    public void setSociete(String societe) {
        this.societe = societe;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCode_postal() {
        return code_postal;
    }

    public void setCode_postal(String code_postal) {
        this.code_postal = code_postal;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.code);
        hash = 79 * hash + Objects.hashCode(this.societe);
        hash = 79 * hash + Objects.hashCode(this.contact);
        hash = 79 * hash + Objects.hashCode(this.fonction);
        hash = 79 * hash + Objects.hashCode(this.adresse);
        hash = 79 * hash + Objects.hashCode(this.ville);
        hash = 79 * hash + Objects.hashCode(this.region);
        hash = 79 * hash + Objects.hashCode(this.code_postal);
        hash = 79 * hash + Objects.hashCode(this.pays);
        hash = 79 * hash + Objects.hashCode(this.telephone);
        hash = 79 * hash + Objects.hashCode(this.fax);
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
        final ClientEntity other = (ClientEntity) obj;
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        if (!Objects.equals(this.societe, other.societe)) {
            return false;
        }
        if (!Objects.equals(this.contact, other.contact)) {
            return false;
        }
        if (!Objects.equals(this.fonction, other.fonction)) {
            return false;
        }
        if (!Objects.equals(this.adresse, other.adresse)) {
            return false;
        }
        if (!Objects.equals(this.ville, other.ville)) {
            return false;
        }
        if (!Objects.equals(this.region, other.region)) {
            return false;
        }
        if (!Objects.equals(this.code_postal, other.code_postal)) {
            return false;
        }
        if (!Objects.equals(this.pays, other.pays)) {
            return false;
        }
        if (!Objects.equals(this.telephone, other.telephone)) {
            return false;
        }
        if (!Objects.equals(this.fax, other.fax)) {
            return false;
        }
        return true;
    }   

    @Override
    public String toString() {
        return "ClientEntity{" + "code=" + code + ", societe=" + societe + ", contact=" + contact + ", fonction=" + fonction + ", adresse=" + adresse + ", ville=" + ville + ", region=" + region + ", code_postal=" + code_postal + ", pays=" + pays + ", telephone=" + telephone + ", fax=" + fax + '}';
    }
    
}
