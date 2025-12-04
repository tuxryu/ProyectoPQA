
package mx.edu.uacm.is.slt.as.sistpolizas.apiClient;

import java.util.List;
import mx.edu.uacm.is.slt.as.sistpolizas.httpClient.ClienteHttpClient;
import mx.edu.uacm.is.slt.as.sistpolizas.httpClient.PolizaHttpClient;
import mx.edu.uacm.is.slt.as.sistpolizas.model.Cliente;
import mx.edu.uacm.is.slt.as.sistpolizas.model.Poliza;

public class ApiClientLocal extends ApiClient{


    public ApiClientLocal() {
        this.clienteHttpClient = new ClienteHttpClient("http://localhost:8080/api/v1");
        this.polizaHttpClient = new PolizaHttpClient("http://localhost:8080/api/v1");

    }
    
    public List<Cliente> getClientes() throws Exception{
        return clienteHttpClient.getClientes();
    }

    public List<Cliente> setClientes(List<Cliente> clientes) throws Exception{
        return clienteHttpClient.agregarClientes(clientes);
    }
   
    public void eliminarClientes() throws Exception {
        clienteHttpClient.eliminarClientes();
    }
    
        public void eliminarPolizas() throws Exception {
            polizaHttpClient.eliminarPolizas();
    }
    
    public void eliminarCliente(String curp) throws Exception {
        clienteHttpClient.eliminarClientePorCurp(curp);
    }
    
    public List<Poliza> setPolizas(List<Poliza> polizas) throws Exception{
        return polizaHttpClient.agregarPolizas(polizas);
    }  
    
}
