package JavaDBArchitects.modelo;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "federados")
public class Federado extends Socio {

    @Column(name = "nif")
    private String NIF;

    @ManyToOne
    @JoinColumn(name = "id_federacion", referencedColumnName = "id_federacion")
    private Federacion federacion;

    public Federado() {}

    public Federado(String nombre, String nif, Federacion federacion, BigDecimal cuotaMensual) {
        super(0, nombre, "FEDERADO", nif, null, federacion != null ? federacion.getIdFederacion() : null, null, cuotaMensual);
        this.NIF = nif;
        this.federacion = federacion;
    }

    @Override
    public double calcularCuotaMensual() {
        return getCuotaMensual().doubleValue();
    }

    public String getNIF() {
        return NIF;
    }

    public void setNIF(String NIF) {
        this.NIF = NIF;
    }

    public Federacion getFederacion() {
        return federacion;
    }

    public void setFederacion(Federacion federacion) {
        this.federacion = federacion;
    }

    @Override
    public String toString() {
        return String.format("Federado \n" +
                        "Número de Socio: %d\n" +
                        "Nombre: '%s'\n" +
                        "NIF: '%s'\n" +
                        "%s\n" +
                        "Cuota Mensual: %s€\n",
                getNumeroSocio(), getNombre(), NIF, federacion != null ? federacion.toString() : "Sin federación", getCuotaMensual());
    }
}
