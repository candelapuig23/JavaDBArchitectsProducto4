package JavaDBArchitects.modelo;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "estandars")
public class Estandar extends Socio {

    @Column(name = "nif")
    private String NIF;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_seguro", referencedColumnName = "id_seguro")
    private Seguro seguro;

    public Estandar() {}

    public Estandar(String nombre, String nif, TipoSeguro tipoSeguro, BigDecimal cuotaMensual) {
        super(0, nombre, "ESTANDAR", nif, tipoSeguro, null, null, cuotaMensual);
        this.NIF = nif;
        this.seguro = new Seguro(tipoSeguro, cuotaMensual.floatValue());
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

    public TipoSeguro getTipoSeguro() {
        return seguro != null ? seguro.getTipo() : null;
    }

    public Seguro getSeguro() {
        return seguro;
    }

    public void setSeguro(Seguro seguro) {
        this.seguro = seguro;
    }

    @Override
    public String toString() {
        return "Estandar \n" +
                "Número de Socio: " + getNumeroSocio() + "\n" +
                "Nombre: '" + getNombre() + "'\n" +
                "NIF: '" + getNIF() + "'\n" +
                "Seguro: " + seguro + "\n" +
                "Cuota Mensual: " + getCuotaMensual() + "€\n";
    }
}
