package JavaDBArchitects.modelo.dao.entidades;

import jakarta.persistence.*;


@Entity
@Table(name = "federaciones")
public class FederacionEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_federacion", nullable = false)
    private int idFederacion;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    // Constructor por defecto
    public FederacionEntidad() {
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

    @Override
    public String toString() {
        return "FederacionEntidad{" +
                "idFederacion=" + idFederacion +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}