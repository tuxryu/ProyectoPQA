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

    @GetMapping("/clientes")
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

        // --- INICIO DE LA CORRECCIÓN ---
        
        // 1. Intentamos traer los clientes del servidor remoto PRIMERO
        var nuevosClientes = remoto.getClientes();

        // 2. Verificamos: ¿Realmente llegaron datos?
        if (nuevosClientes != null && !nuevosClientes.isEmpty()) {
            System.out.println("Conexión exitosa. Actualizando base de datos de Clientes...");
            
            // Solo si la descarga fue exitosa, borramos lo viejo
            local.eliminarClientes(); 
            // Y guardamos lo nuevo
            local.setClientes(nuevosClientes);
        } else {
            // Si falló, avisamos en consola y NO borramos nada
            System.out.println("Error o lista vacía desde remoto. Usando datos locales de Clientes.");
        }
        
        // --- FIN DE LA CORRECCIÓN ---

        // 3. Ahora buscamos el cliente en la BD local (sea la versión vieja o la nueva)
        Cliente cliente = local.getBYCurp(curp);

        if (cliente == null) {
            // Intento extra de buscar individualmente en remoto si no está en local
            if (remoto.getBYCurp(curp) == null) {
                cliente = new Cliente(false);
            } else {
                cliente = local.agregarCliente(remoto.getBYCurp(curp));
            }
        }

        model.addAttribute("cliente", cliente);
        return "clientebycurp";
    }

    @PostMapping("/clientes/registrar")
    public String agregarCliente(@RequestBody MultiValueMap<String, String> form, Model model) throws Exception {
        // 1. Recibir datos
        String curp = form.getFirst("curp");
        String fecha = form.getFirst("fecha");
        String nombres = form.getFirst("nombres");
        String primerApellido = form.getFirst("primerApellido");
        String segundoApellido = form.getFirst("segundoApellido");
        String direccion = form.getFirst("direccion");
        
        Cliente cliente;

        // 2. Crear el objeto (Validando string vacio)
        if (segundoApellido == null || segundoApellido.trim().isEmpty()) {
            cliente = new Cliente(nombres, primerApellido, direccion, curp, Convertir.stringAOffsetDateTime(fecha));
        } else {
            cliente = new Cliente(nombres, primerApellido, segundoApellido, direccion, curp, Convertir.stringAOffsetDateTime(fecha));
        }

        ApiClientLocal local = new ApiClientLocal();
        ApiClientRemoto remoto = new ApiClientRemoto();

        // 3. Intentar guardar en Remoto y luego en Local
        try {
            Cliente clienteRemoto = remoto.agregarCliente(cliente);
            if (clienteRemoto != null) {
                // Si remoto respondió bien, guardamos esa versión confirmada
                cliente = local.agregarCliente(clienteRemoto);
            } else {
                // Si remoto falló (dio null), intentamos guardar la versión local directo
                System.out.println("Remoto devolvió null, guardando en local...");
                cliente = local.agregarCliente(cliente);
            }
        } catch (Exception e) {
            System.out.println("Error al conectar con remoto: " + e.getMessage());
            // Si todo falla, intentamos guardar localmente lo que tenemos
            try {
                cliente = local.agregarCliente(cliente);
            } catch (Exception ex) {
                System.out.println("Error fatal guardando cliente localmente.");
                cliente = null;
            }
        }

        // --- LA RED DE SEGURIDAD (Esto arregla tu error 500) ---
        if (cliente == null) {
            // Si después de todo el cliente es null, creamos uno vacío
            // Usamos tu constructor especial para clientes vacíos
            cliente = new Cliente(false); 
            // Opcional: Para que sepas que pasó algo raro
            cliente.setNombres("Error al guardar"); 
            cliente.setCurp(curp); // Para que al menos se vea la CURP intentada
        }
        // -------------------------------------------------------

        model.addAttribute("cliente", cliente);
        return "clientebycurp";
    }
}
