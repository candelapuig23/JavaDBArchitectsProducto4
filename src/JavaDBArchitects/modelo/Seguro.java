package JavaDBArchitects.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "seguros") // Nombre de la tabla en la base de datos
public class Seguro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    @Column(name = "id_seguro") // Nombre de la columna del ID en la tabla
    private Long id;

    @Enumerated(EnumType.STRING) // Almacena el enum como un string en la base de datos
    @Column(name = "tipo", nullable = false) // Especifica la columna y la restricción de no nulo
    private TipoSeguro tipo;

    @Column(name = "precio", nullable = false) // Especifica la columna y la restricción de no nulo
    private float precio;

    // Constructor vacío requerido por JPA
    public Seguro() {}

    // Constructor con parámetros
    public Seguro(TipoSeguro tipo, float precio) {
        this.tipo = tipo;
        this.precio = precio;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    // Método toString para representar el objeto
    @Override
    public String toString() {
        return "Seguro{" +
                "id=" + id +
                ", tipo=" + tipo +
                ", precio=" + precio +
                '}';
    }
}
