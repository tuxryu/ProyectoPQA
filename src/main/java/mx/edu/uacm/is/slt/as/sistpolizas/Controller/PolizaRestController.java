package mx.edu.uacm.is.slt.as.sistpolizas.RestController;

import java.text.ParseException;


import  java.util.List;
import java.util.Optional;
import mx.edu.uacm.is.slt.as.sistpolizas.AuxiliarF.Convertir;

import mx.edu.uacm.is.slt.as.sistpolizas.model.Poliza;
import mx.edu.uacm.is.slt.as.sistpolizas.service.PolizaService;



public class PolizaRestController {
 
 
    
    public PolizaRestController() {
    }
    
  
    public List<Poliza> getPolizas(){
       return polizaService.getPolizas();
    }
    
   
    public Optional<Poliza> getPolizaByClave(@PathVariable String clave){
            
        return polizaService.getPolizaByClave(clave);
    }
  
    
    public List<Poliza> getPolizaByTipo(@PathVariable String tipo){
        return polizaService.getPolizasByTipo(Convertir.stringAInt(tipo));
    }
    
      
    public List<Poliza> getPolizaByCurp(@PathVariable String curp){
        return polizaService.getPolizasByCurp(curp);
    }
    
    public List<Poliza> getPolizaByNobre(@PathVariable String nomres, @PathVariable String primer_apellido, @PathVariable(required = false) String segundo_apellido){
        if(segundo_apellido == null){
            return polizaService.getPolizasByCliente(nomres, primer_apellido);
        }else{
            return polizaService.getPolizasByCliente(nomres, primer_apellido, segundo_apellido);
        }    
    }    
    
    public List<Poliza> getPolizasByBeneficiario(@PathVariable String nomres, @PathVariable String primer_apellido, @PathVariable(required = false) String segundo_apellido){
        
        if(segundo_apellido == null){
            return polizaService.getPolizasByBeneficiario(nomres, primer_apellido);
        }else{
            return polizaService.getPolizasByBeneficiario(nomres, primer_apellido, segundo_apellido);
        }        } 
    
    public List<Poliza> getPolizasByFechaNacimientoBeneficiario(@PathVariable String fechaNacimientoBeneficiario) throws ParseException{
        
        return polizaService.getPolizasByBeneficiariosFechaNacimiento(fechaNacimientoBeneficiario);
    }
    
    public Optional<Poliza> agregarPoliza(@RequestBody Poliza poliza){
        return polizaService.agregarPoliza(poliza);
    }
        
    
    public List<Poliza>  agregarPolizas(@RequestBody List<Poliza> polizas){
        polizaService.agregarPolizas(polizas);
        return  polizas;
    }    
    
      
    public void eliminarPolizas(){
        polizaService.eliminarPolizas();  
    }
    
   
    public void eliminarPolizaByClave(@PathVariable String clave){
        polizaService.eliminarPoliza(clave);
    }
}