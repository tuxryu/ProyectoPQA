package mx.edu.uacm.is.slt.as.sistpolizas.RestController;

import java.time.LocalDate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import mx.edu.uacm.is.slt.as.sistpolizas.AuxiliarF.Convertir;

import mx.edu.uacm.is.slt.as.sistpolizas.model.Beneficiario;
import mx.edu.uacm.is.slt.as.sistpolizas.model.IdBeneficiario;
import mx.edu.uacm.is.slt.as.sistpolizas.service.BeneficiarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/beneficiarios")
public class BeneficiarioRestController {
    
    @Autowired
    BeneficiarioService beneficiarioService;

    public BeneficiarioRestController() {
    }

    @PostMapping("/{fechaNacimiento}/{polizaClave}/{nombres}/{primerApellido}/{segundoApellido}")
    public Beneficiario agrgarBeneficiario(@RequestBody Beneficiario beneficiario) {
        return beneficiarioService.agregarBeneficiario(beneficiario);
    }

    @PostMapping()
    public List<Beneficiario> agregarBeneficiarios(@RequestBody List<Beneficiario> beneficiarios) {
        return beneficiarioService.agregarBeneficiarios(beneficiarios);
    }    
    
    @GetMapping
    public List<Beneficiario> getBeneficiarios() {
        return beneficiarioService.getBeneficiarios();
    }
    
    @GetMapping("/{fechaNacimiento}/{clavePoliza}/{nombres}/{primerApellido}/{segundoApellido}")
    public Optional<Beneficiario> getBeneficiario(@PathVariable String nombres, @PathVariable String primerApellido, @PathVariable(required = false) String segundoApellido, @PathVariable LocalDate fechaNacimiento, @PathVariable String clavePoliza) {
        IdBeneficiario id = new IdBeneficiario(nombres, primerApellido, segundoApellido, fechaNacimiento, clavePoliza);
        return beneficiarioService.getBeneficiario(id);
    } 
    

    
}
