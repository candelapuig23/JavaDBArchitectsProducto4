package JavaDBArchitects.dao.entidades;


import jakarta.persistence.*;

@Entity
@Table(name = "excursiones")
public class ExcursionEntidad {

    @Id
    @Column(name = "idExcursion", nullable = false)
    private String idExcursion;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "fecha", nullable = false)
    private java.sql.Date fecha;

    @Column(name = "num_dias", nullable = false)
    private int numDias;

    @Column(name = "precio", nullable = false)
    private float precio;

    @Column(name = "codigo", nullable = false)
    private String codigo;

    // Constructor por defecto
    public ExcursionEntidad() {
    }

    // Getters y Setters (corregido el getter)
    public String getIdExcursion() {  // Renombrado correctamente
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

    public java.sql.Date getFecha() {
        return fecha;
    }

    public void setFecha(java.sql.Date fecha) {
        this.fecha = fecha;
    }

    public int getNumDias() {
        return numDias;
    }

    public void setNumDias(int numDias) {
        this.numDias = numDias;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "ExcursionEntidad{" +
                "idExcursion='" + idExcursion + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fecha=" + fecha +
                ", numDias=" + numDias +
                ", precio=" + precio +
                ", codigo='" + codigo + '\'' +
                '}';
    }
}
