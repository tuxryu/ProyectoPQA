/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.uacm.is.slt.as.sistpolizas.controller;

import java.util.List;
import mx.edu.uacm.is.slt.as.sistpolizas.AuxiliarF.Convertir;
import mx.edu.uacm.is.slt.as.sistpolizas.apiClient.ApiClientLocal;
import mx.edu.uacm.is.slt.as.sistpolizas.apiClient.ApiClientRemoto;
import mx.edu.uacm.is.slt.as.sistpolizas.modelo.Beneficiario;
import mx.edu.uacm.is.slt.as.sistpolizas.modelo.Cliente;
import mx.edu.uacm.is.slt.as.sistpolizas.modelo.Poliza;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class PilizaController {
        @GetMapping("/polizas")
    public String mostrar(Model model) throws Exception{

        return "polizas";
        
    }
    
    @PostMapping("/polizas/consultar")
    public String polizaByClave(@RequestBody MultiValueMap<String, String> form, Model model) throws Exception {
        String claveSinLimpiar = form.getFirst("clave");
        String clave = (claveSinLimpiar != null) ? claveSinLimpiar.trim() : "";
        
        // 2. Imprimimos en consola para ver EXACTAMENTE qué está buscando
        System.out.println("------------------------------------------------");
        System.out.println("El usuario escribió: '" + claveSinLimpiar + "'");
        System.out.println("Buscando en BD la clave limpia: '" + clave + "'");
        System.out.println("------------------------------------------------");
        // -------------------

        ApiClientRemoto remoto = new ApiClientRemoto();
        ApiClientLocal local = new ApiClientLocal();
        // 1. INTENTO DE SINCRONIZACIÓN (El código que te di antes)
        try {
            var nuevasPolizas = remoto.getPolizas();
            var nuevosClientes = remoto.getClientes();
    
            if (nuevasPolizas != null && !nuevasPolizas.isEmpty()) {
                local.eliminarPolizas();
                local.eliminarClientes();
                local.setClientes(nuevosClientes);
                local.setPolizas(nuevasPolizas);
            }
        } catch (Exception e) {
            System.out.println("No se pudo conectar al remoto, usando datos locales.");
        }

        // 2. BÚSQUEDA LOCAL
        Poliza poliza = local.getByClave(clave);

        // 3. BUSQUEDA INDIVIDUAL (Si no estaba en la lista masiva)
        if (poliza == null) {
            try {
                Poliza polizaRemota = remoto.getByClave(clave);
                if (polizaRemota != null) {
                    poliza = local.agregarPoliza(polizaRemota);
                }
            } catch (Exception e) {
                System.out.println("Error buscando poliza individual remota");
            }
        }

        // --- CORRECCIÓN DEL ERROR 500 (RED DE SEGURIDAD) ---
        if (poliza == null) {
            // Si después de buscar por cielo, mar y tierra sigue siendo null,
            // creamos una vacía para que Thymeleaf no falle.
            poliza = new Poliza(); 
            poliza.setDescripcion("No encontrada"); // Opcional: para que sepas que pasó
            poliza.setClave("---");
        }   
        
        if (poliza != null && poliza.getClave() != null && !poliza.getClave().equals("---")) {
        try {
            System.out.println("Buscando beneficiarios para la póliza: " + poliza.getClave());
            
            // 1. Preguntamos al remoto si hay beneficiarios para esta póliza
            List<Beneficiario> benefsRemotos = remoto.getBeneficiariosByPoliza(poliza.getClave());
            
            if (benefsRemotos != null && !benefsRemotos.isEmpty()) {
                System.out.println("Encontrados " + benefsRemotos.size() + " beneficiarios remotos. Guardando...");
                // 2. Los guardamos en local
                local.agregarBeneficiarios(benefsRemotos);
            }
        } catch (Exception e) {
            System.out.println("Aviso: No se pudieron sincronizar beneficiarios: " + e.getMessage());
        }
    }

        model.addAttribute("poliza", poliza);
        return "polizabyclave";
    }
    

    @PostMapping("/polizas/registrar")
    public String registrarPoliza(@RequestBody MultiValueMap<String, String> form, Model model) throws Exception {
        // 1. Extraer datos del formulario
        String clave = form.getFirst("clave");
        // Nota: Convertir.stringAInt y stringADouble son métodos de tu clase AuxiliarF/Convertir
        int tipo = Convertir.stringAInt(form.getFirst("tipo"));
        double monto = Convertir.stringAFloat(form.getFirst("monto")); // O Double.parseDouble
        String descripcion = form.getFirst("descripcion");
        String curpCliente = form.getFirst("curpCliente"); // Asegúrate que el input se llame así en el HTML

        // 2. Crear el objeto
        Poliza poliza = new Poliza(clave, tipo, monto, descripcion, curpCliente);

        ApiClientLocal local = new ApiClientLocal();
        ApiClientRemoto remoto = new ApiClientRemoto();

        // 3. Lógica de Guardado (Igual que en Clientes: Remoto -> Local)
        try {
            Poliza polizaRemota = remoto.agregarPoliza(poliza);
            if (polizaRemota != null) {
                // Si remoto confirmó, guardamos esa versión
                poliza = local.agregarPoliza(polizaRemota);
            } else {
                // Si remoto falló, guardamos localmente directo
                System.out.println("Remoto devolvió null, guardando local...");
                poliza = local.agregarPoliza(poliza);
            }
        } catch (Exception e) {
            System.out.println("Error conectando con remoto: " + e.getMessage());
            // Intento final local
            try {
                 poliza = local.agregarPoliza(poliza);
            } catch (Exception ex) {
                 System.out.println("Error fatal al guardar póliza local.");
                 poliza = null;
            }
        }

        // 4. Red de Seguridad para la Vista
        if (poliza == null) {
            poliza = new Poliza();
            poliza.setDescripcion("Error al guardar");
            poliza.setClave(clave);
        }

        model.addAttribute("poliza", poliza);
        return "polizabyclave"; // O la vista que prefieras mostrar al finalizar
    }
    
}
