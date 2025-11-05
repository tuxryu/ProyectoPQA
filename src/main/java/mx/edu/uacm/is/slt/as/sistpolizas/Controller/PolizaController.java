package mx.edu.uacm.is.slt.as.sistpolizas.controller;

import mx.edu.uacm.is.slt.as.sistpolizas.apiClient.ApiClientLocal;
import mx.edu.uacm.is.slt.as.sistpolizas.apiClient.ApiClientRemoto;
import mx.edu.uacm.is.slt.as.sistpolizas.model.Cliente;
import mx.edu.uacm.is.slt.as.sistpolizas.model.Poliza;



@Controller
public class PolizaController {
       
    public String mostrar(Model model) throws Exception{

        return "polizas";
        
    }
    
    
    public String polizaByClave(@RequestBody MultiValueMap<String, String> form, Model model) throws Exception {
        String clave = form.getFirst("clave");
        ApiClientRemoto remoto = new ApiClientRemoto();
        ApiClientLocal local = new ApiClientLocal();
                        local.eliminarPolizas();
        local.eliminarClientes();
        local.setClientes(remoto.getClientes());


        local.setPolizas(remoto.getPolizas());
        Poliza poliza = local.getByClave(clave);

        if (poliza == null) {
            if (remoto.getByClave(clave) == null) {

                poliza = new Poliza();
            }else{
                poliza = local.agregarPoliza(remoto.getByClave(clave));
            }
        }

        model.addAttribute("poliza", poliza);
        return "polizabyclave";
    }
    
}
