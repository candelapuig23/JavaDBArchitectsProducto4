package JavaDBArchitects.modelo.dao.entidades;

import JavaDBArchitects.modelo.Excursion;
import JavaDBArchitects.modelo.Socio;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.*;

@Entity
@Table(name = "inscripciones")
public class InscripcionEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inscripcion", nullable = false)
    private int idInscripcion;

    @ManyToOne
    @JoinColumn(name = "id_socio", nullable = false)
    private SocioEntidad socio;

    @ManyToOne
    @JoinColumn(name = "id_excursion", nullable = false)
    private ExcursionEntidad excursion;

    @Column(name = "fecha_inscripcion", nullable = false)
    private java.sql.Date fechaInscripcion;

    // Constructor por defecto
    public InscripcionEntidad() {
    }

    // Getters y Setters
    public int getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(int idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public SocioEntidad getSocio() {
        return socio;
    }

    public void setSocio(SocioEntidad socio) {
        this.socio = socio;
    }

    public ExcursionEntidad getExcursion() {
        return excursion;
    }

    public void setExcursion(ExcursionEntidad excursion) {
        this.excursion = excursion;
    }

    public java.sql.Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(java.sql.Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    @Override
    public String toString() {
        return "InscripcionEntidad{" +
                "idInscripcion=" + idInscripcion +
                ", socio=" + socio +
                ", excursion=" + excursion +
                ", fechaInscripcion=" + fechaInscripcion +
                '}';
    }
}
