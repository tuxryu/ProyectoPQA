package mx.edu.uacm.is.slt.as.sistpolizas.repository;

import java.util.Optional;
import mx.edu.uacm.is.slt.as.sistpolizas.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String>{
    Optional<Cliente> findByNombresAndPrimerApellidoAndSegundoApellido(String nombres, String primerApellido, String segundoApellido);
    Optional<Cliente> findByNombresAndPrimerApellido(String nombres, String primerApellido);

}