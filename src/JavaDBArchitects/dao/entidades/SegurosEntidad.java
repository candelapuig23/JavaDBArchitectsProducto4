package JavaDBArchitects.dao.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "seguros")
public class SegurosEntidad {

    @Id
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "precio", nullable = false)
    private float precio;

    // Constructor por defecto
    public SegurosEntidad() {
    }

    // Getters y Setters
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "SegurosEntidad{" +
                "tipo='" + tipo + '\'' +
                ", precio=" + precio +
                '}';
    }
}
