package JavaDBArchitects.dao.entidades;
import jakarta.persistence.*;

public class EstandarEntidad extends SocioEntidad {

    @Column(name = "nif", nullable = false)
    private String nif;

    @ManyToOne
    @JoinColumn(name = "id_seguro")
    private SegurosEntidad seguro;

    // Constructor por defecto
    public EstandarEntidad() {
    }

    // Getters y Setters
    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public SegurosEntidad getSeguro() {
        return seguro;
    }

    public void setSeguro(SegurosEntidad seguro) {
        this.seguro = seguro;
    }

    @Override
    public String toString() {
        return "SocioEstandarEntidad{" +
                "nif='" + nif + '\'' +
                ", seguro=" + seguro +
                "} " + super.toString();
    }
}