package mx.edu.uacm.is.slt.as.sistpolizas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "beneficiarios")
public class Beneficiario {

    @EmbeddedId
    private IdBeneficiario id;

    @MapsId("clavePoliza")
    @ManyToOne(optional = false)
    @JoinColumn(name = "clave_poliza", referencedColumnName = "clave")
    private Poliza poliza;

    private double porcentaje;

    public Beneficiario() {
    }

    public Beneficiario(IdBeneficiario id, double porcentaje, Poliza poliza) {
        this.id = id;
        this.porcentaje = porcentaje;
        this.poliza = poliza;
    }

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

    public Poliza getPoliza() {
        return poliza;
    }

    public void setPoliza(Poliza poliza) {
        this.poliza = poliza;
    }
}
