package mx.edu.uacm.is.slt.as.sistpolizas.restController;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


import mx.edu.uacm.is.slt.as.sistpolizas.modelo.Beneficiario;
import mx.edu.uacm.is.slt.as.sistpolizas.modelo.IdBeneficiario;
import mx.edu.uacm.is.slt.as.sistpolizas.service.BeneficiarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import mx.edu.uacm.is.slt.as.sistpolizas.AuxiliarF.Convertir;

@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/api/v1")
public class BeneficiarioRestController {
    
    @Autowired
    BeneficiarioService beneficiarioService;

    public BeneficiarioRestController() {
    }

    @PostMapping("/{fechaNacimiento}/{polizaClave}/{nombres}/{primerApellido}/{segundoApellido}")
    public Beneficiario agrgarBeneficiario(@RequestBody Beneficiario beneficiario) {
        return beneficiarioService.agregarBeneficiario(beneficiario);
    }

    @PostMapping("/beneficiario")
    public Beneficiario agregarBeneficiario(@RequestBody Beneficiario beneficiario) {
        return beneficiarioService.agregarBeneficiario(beneficiario);
    }
     @PostMapping("/beneficiarios")
    public List<Beneficiario> agregarBeneficiarios(@RequestBody List<Beneficiario> beneficiarios) {
        return beneficiarioService.agregarBeneficiarios(beneficiarios);
    }
    
    @GetMapping("/beneficiarios")
    public List<Beneficiario> getBeneficiarios() {
        return beneficiarioService.getBeneficiarios();
    }
    
    @GetMapping("/beneficiario/{fechaNacimiento}/{clavePoliza}/{nombres}/{primerApellido}/{segundoApellido}")
    public Optional<Beneficiario> getBeneficiario(
            @PathVariable String nombres, 
            @PathVariable String primerApellido, 
            @PathVariable(required = false) String segundoApellido, 
            @PathVariable String fechaNacimiento, 
            @PathVariable String clavePoliza) {
        
        OffsetDateTime fechaConvertida = Convertir.stringAOffsetDateTime(fechaNacimiento);
        IdBeneficiario id = new IdBeneficiario(nombres, primerApellido, segundoApellido, fechaConvertida, clavePoliza);
        return beneficiarioService.getBeneficiario(id);
    }
    
    @PostMapping("/beneficiario/manual/{fechaNacimiento}/{polizaClave}/{nombres}/{primerApellido}/{segundoApellido}")
    public Beneficiario agrgarBeneficiarioManual(@RequestBody Beneficiario beneficiario) {
        return beneficiarioService.agregarBeneficiario(beneficiario);
    }
    

    
}
