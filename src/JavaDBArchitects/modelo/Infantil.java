package JavaDBArchitects.modelo;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "infantiles")
public class Infantil extends Socio {

    @Column(name = "num_socio_padre")
    private Integer numSocioPadreOMadre;

    public Infantil() {}

    public Infantil(String nombre, Integer numSocioPadreOMadre, BigDecimal cuotaMensual) {
        super(0, nombre, "INFANTIL", null, null, null, numSocioPadreOMadre, cuotaMensual);
        this.numSocioPadreOMadre = numSocioPadreOMadre;
    }


    @Override
    public double calcularCuotaMensual() {
        return getCuotaMensual().doubleValue();
    }

    public Integer getNumSocioPadreOMadre() {
        return numSocioPadreOMadre;
    }

    public void setNumSocioPadreOMadre(Integer numSocioPadreOMadre) {
        this.numSocioPadreOMadre = numSocioPadreOMadre;
    }

    @Override
    public String toString() {
        return String.format("Infantil \n" +
                        "Número de Socio: %d\n" +
                        "Nombre: '%s'\n" +
                        "NIF del tutor: '%s'\n" +
                        "Cuota Mensual: %s€\n",
                getNumeroSocio(), getNombre(), numSocioPadreOMadre, getCuotaMensual());
    }
}



