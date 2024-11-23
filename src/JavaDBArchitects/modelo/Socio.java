package JavaDBArchitects.modelo;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Clase abstracta Socio: Representa a un socio del centro.
 * Contiene atributos comunes para todos los tipos de socios.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  // Puedes usar JOINED o TABLE_PER_CLASS según tu diseño
@DiscriminatorColumn(name = "tipo_socio", discriminatorType = DiscriminatorType.STRING)
@Table(name = "socios")
public abstract class Socio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numeroSocio")
    private int numeroSocio;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String tipoSocio;

    @Column(unique = true)
    private String nif;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_seguro")
    private TipoSeguro tipoSeguro;

    @Column(name = "id_federacion")
    private Integer idFederacion;

    @Column(name = "id_socio_padre")
    private Integer idSocioPadre;

    @Column(name = "cuota_mensual", precision = 10, scale = 2)
    private BigDecimal cuotaMensual;

    // Constructor vacío requerido por JPA
    public Socio() {
    }

    public Socio(int numeroSocio, String nombre, String tipoSocio, String nif, TipoSeguro tipoSeguro, Integer idFederacion, Integer idSocioPadre, BigDecimal cuotaMensual) {
        this.numeroSocio = numeroSocio;
        this.nombre = nombre;
        this.tipoSocio = tipoSocio;
        this.nif = nif;
        this.tipoSeguro = tipoSeguro;
        this.idFederacion = idFederacion;
        this.idSocioPadre = idSocioPadre;
        this.cuotaMensual = cuotaMensual;
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

    public TipoSeguro getTipoSeguro() {
        return tipoSeguro;
    }

    public void setTipoSeguro(TipoSeguro tipoSeguro) {
        this.tipoSeguro = tipoSeguro;
    }

    public Integer getIdFederacion() {
        return idFederacion;
    }

    public void setIdFederacion(Integer idFederacion) {
        this.idFederacion = idFederacion;
    }

    public Integer getIdSocioPadre() {
        return idSocioPadre;
    }

    public void setIdSocioPadre(Integer idSocioPadre) {
        this.idSocioPadre = idSocioPadre;
    }

    public BigDecimal getCuotaMensual() {
        return cuotaMensual;
    }

    public void setCuotaMensual(BigDecimal cuotaMensual) {
        this.cuotaMensual = cuotaMensual;
    }

    public abstract double calcularCuotaMensual();

    @Override
    public String toString() {
        return "Socio{" +
                "numeroSocio=" + numeroSocio +
                ", nombre='" + nombre + '\'' +
                ", tipoSocio='" + tipoSocio + '\'' +
                ", nif='" + nif + '\'' +
                ", tipoSeguro=" + tipoSeguro +
                ", idFederacion=" + idFederacion +
                ", idSocioPadre=" + idSocioPadre +
                ", cuotaMensual=" + cuotaMensual +
                '}';
    }
}


