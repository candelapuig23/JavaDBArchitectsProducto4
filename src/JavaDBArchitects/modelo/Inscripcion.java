package JavaDBArchitects.modelo;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * La clase Inscripción representa el registro de un socio en una excursión.
 */
@Entity
@Table(name = "inscripciones")
public class Inscripcion {

    // Formateador para representar las fechas en formato "dd/MM/yyyy"
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Atributos de la clase Inscripción

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inscripcion")
    private String id_inscripcion;  // Número de inscripción único

    @ManyToOne
    @JoinColumn(name = "numero_socio", referencedColumnName = "numeroSocio")
    private Socio socio;  // Socio asociado a la inscripción

    @ManyToOne
    @JoinColumn(name = "idEexcursion", referencedColumnName = "idExcursion")
    private Excursion excursion;  // Excursión asociada a la inscripción

    @Column(name = "fecha_inscripcion", nullable = false)
    private LocalDate fecha_inscripcion;  // Fecha en la que se realizó la inscripción

    // Constructor vacío para JPA
    public Inscripcion() {}

    // Constructor: Inicializa los atributos de la inscripción
    public Inscripcion(String numInscripcion, Socio socio, Excursion excursion, LocalDate fecha_inscripcion) {
        this.id_inscripcion = numInscripcion;
        this.socio = socio;
        this.excursion = excursion;
        this.fecha_inscripcion = fecha_inscripcion;
    }

    // Getters y Setters: Permiten acceder y modificar los atributos

    public String getIdInscripcion() {
        return id_inscripcion;
    }

    public void setIdInscripcion(String idInscripcion) {
        this.id_inscripcion = idInscripcion;
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    public Excursion getExcursion() {
        return excursion;
    }

    public void setExcursion(Excursion excursion) {
        this.excursion = excursion;
    }

    public LocalDate getFecha_inscripcion() {
        return fecha_inscripcion;
    }

    public void setFechaInscripcion(LocalDate fecha_inscripcion) {
        this.fecha_inscripcion = fecha_inscripcion;
    }

    /**
     * Proporciona una representación en texto de la inscripción, incluyendo el socio,
     * la excursión y la fecha de inscripción.
     *
     * @return una cadena con los detalles de la inscripción.
     */
    @Override
    public String toString() {
        return "Nº Inscripción: " + id_inscripcion + '\n' +
                "Socio: " + socio + '\n' +
                "Excursión: " + excursion + '\n' +
                "Fecha de la Inscripción: " + fecha_inscripcion.format(FORMATO_FECHA);
    }
}
