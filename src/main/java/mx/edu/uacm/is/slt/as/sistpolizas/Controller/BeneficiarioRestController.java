package mx.edu.uacm.is.slt.as.sistpolizas.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import mx.edu.uacm.is.slt.as.sistpolizas.AuxiliarF.Convertir;
import mx.edu.uacm.is.slt.as.sistpolizas.model.Beneficiario;
import mx.edu.uacm.is.slt.as.sistpolizas.model.IdBeneficiario;
import mx.edu.uacm.is.slt.as.sistpolizas.service.BeneficiarioService;



public class BeneficiarioRestController {
    
   

    public BeneficiarioRestController() {
    }

   
    public Beneficiario agrgarBeneficiario(@RequestBody Beneficiario beneficiario) {
        return beneficiarioService.agregarBeneficiario(beneficiario);
    }

   
    public List<Beneficiario> agregarBeneficiarios(@RequestBody List<Beneficiario> beneficiarios) {
        return beneficiarioService.agregarBeneficiarios(beneficiarios);
    }    
    
    
    public List<Beneficiario> getBeneficiarios() {
        return beneficiarioService.getBeneficiarios();
    }
    
    public Optional<Beneficiario> getBeneficiario(@PathVariable String nombres, @PathVariable String primerApellido, @PathVariable(required = false) String segundoApellido, @PathVariable LocalDate fechaNacimiento, @PathVariable String clavePoliza) {
        IdBeneficiario id = new IdBeneficiario(nombres, primerApellido, segundoApellido, fechaNacimiento, clavePoliza);
        return beneficiarioService.getBeneficiario(id);
    } 
    

    
}