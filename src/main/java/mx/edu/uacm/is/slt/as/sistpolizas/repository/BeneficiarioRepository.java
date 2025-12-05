package mx.edu.uacm.is.slt.as.sistpolizas.repository;

import java.time.OffsetDateTime;
import java.util.Date;
import mx.edu.uacm.is.slt.as.sistpolizas.modelo.Beneficiario;
import mx.edu.uacm.is.slt.as.sistpolizas.modelo.IdBeneficiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeneficiarioRepository extends JpaRepository<Beneficiario, IdBeneficiario> {
    List<Beneficiario> findById_NombresAndId_PrimerApellidoAndId_SegundoApellido(String nombres, String primerApellido, String segundoApellido);
    List<Beneficiario> findById_NombresAndId_PrimerApellido(String nombres, String primerApellido);
    List<Beneficiario> findByIdFechaNacimiento(OffsetDateTime fechaNacimiento);
//Buscar todos los beneficiarios de una p�liza espec�fica
    List<Beneficiario> findByPoliza_Clave(String clavePoliza);
}
