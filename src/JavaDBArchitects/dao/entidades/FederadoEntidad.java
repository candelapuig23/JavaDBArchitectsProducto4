package JavaDBArchitects.dao.entidades;
import jakarta.persistence.*;


public class FederadoEntidad extends SocioEntidad {

    @Column(name = "nif", nullable = false)
    private String nif;

    @ManyToOne
    @JoinColumn(name = "id_federacion", nullable = false)
    private FederacionEntidad federacion;

    // Constructor por defecto
    public FederadoEntidad() {
    }

    // Getters y Setters
    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public FederacionEntidad getFederacion() {
        return federacion;
    }

    public void setFederacion(FederacionEntidad federacion) {
        this.federacion = federacion;
    }

    @Override
    public String toString() {
        return "FederadoEntidad{" +
                "Nif:='" + getNif() + '\'' +
                ", Federacion:" + getFederacion() +
                "} " + super.toString();
    }
}
