package modele.entity;

import java.util.Objects;

/**
 *
 * @author Axel
 */
public class CategorieEntity {
    
    int code; //clef primaire
    String libelle;
    String description;

    public CategorieEntity(int code, String libelle, String description) {
        this.code = code;
        this.libelle = libelle;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.code;
        hash = 31 * hash + Objects.hashCode(this.libelle);
        hash = 31 * hash + Objects.hashCode(this.description);
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
        final CategorieEntity other = (CategorieEntity) obj;
        if (this.code != other.code) {
            return false;
        }
        if (!Objects.equals(this.libelle, other.libelle)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CategorieEntity{" + "code=" + code + ", libelle=" + libelle + ", description=" + description + '}';
    }
    
}
