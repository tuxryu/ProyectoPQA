
package mx.edu.uacm.is.slt.as.sistpolizas.httpClient;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import mx.edu.uacm.is.slt.as.sistpolizas.AuxiliarF.Convertir;
import mx.edu.uacm.is.slt.as.sistpolizas.model.Cliente;
import mx.edu.uacm.is.slt.as.sistpolizas.model.Poliza;

public class PolizaHttpClient {
        private String rutaBase;
    private HttpClient httpClient;
    private ObjectMapper mapper;

    public PolizaHttpClient(String rutaBase) {
        this.rutaBase = rutaBase;
        this.httpClient = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
    }
    
    // GET: /clientes
    public List<Poliza> getPolizas() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rutaBase + "/polizas"))
                .GET()
                .build();

        HttpResponse<String> respuesta = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (respuesta.statusCode() == 200) {
            String json = respuesta.body();
            return Arrays.asList(mapper.readValue(json, Poliza[].class));
        } else {
            System.out.println("Error al obtener lista de polizas: " + respuesta.statusCode());
            return List.of();
        }
    }

    public List<Poliza> agregarPolizas(List<Poliza> polizas) throws Exception {
    // Convertimos la lista de pólizas a JSON
    String jsonBody = mapper.writeValueAsString(polizas);

    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(rutaBase + "/polizas")) // Cambiar al endpoint correcto
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
            .build();

    HttpResponse<String> respuesta = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    if (respuesta.statusCode() == 200 || respuesta.statusCode() == 201) {
        // Convertimos la respuesta a una lista de Poliza
        JavaType tipoLista = mapper.getTypeFactory().constructCollectionType(List.class, Poliza.class);
        return mapper.readValue(respuesta.body(), tipoLista);
    } else {
        System.out.println("Error al agregar pólizas: " + respuesta.statusCode());
        return null;
    }
    
}
    
    // POST: /poliza/{clave}/{tipo}/{monto}/{descripcion}/{curpCliente}
public Poliza agregarPoliza(Poliza poliza) throws Exception {
    String jsonBody = mapper.writeValueAsString(poliza);

    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(rutaBase + "/poliza/" 
                + poliza.getClave() + "/"
                + poliza.getTipo() + "/"
                + poliza.getMonto() + "/"
                + Convertir.espaciosAGuion(poliza.getDescripcion()) + "/"
                + poliza.getCurpCliente()))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
            .build();

    HttpResponse<String> respuesta = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    if (respuesta.statusCode() == 200 || respuesta.statusCode() == 201) {
        return mapper.readValue(respuesta.body(), Poliza.class);
    } else {
        System.out.println("Error al agregar póliza: " + respuesta.statusCode());
        return null;
    }
}

    // GET: /cliente/{curp}
    public Poliza getPolizaByClave(String clave) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rutaBase + "/poliza/" + clave))
                .GET()
                .build();

        HttpResponse<String> respuesta = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (respuesta.statusCode() == 200) {
            String json = respuesta.body();
            String body = respuesta.body();
            if (body == null || body.isBlank()) {
                System.out.println("Respuesta vacía: cliente no encontrado");
                return null;
            }
            return mapper.readValue(json, Poliza.class);
        } else {
            System.out.println("Error al obtener cliente: " + respuesta.statusCode());
            return null;
        }
    }

    public void eliminarPolizas() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rutaBase + "/polizas"))
                .DELETE()
                .build();

        HttpResponse<Void> respuesta = httpClient.send(request, HttpResponse.BodyHandlers.discarding());

        if (respuesta.statusCode() != 200 && respuesta.statusCode() != 204) {
            System.out.println("Error al eliminar todos los polizas: " + respuesta.statusCode());
        }
    }    
    
}
