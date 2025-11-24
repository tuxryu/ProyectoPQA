package mx.edu.uacm.is.slt.as.sistpolizas.repository;

import java.util.List;
import java.util.UUID;
import mx.edu.uacm.is.slt.as.sistpolizas.modelo.Poliza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolizaRepository extends JpaRepository<Poliza, String>{

    List<Poliza> findByTipo(int tipo);
    List<Poliza> findByCurpCliente(String curp);

}