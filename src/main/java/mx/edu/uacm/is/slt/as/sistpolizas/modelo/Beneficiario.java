package mx.edu.uacm.is.slt.as.sistpolizas.modelo;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class Beneficiario {

    @EmbeddedId
    private IdBeneficiario id;

    private double porcentaje;

    public Beneficiario() {
    }

    public Beneficiario(IdBeneficiario id, double porcentaje) {
        this.id = id;
        this.porcentaje = porcentaje;
    }

    // Getters y Setters
    public IdBeneficiario getId() {
        return id;
    }

    public void setId(IdBeneficiario id) {
        this.id = id;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }
}
