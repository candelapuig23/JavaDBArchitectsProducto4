package JavaDBArchitects.modelo.dao.entidades;

import JavaDBArchitects.modelo.TipoSeguro;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "socios")
public class SocioEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numeroSocio", nullable = false)
    private int numeroSocio;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "tipo_socio", nullable = false)
    private String tipoSocio;

    @Column(name = "nif")
    private String nif;

    @ManyToOne
    @JoinColumn(name = "id_federacion")
    private FederacionEntidad federacion;

    @ManyToOne
    @JoinColumn(name = "id_socio_padre")
    private SocioEntidad socioPadre;

    // Constructor por defecto
    public SocioEntidad() {
    }

    // Getters y Setters
    public int getNumeroSocio() {
        return numeroSocio;
    }

    public void setNumeroSocio(int numeroSocio) {
        this.numeroSocio = numeroSocio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoSocio() {
        return tipoSocio;
    }

    public void setTipoSocio(String tipoSocio) {
        this.tipoSocio = tipoSocio;
    }

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

    public SocioEntidad getSocioPadre() {
        return socioPadre;
    }

    public void setSocioPadre(SocioEntidad socioPadre) {
        this.socioPadre = socioPadre;
    }

    @Override
    public String toString() {
        return "SocioEntidad{" +
                "numeroSocio=" + numeroSocio +
                ", nombre='" + nombre + '\'' +
                ", tipoSocio='" + tipoSocio + '\'' +
                ", nif='" + nif + '\'' +
                ", federacion=" + federacion +
                ", socioPadre=" + (socioPadre != null ? socioPadre.getNumeroSocio() : "N/A") +
                '}';
    }
}
