package mx.edu.uacm.is.slt.as.sistpolizas.service;

import java.text.ParseException;
import java.time.OffsetDateTime; // Actualizado para coincidir con tu IdBeneficiario
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.uacm.is.slt.as.sistpolizas.AuxiliarF.Convertir;
import mx.edu.uacm.is.slt.as.sistpolizas.modelo.Beneficiario;
import mx.edu.uacm.is.slt.as.sistpolizas.modelo.IdBeneficiario;
import mx.edu.uacm.is.slt.as.sistpolizas.modelo.Poliza;
import mx.edu.uacm.is.slt.as.sistpolizas.repository.BeneficiarioRepository;
import mx.edu.uacm.is.slt.as.sistpolizas.repository.PolizaRepository; 
@Service
public class BeneficiarioService {

    @Autowired
    private BeneficiarioRepository beneficiarioRepository;

    @Autowired
    private PolizaRepository polizaRepository; // INYECTAMOS EL REPOSITORIO DE PÓLIZAS

    // Crear o actualizar UNO 
    public Beneficiario agregarBeneficiario(Beneficiario beneficiario) {
        // Validación: Antes de guardar, buscamos al "Padre" (Póliza) y se lo asignamos
        if (beneficiario.getId() != null && beneficiario.getId().getClavePoliza() != null) {
            String clavePoliza = beneficiario.getId().getClavePoliza();
            
            // Buscamos la póliza real en la BD
            Optional<Poliza> polizaPadre = polizaRepository.findById(clavePoliza);
            
            // Si existe, conectamos los cables para que Hibernate no falle
            if (polizaPadre.isPresent()) {
                beneficiario.setPoliza(polizaPadre.get());
            }
        }
        return beneficiarioRepository.save(beneficiario);
    }
    
    // Crear o actualizar LISTA 
    public List<Beneficiario> agregarBeneficiarios(List<Beneficiario> beneficiarios) {
        for (Beneficiario b : beneficiarios) {
            if (b.getId() != null && b.getId().getClavePoliza() != null) {
                String clavePoliza = b.getId().getClavePoliza();
                // Si encontramos la póliza, se la asignamos al beneficiario actual
                polizaRepository.findById(clavePoliza).ifPresent(p -> b.setPoliza(p));
            }
        }
        return beneficiarioRepository.saveAll(beneficiarios);
    }    

    // Obtener todos
    public List<Beneficiario> getBeneficiarios() {
        return beneficiarioRepository.findAll();
    }

    // Buscar por ID
    public Optional<Beneficiario> getBeneficiario(IdBeneficiario id) {
        return beneficiarioRepository.findById(id);
    }

    // Tus métodos personalizados se quedan igual
    public List<Beneficiario> getBeneficiariosByNombresApellidos(String nombres, String primerApellido, String segundoApellido){
        return beneficiarioRepository.findById_NombresAndId_PrimerApellidoAndId_SegundoApellido(nombres, primerApellido, segundoApellido);
    }

    public List<Beneficiario> getBeneficiariosByNombresApellido(String nombres, String primerApellido){
        return beneficiarioRepository.findById_NombresAndId_PrimerApellido(nombres, primerApellido);
    }
    
    // ACTUALIZACIÓN: Cambiamos Date por OffsetDateTime porque IdBeneficiario ya no usa Date
    public List<Beneficiario> getBeneficiariosByFechaNacimiento(String fechaNacimiento) throws ParseException {
            // Usamos tu clase Convertir para evitar errores de formato
            OffsetDateTime fecha = Convertir.stringAOffsetDateTime(fechaNacimiento);
            return beneficiarioRepository.findByIdFechaNacimiento(fecha);
    }
    
    // Eliminar por ID
    public void deleteBeneficiarios(IdBeneficiario id) {
        beneficiarioRepository.deleteById(id);
    }
    
    // Eliminar todos
    public void deleteBeneficiarioss() {
        beneficiarioRepository.deleteAll();
    }
}
