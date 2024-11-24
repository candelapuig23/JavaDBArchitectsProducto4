package JavaDBArchitects.dao.entidades;
import jakarta.persistence.*;


public class InfantilEntidad extends SocioEntidad {

    @Column(name = "num_socio_padre_o_madre", nullable = false)
    private Integer numSocioPadreOMadre;

    // Constructor por defecto
    public InfantilEntidad() {
    }

    // Getters y Setters
    public Integer getNumSocioPadreOMadre() {
        return numSocioPadreOMadre;
    }

    public void setNumSocioPadreOMadre(Integer numSocioPadreOMadre) {
        this.numSocioPadreOMadre = numSocioPadreOMadre;
    }

    @Override
    public String toString() {
        return "Numero socio tutor:" + getNumSocioPadreOMadre()+super.toString();
    }
}
