package mx.edu.uacm.is.slt.as.sistpolizas.RestController;

import java.text.ParseException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import  java.util.List;
import java.util.Optional;
import mx.edu.uacm.is.slt.as.sistpolizas.AuxiliarF.Convertir;

import mx.edu.uacm.is.slt.as.sistpolizas.model.Poliza;
import mx.edu.uacm.is.slt.as.sistpolizas.service.PolizaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/api/v1")
public class PolizaRestController {
 
    @Autowired
    PolizaService polizaService;
    
    public PolizaRestController() {
    }
    
    @GetMapping("/polizas")
    public List<Poliza> getPolizas(){
       return polizaService.getPolizas();
    }
    
    @GetMapping("/{clave}")
    public Optional<Poliza> getPolizaByClave(@PathVariable String clave){
            
        return polizaService.getPolizaByClave(clave);
    }
  
     @GetMapping("/t/{tipo}")
    public List<Poliza> getPolizaByTipo(@PathVariable String tipo){
        return polizaService.getPolizasByTipo(Convertir.stringAInt(tipo));
    }
    
      @GetMapping("/c/{curp}")
    public List<Poliza> getPolizaByCurp(@PathVariable String curp){
        return polizaService.getPolizasByCurp(curp);
    }
    
      @GetMapping({"/c/{nomres}/{primer_apellido}/{segundo_apellido}", "/c/{nomres}/{primer_apellido}/", "/c/{nomres}/{primer_apellido}"})
    public List<Poliza> getPolizaByNobre(@PathVariable String nomres, @PathVariable String primer_apellido, @PathVariable(required = false) String segundo_apellido){
        if(segundo_apellido == null){
            return polizaService.getPolizasByCliente(nomres, primer_apellido);
        }else{
            return polizaService.getPolizasByCliente(nomres, primer_apellido, segundo_apellido);
        }    
    }    
    
      @GetMapping({"/b/{nomres}/{primer_apellido}/{segundo_apellido}", "/b/{nomres}/{primer_apellido}/", "/b/{nomres}/{primer_apellido}"})
    public List<Poliza> getPolizasByBeneficiario(@PathVariable String nomres, @PathVariable String primer_apellido, @PathVariable(required = false) String segundo_apellido){
        
        if(segundo_apellido == null){
            return polizaService.getPolizasByBeneficiario(nomres, primer_apellido);
        }else{
            return polizaService.getPolizasByBeneficiario(nomres, primer_apellido, segundo_apellido);
        }        } 
    
      @GetMapping("/b/{fechaNacimientoBeneficiario}")
    public List<Poliza> getPolizasByFechaNacimientoBeneficiario(@PathVariable String fechaNacimientoBeneficiario) throws ParseException{
        
        return polizaService.getPolizasByBeneficiariosFechaNacimiento(fechaNacimientoBeneficiario);
    }
    
    @PostMapping("/{clave}/{tipo}/{monto}/{descripcion}/{curpCliente}")
    public Optional<Poliza> agregarPoliza(@RequestBody Poliza poliza){
        return polizaService.agregarPoliza(poliza);
    }
        
    @PostMapping("/polizas")
    public List<Poliza>  agregarPolizas(@RequestBody List<Poliza> polizas){
        polizaService.agregarPolizas(polizas);
        return  polizas;
    }    
    
        @DeleteMapping("/polizas")
    public void eliminarPolizas(){
        polizaService.eliminarPolizas();  
    }
    
    @DeleteMapping("/{clave}")
    public void eliminarPolizaByClave(@PathVariable String clave){
        polizaService.eliminarPoliza(clave);
    }
}
