package JavaDBArchitects.modelo.dao.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table (name="federaciones")

public class FederacionEntidad {

    // Atributos de la federación:
    private int id_federacion;
    private String nombre;

    // Constructor solo con id_federacion
    public FederacionEntidad(int id_federacion) {
        this.id_federacion = id_federacion;
    }
    // Constructor para solo el nombre
    public FederacionEntidad(String nombre) {
        this.nombre = nombre;
    }

    // Constructor completo con id_federacion y nombre
    public FederacionEntidad(int id_federacion, String nombre) {
        this.id_federacion = id_federacion;
        this.nombre = nombre;
    }

    // Constructor sin id_federacion para otros usos, si es necesario
    public int getId_federacion() {
        return id_federacion;
    }



    public void setId_federacion(int id_federacion) {
        this.id_federacion = id_federacion;
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
        return "ID Federación: " + id_federacion + '\n' + "Nombre: " + nombre + '\n';
    }
}