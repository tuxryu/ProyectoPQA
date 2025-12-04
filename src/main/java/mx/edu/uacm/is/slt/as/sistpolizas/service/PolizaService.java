package mx.edu.uacm.is.slt.as.sistpolizas.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import mx.edu.uacm.is.slt.as.sistpolizas.AuxiliarF.Convertir;
import mx.edu.uacm.is.slt.as.sistpolizas.modelo.Beneficiario;
import mx.edu.uacm.is.slt.as.sistpolizas.modelo.Cliente;
import mx.edu.uacm.is.slt.as.sistpolizas.modelo.Poliza;
import mx.edu.uacm.is.slt.as.sistpolizas.repository.PolizaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PolizaService {
    @Autowired
    PolizaRepository polizaRepository;

    @Autowired
    ClienteService clienteService;

    @Autowired
    BeneficiarioService beneficiarioService;

    public List<Poliza> getPolizas() {
        return polizaRepository.findAll();
    }

    public Optional<Poliza> getPolizaByClave(String clave){
        return polizaRepository.findById(clave);
    }

    public List<Poliza> getPolizasByTipo(int tipo){
        return  polizaRepository.findByTipo(tipo);
    }

    public List<Poliza> getPolizasByCurp(String curp){
        return polizaRepository.findByCurpCliente(curp);
    }

    public List<Poliza> getPolizasByCliente(String nombres, String primerApellido, String segundoApellido) {
        Optional<Cliente> clienteOpt = clienteService.getClienteByNombresApellidos(nombres, primerApellido, segundoApellido);

        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            return polizaRepository.findByCurpCliente(cliente.getCurp()); // usa el nombre correcto del campo en la entidad
        } else {
            // Cliente no encontrado, puedes lanzar una excepción o devolver una lista vacía
            return new ArrayList<>();
        }
    }

    public List<Poliza> getPolizasByCliente(String nombres, String primerApellido){
        Optional<Cliente> clienteOpt = clienteService.getClienteByNombresApellido(nombres, primerApellido);

        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            return polizaRepository.findByCurpCliente(cliente.getCurp()); // usa el nombre correcto del campo en la entidad
        } else {
            // Cliente no encontrado, puedes lanzar una excepción o devolver una lista vacía
            return new ArrayList<>();
        }
    }

    public List<Poliza> getPolizasByBeneficiario(String nombres, String primerApellido, String segundoApellido) {
        List<Beneficiario> beneficiariosList = beneficiarioService.getBeneficiariosByNombresApellidos(nombres, primerApellido, segundoApellido);
        List<Poliza> polizasList = new ArrayList<>();
        if (beneficiariosList.isEmpty()) {
            for(int i = 0; i<polizasList.size(); i++){
                polizasList.add(polizaRepository.getReferenceById(beneficiariosList.get(i).getId().getClavePoliza()));
            }
            return polizasList;
        } else {
            // Cliente no encontrado, puedes lanzar una excepción o devolver una lista vacía
            return new ArrayList<>();
        }
    }

    public List<Poliza> getPolizasByBeneficiario(String nombres, String primerApellido){
        List<Beneficiario> beneficiariosList = beneficiarioService.getBeneficiariosByNombresApellido(nombres, primerApellido);
        List<Poliza> polizasList = new ArrayList<>();
        if (beneficiariosList.isEmpty()) {
            for(int i = 0; i<polizasList.size(); i++){
                polizasList.add(polizaRepository.getReferenceById(beneficiariosList.get(i).getId().getClavePoliza()));
            }
            return polizasList;
        } else {
            // Cliente no encontrado, puedes lanzar una excepción o devolver una lista vacía
            return new ArrayList<>();
        }
    }

    public List<Poliza> getPolizasByBeneficiariosFechaNacimiento(String fechaNacimiento) throws ParseException{
        List<Beneficiario> beneficiariosList = beneficiarioService.getBeneficiariosByFechaNacimiento(fechaNacimiento);
        List<Poliza> polizasList = new ArrayList<>();
        if (beneficiariosList.isEmpty()) {
            for(int i = 0; i<polizasList.size(); i++){
                polizasList.add(polizaRepository.getReferenceById(beneficiariosList.get(i).getId().getClavePoliza()));
            }
            return polizasList;
        } else {
            // Cliente no encontrado, puedes lanzar una excepción o devolver una lista vacía
            return new ArrayList<>();
        }
    }

    public  Optional<Poliza> agregarPoliza(Poliza poliza){
        polizaRepository.save(poliza);
        return polizaRepository.findById(poliza.getClave());
    }

    public void agregarPolizas(List<Poliza> polizas){
        polizaRepository.saveAll(polizas);
    }

    public  void eliminarPoliza(String clave){
        polizaRepository.deleteById(clave);
    }

    public void eliminarPolizas() {
        polizaRepository.deleteAll();
    }

}