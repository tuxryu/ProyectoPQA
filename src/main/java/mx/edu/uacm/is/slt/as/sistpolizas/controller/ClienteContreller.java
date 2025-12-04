package mx.edu.uacm.is.slt.as.sistpolizas.controller;

import java.time.OffsetDateTime;
import mx.edu.uacm.is.slt.as.sistpolizas.AuxiliarF.Convertir;
import mx.edu.uacm.is.slt.as.sistpolizas.apiClient.ApiClientLocal;
import mx.edu.uacm.is.slt.as.sistpolizas.apiClient.ApiClientRemoto;
import mx.edu.uacm.is.slt.as.sistpolizas.httpClient.ClienteHttpClient;
import mx.edu.uacm.is.slt.as.sistpolizas.model.Cliente;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ClienteContreller {

    @GetMapping("/clientes")// volarr
    public String mostrar(Model model) throws Exception {
        return "clientes";
    }

    @GetMapping("/cliente")
    public String mostra(Model model) throws Exception {
        ApiClientRemoto remoto = new ApiClientRemoto();
        ApiClientLocal local = new ApiClientLocal();
        local.setClientes(remoto.getClientes());
        local.setPolizas(remoto.getPolizas());
        model.addAttribute("curp", new String());
        return "view";
    }

    @PostMapping("/clientes/consultar")
    public String clientebycurp(@RequestBody MultiValueMap<String, String> form, Model model) throws Exception {
        String curp = form.getFirst("curp");
        ApiClientRemoto remoto = new ApiClientRemoto();
        ApiClientLocal local = new ApiClientLocal();
        local.eliminarClientes();
        local.setClientes(remoto.getClientes());
        Cliente cliente = local.getBYCurp(curp);

        if (cliente == null) {
            if (remoto.getBYCurp(curp) == null) {

                cliente = new Cliente(false);
            }else{
                cliente = local.agregarCliente(remoto.getBYCurp(curp));
            }
        }

        model.addAttribute("cliente", cliente);
        return "clientebycurp";
    }


    public String agregarCliente(@RequestBody MultiValueMap<String, String> form, Model model) throws Exception {
        String curp = form.getFirst("curp");
        String fecha = form.getFirst("fecha");
        String nombres = form.getFirst("nombres");
        String primerApellido = form.getFirst("primerApellido");
        String segundoApellido = form.getFirst("segundoApellido");
        String direccion = form.getFirst("direccion");
        Cliente cliente;

        if ("" == segundoApellido) {
            cliente = new Cliente(nombres, primerApellido, direccion, curp, Convertir.stringAOffsetDateTime(fecha));
        } else {
            cliente = new Cliente(nombres, primerApellido, segundoApellido, direccion, curp, Convertir.stringAOffsetDateTime(fecha));
        }

        ApiClientLocal local = new ApiClientLocal();
        ApiClientRemoto remoto = new ApiClientRemoto();
        remoto.agregarCliente(cliente);
        cliente = local.agregarCliente(remoto.getBYCurp(curp));

        model.addAttribute("cliente", cliente);
        return "clientebycurp";
    }
}
