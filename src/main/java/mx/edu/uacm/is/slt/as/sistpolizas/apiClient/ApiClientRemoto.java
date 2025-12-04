
package mx.edu.uacm.is.slt.as.sistpolizas.apiClient;

import java.util.ArrayList;
import java.util.List;
import mx.edu.uacm.is.slt.as.sistpolizas.httpClient.*;
import mx.edu.uacm.is.slt.as.sistpolizas.model.Cliente;
import mx.edu.uacm.is.slt.as.sistpolizas.model.Poliza;

public class ApiClientRemoto extends ApiClient{


    public ApiClientRemoto() {
        this.clienteHttpClient = new ClienteHttpClient("http://nachintoch.mx:8080");
        this.polizaHttpClient = new PolizaHttpClient("http://nachintoch.mx:8080");
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
