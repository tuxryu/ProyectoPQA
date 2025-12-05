package mx.edu.uacm.is.slt.as.sistpolizas.modelo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    private String curp;

    private String nombres;

    private String primerApellido;

    private String segundoApellido;

    private String direccion;
    
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
private OffsetDateTime fechaNacimiento;

    
    public Cliente() {
    }
    
    public Cliente(boolean existe) {
        this.nombres = null;
        this.primerApellido = null;
        this.segundoApellido = null;
        this.direccion = null;
        this.curp = null;
        this.fechaNacimiento = null;    
    }

    public Cliente(String nombres, String primerApellido, String segundoApellido, String direccion, String curp, OffsetDateTime fechaNacimiento) {
        this.nombres = nombres;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.direccion = direccion;
        this.curp = curp;
        this.fechaNacimiento = fechaNacimiento;
    }
    
    public Cliente(String nombres, String primerApellido, String direccion, String curp, OffsetDateTime fechaNacimiento) {
        this.nombres = nombres;
        this.primerApellido = primerApellido;
        this.segundoApellido = null;
        this.direccion = direccion;
        this.curp = curp;
        this.fechaNacimiento = fechaNacimiento;
    }    

    public boolean compare(Cliente cliente) {
        return this.curp.equals(cliente.curp)
            && this.nombres.equals(cliente.nombres)
            && this.primerApellido.equals(cliente.primerApellido)
            && this.segundoApellido.equals(cliente.segundoApellido)
            && this.fechaNacimiento.equals(cliente.fechaNacimiento)
            && this.direccion.equals(cliente.direccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(curp, nombres, primerApellido, segundoApellido, direccion, fechaNacimiento);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cliente other = (Cliente) obj;
        return Objects.equals(curp, other.curp)
            && Objects.equals(nombres, other.nombres)
            && Objects.equals(primerApellido, other.primerApellido)
            && Objects.equals(segundoApellido, other.segundoApellido)
            && Objects.equals(direccion, other.direccion)
            && Objects.equals(fechaNacimiento, other.fechaNacimiento);
    }

    // Getters y setters
    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public OffsetDateTime getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(OffsetDateTime fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
