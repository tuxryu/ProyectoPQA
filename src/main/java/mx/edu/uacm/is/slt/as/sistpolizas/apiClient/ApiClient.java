package mx.edu.uacm.is.slt.as.sistpolizas.apiClient;

import java.util.List;
import mx.edu.uacm.is.slt.as.sistpolizas.httpClient.ClienteHttpClient;
import mx.edu.uacm.is.slt.as.sistpolizas.httpClient.PolizaHttpClient;
import mx.edu.uacm.is.slt.as.sistpolizas.model.Cliente;
import mx.edu.uacm.is.slt.as.sistpolizas.model.Poliza;

public class ApiClient {

    protected ClienteHttpClient clienteHttpClient;
    protected PolizaHttpClient polizaHttpClient;

    public ApiClient() {
    }

    public Cliente agregarCliente(Cliente cliente) throws Exception {
        return clienteHttpClient.agregarCliente(cliente);
    }

    public Cliente getBYCurp(String curp) throws Exception {
        return clienteHttpClient.getClienteByCurp(curp);
    }

    public List<Poliza> getPolizas() throws Exception {
        return polizaHttpClient.getPolizas();
    }
    
    public Poliza getByClave(String clave) throws Exception{
        return polizaHttpClient.getPolizaByClave(clave);
    }
    
    public Poliza agregarPoliza(Poliza poliza) throws Exception{
        return polizaHttpClient.agregarPoliza(poliza);
    }

}
