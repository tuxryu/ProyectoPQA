package mx.edu.uacm.is.slt.as.sistpolizas.modelo; // (Revisa que tu paquete sea el correcto)

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity
public class Poliza {

    @Id
    private String clave;         // Clave única de la póliza
    private int tipo;          // Tipo de seguro: auto, vida o médico
    private double monto;         // Monto asegurado
    private String descripcion;   // Descripción del seguro
    private String curpCliente;      // Cliente asegurado (relación con otra clase)

    public Poliza() {
    }

    // Constructor
    public Poliza(String clave, int tipo, double monto, String descripcion, String curpCliente) {
        this.clave = clave;
        this.tipo = tipo;
        this.monto = monto;
        this.descripcion = descripcion;
        this.curpCliente = curpCliente;
    }

    public boolean compare(Poliza poliza){
        return this.clave.equals(poliza.clave) && this.tipo == poliza.tipo && this.monto == poliza.monto && this.descripcion.equals(poliza.descripcion) && this.curpCliente.equals(poliza.curpCliente);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.clave);
        hash = 89 * hash + this.tipo;
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.monto) ^ (Double.doubleToLongBits(this.monto) >>> 32));
        hash = 89 * hash + Objects.hashCode(this.descripcion);
        hash = 89 * hash + Objects.hashCode(this.curpCliente);
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
        final Poliza other = (Poliza) obj;
        if (this.tipo != other.tipo) {
            return false;
        }
        if (Double.doubleToLongBits(this.monto) != Double.doubleToLongBits(other.monto)) {
            return false;
        }
        if (!Objects.equals(this.clave, other.clave)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        return Objects.equals(this.curpCliente, other.curpCliente);
    }



    // Getters y Setters
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;

    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCurpCliente() {
        return curpCliente;
    }

    public void setCurpCliente(String curpCliente) {
        this.curpCliente = curpCliente;
    }
}