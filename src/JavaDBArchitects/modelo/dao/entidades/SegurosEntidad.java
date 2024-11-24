package JavaDBArchitects.modelo.dao.entidades;

import JavaDBArchitects.modelo.TipoSeguro;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table (name="seguros")
public class SegurosEntidad {
    private TipoSeguro tipo;
    private float precio;

    public SegurosEntidad(TipoSeguro tipo, float precio) {
        this.tipo = tipo;
        this.precio = precio;
    }

    public TipoSeguro getTipo() {
        return tipo;
    }

    public void setTipo(TipoSeguro tipo) {
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
        return "Seguro{" +
                "tipo=" + tipo +
                ", precio=" + precio +
                '}';
    }
}
