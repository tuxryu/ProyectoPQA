package mx.edu.uacm.is.slt.as.sistpolizas.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity
public class Cliente {

    @Id
    private String curp;
    private String nombres;
    private String primerApellido;
    private String segundoApellido;
    private String direccion;
    private String fechaNacimiento;

    // Constructor

    public Cliente() {
    }

    public Cliente(String nombres, String primerApellido, String segundoApellido, String direccion, String curp, String fechaNacimiento) {
        this.nombres = nombres;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.direccion = direccion;
        this.curp = curp;
        this.fechaNacimiento = fechaNacimiento;
    }

    public boolean compare(Cliente cliente){
        return this.curp.equals(cliente.curp) && this.nombres.equals(cliente.nombres) && this.primerApellido.equals(cliente.primerApellido) && this.segundoApellido.equals(cliente.segundoApellido) && this.fechaNacimiento.equals(cliente.fechaNacimiento) && this.direccion.equals(cliente.direccion);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.curp);
        hash = 73 * hash + Objects.hashCode(this.nombres);
        hash = 73 * hash + Objects.hashCode(this.primerApellido);
        hash = 73 * hash + Objects.hashCode(this.segundoApellido);
        hash = 73 * hash + Objects.hashCode(this.direccion);
        hash = 73 * hash + Objects.hashCode(this.fechaNacimiento);
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
        final Cliente other = (Cliente) obj;
        if (!Objects.equals(this.curp, other.curp)) {
            return false;
        }
        if (!Objects.equals(this.nombres, other.nombres)) {
            return false;
        }
        if (!Objects.equals(this.primerApellido, other.primerApellido)) {
            return false;
        }
        if (!Objects.equals(this.segundoApellido, other.segundoApellido)) {
            return false;
        }
        if (!Objects.equals(this.direccion, other.direccion)) {
            return false;
        }
        return Objects.equals(this.fechaNacimiento, other.fechaNacimiento);
    }



    // Getters y Setters
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

}