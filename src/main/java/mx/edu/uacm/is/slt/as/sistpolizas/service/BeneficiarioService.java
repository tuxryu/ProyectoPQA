package mx.edu.uacm.is.slt.as.sistpolizas.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import mx.edu.uacm.is.slt.as.sistpolizas.modelo.Beneficiario;
import mx.edu.uacm.is.slt.as.sistpolizas.modelo.IdBeneficiario;
import mx.edu.uacm.is.slt.as.sistpolizas.repository.BeneficiarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BeneficiarioService {

    @Autowired
    private BeneficiarioRepository beneficiarioRepository;

    // Crear o actualizar
    public Beneficiario agregarBeneficiario(Beneficiario beneficiario) {
        return beneficiarioRepository.save(beneficiario);
    }
    
    // Crear o actualizar
    public List<Beneficiario> agregarBeneficiarios(List<Beneficiario> beneficiarios) {
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

        public List<Beneficiario> getBeneficiariosByNombresApellidos(String nombres, String primerApellido, String segundoApellido){
        return beneficiarioRepository.findById_NombresAndId_PrimerApellidoAndId_SegundoApellido(nombres, primerApellido, segundoApellido);
    }

    public List<Beneficiario> getBeneficiariosByNombresApellido(String nombres, String primerApellido){
        return beneficiarioRepository.findById_NombresAndId_PrimerApellido(nombres, primerApellido);
    }
    
    public List<Beneficiario> getBeneficiariosByFechaNacimiento(String fechaNacimiento) throws ParseException{
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fecha = sdf.parse(fechaNacimiento);
            return beneficiarioRepository.findByIdFechaNacimiento(fecha);
    }
    
    // Eliminar por ID
    public void deleteBeneficiarios(IdBeneficiario id) {
        beneficiarioRepository.deleteById(id);
    }
    
    // Eliminar por ID
    public void deleteBeneficiarioss() {
        beneficiarioRepository.deleteAll();
    }
}
