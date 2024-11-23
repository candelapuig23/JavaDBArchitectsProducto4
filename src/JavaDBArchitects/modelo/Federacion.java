package JavaDBArchitects.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "Federaciones") // Asegúrate de que el nombre coincide con la tabla en tu base de datos
public class Federacion {

    // Atributos de la federación:
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremental
    @Column(name = "id_federacion")
    private int idFederacion;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    // Constructor vacío necesario para JPA
    public Federacion() {
    }

    // Constructor solo con id_federacion
    public Federacion(int idFederacion) {
        this.idFederacion = idFederacion;
    }

    // Constructor solo con el nombre
    public Federacion(String nombre) {
        this.nombre = nombre;
    }

    // Constructor completo
    public Federacion(int idFederacion, String nombre) {
        this.idFederacion = idFederacion;
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getIdFederacion() {
        return idFederacion;
    }

    public void setIdFederacion(int idFederacion) {
        this.idFederacion = idFederacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Método toString para proporcionar una representación en texto de la clase Federacion
    @Override
    public String toString() {
        return "ID Federación: " + idFederacion + '\n' +
                "Nombre: " + nombre + '\n';
    }
}
