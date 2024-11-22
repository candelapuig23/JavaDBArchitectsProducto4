package JavaDBArchitects.modelo;

import jakarta.persistence.*; // Cambia a JPA de Jakarta
import java.util.Date;

@Entity // Define esta clase como una entidad JPA
@Table(name = "Excursiones") // Mapea esta clase con la tabla "Excursiones" en la base de datos
public class Excursion {

    @Id // Indica que este campo es la clave primaria
    @Column(name = "idExcursion") // Mapea con la columna "idExcursion"
    private String idExcursion;

    @Column(name = "descripcion", nullable = false) // Mapea con la columna "descripcion"
    private String descripcion;

    @Temporal(TemporalType.DATE) // Indica que este campo es un tipo de fecha
    @Column(name = "fecha", nullable = false) // Mapea con la columna "fecha"
    private Date fecha;

    @Column(name = "num_dias", nullable = false) // Mapea con la columna "num_dias"
    private int numDias;

    @Column(name = "precio", nullable = false) // Mapea con la columna "precio"
    private float precioInscripcion;

    // Constructor sin parámetros requerido por JPA
    public Excursion() {}

    // Constructor completo para inicializar la entidad
    public Excursion(String idExcursion, String descripcion, Date fecha, int numDias, float precioInscripcion) {
        this.idExcursion = idExcursion;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.numDias = numDias;
        this.precioInscripcion = precioInscripcion;
    }

    // Getters y setters (sin cambios respecto a tu versión actual)
    public String getIdExcursion() {
        return idExcursion;
    }

    public void setIdExcursion(String idExcursion) {
        this.idExcursion = idExcursion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getNumDias() {
        return numDias;
    }

    public void setNumDias(int numDias) {
        this.numDias = numDias;
    }

    public float getPrecioInscripcion() {
        return precioInscripcion;
    }

    public void setPrecioInscripcion(float precioInscripcion) {
        this.precioInscripcion = precioInscripcion;
    }

    // Sobrescribir el método toString para facilitar el uso en logs o debugging
    @Override
    public String toString() {
        return "Excursion{" +
                "idExcursion='" + idExcursion + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fecha=" + fecha +
                ", numDias=" + numDias +
                ", precioInscripcion=" + precioInscripcion +
                '}';
    }
}
