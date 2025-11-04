package mx.edu.uacm.is.slt.as.sistpolizas.modelo;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class IdBeneficiario implements Serializable {

    private String nombres;
    private String primerApellido;
    private String segundoApellido;
    private LocalDate fechaNacimiento;
    private String poliza;

    public IdBeneficiario() {
    }

    public IdBeneficiario(String nombres, String primerApellido, String segundoApellido, LocalDate fechaNacimiento, String poliza) {
        this.nombres = nombres;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.fechaNacimiento = fechaNacimiento;
        this.poliza = poliza;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getPoliza() {
        return poliza;
    }

    public void setPoliza(String poliza) {
        this.poliza = poliza;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.nombres);
        hash = 67 * hash + Objects.hashCode(this.primerApellido);
        hash = 67 * hash + Objects.hashCode(this.segundoApellido);
        hash = 67 * hash + Objects.hashCode(this.fechaNacimiento);
        hash = 67 * hash + Objects.hashCode(this.poliza);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IdBeneficiario other = (IdBeneficiario) obj;
        if (!Objects.equals(this.nombres, other.nombres)) {
            return false;
        }
        if (!Objects.equals(this.primerApellido, other.primerApellido)) {
            return false;
        }
        if (!Objects.equals(this.segundoApellido, other.segundoApellido)) {
            return false;
        }
        if (!Objects.equals(this.poliza, other.poliza)) {
            return false;
        }
        return Objects.equals(this.fechaNacimiento, other.fechaNacimiento);
    }



}