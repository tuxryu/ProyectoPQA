
package mx.edu.uacm.is.slt.as.sistpolizas.apiClient;

import java.util.ArrayList;
import java.util.List;
import mx.edu.uacm.is.slt.as.sistpolizas.httpClient.*;
import mx.edu.uacm.is.slt.as.sistpolizas.model.Beneficiario;
import mx.edu.uacm.is.slt.as.sistpolizas.model.Cliente;
import mx.edu.uacm.is.slt.as.sistpolizas.model.Poliza;

public class ApiClientRemoto extends ApiClient{


    public ApiClientRemoto() {
        this.clienteHttpClient = new ClienteHttpClient("http://nachintoch.mx:8080");
        this.polizaHttpClient = new PolizaHttpClient("http://nachintoch.mx:8080");
        this.beneficiarioHttpClient = new BeneficiarioHttpClient("http://nachintoch.mx:8080");
    }
    

    public List<Beneficiario> getBeneficiariosByPoliza(String clavePolizaBuscada) throws Exception {
        System.out.println("--- Iniciando descarga masiva de beneficiarios para filtrar ---");
        
        // 1. Descargamos TODOS los beneficiarios del servidor
        List<Beneficiario> todos = beneficiarioHttpClient.getTodosLosBeneficiarios();
        
        System.out.println("Total de beneficiarios descargados: " + todos.size());
        
        // 2. Creamos una lista vacía para guardar los que coincidan
        List<Beneficiario> filtrados = new ArrayList<>();

        // 3. Revisamos uno por uno (Filtro manual)
        for (Beneficiario b : todos) {
            // Verificamos que el ID y la clave de póliza no sean nulos
            if (b.getId() != null && b.getId().getClavePoliza() != null) {
                // Comparamos si la clave de la póliza del beneficiario es igual a la que buscamos
                if (b.getId().getClavePoliza().equals(clavePolizaBuscada)) {
                    filtrados.add(b);
                }
            }
        }
        
        System.out.println("Beneficiarios encontrados para la póliza " + clavePolizaBuscada + ": " + filtrados.size());
        return filtrados;
    }
    
    public List<Cliente> getClientes() throws Exception{
        List<Poliza> polizas = polizaHttpClient.getPolizas();
        List<String> curps = new ArrayList<>();
        List<Cliente> clientes = new ArrayList<>();

        for (Poliza poliza : polizas) {
            String curp = poliza.getCurpCliente();
            if (curp != null && !curps.contains(curp)) {
                curps.add(curp);
            }
        }

        for(String curp : curps){
            clientes.add(clienteHttpClient.getClienteByCurp(curp));
        }
        
        return clientes ;
    }    
}
