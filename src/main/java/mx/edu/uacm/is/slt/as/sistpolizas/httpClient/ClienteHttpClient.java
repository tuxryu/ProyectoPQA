package mx.edu.uacm.is.slt.as.sistpolizas.httpClient;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import mx.edu.uacm.is.slt.as.sistpolizas.AuxiliarF.Convertir;
import mx.edu.uacm.is.slt.as.sistpolizas.model.Cliente;

public class ClienteHttpClient {

    private String rutaBase;
    private HttpClient httpClient;
    private ObjectMapper mapper;

    public ClienteHttpClient(String rutaBase) {
        this.rutaBase = rutaBase;
        this.httpClient = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
    }

    // GET: /cliente/{curp}
    public Cliente getClienteByCurp(String curp) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rutaBase + "/cliente/" + curp))
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
            return mapper.readValue(json, Cliente.class);
        } else {
            System.out.println("Error al obtener cliente: " + respuesta.statusCode());
            return null;
        }
    }

    // GET: /clientes
    public List<Cliente> getClientes() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rutaBase + "/clientes"))
                .GET()
                .build();

        HttpResponse<String> respuesta = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (respuesta.statusCode() == 200) {
            String json = respuesta.body();
            return Arrays.asList(mapper.readValue(json, Cliente[].class));
        } else {
            System.out.println("Error al obtener lista de clientes: " + respuesta.statusCode());
            return List.of();
        }
    }

    // POST: /cliente/{...} (pero el servidor espera un objeto en el body)
    public Cliente agregarCliente(Cliente cliente) throws Exception {
        String jsonBody = mapper.writeValueAsString(cliente);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rutaBase + "/cliente/" + cliente.getCurp() + "/"
                        + Convertir.espaciosAGuion(cliente.getDireccion()) + "/"
                        + cliente.getFechaNacimiento() + "/"
                        + cliente.getNombres() + "/"
                        + cliente.getPrimerApellido() + "/"
                        + cliente.getSegundoApellido()))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> respuesta = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (respuesta.statusCode() == 200 || respuesta.statusCode() == 201) {
            return mapper.readValue(respuesta.body(), Cliente.class);
        } else {
            System.out.println("Error al agregar cliente: " + respuesta.statusCode());
            return null;
        }
    }

    public List<Cliente> agregarClientes(List<Cliente> clientes) throws Exception {
        // Convertimos la lista de clientes a JSON
        String jsonBody = mapper.writeValueAsString(clientes);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rutaBase + "/clientes")) // Nuevo endpoint
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> respuesta = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (respuesta.statusCode() == 200 || respuesta.statusCode() == 201) {
            // Convertimos la respuesta a una lista de Cliente
            JavaType tipoLista = mapper.getTypeFactory().constructCollectionType(List.class, Cliente.class);
            return mapper.readValue(respuesta.body(), tipoLista);
        } else {
            System.out.println("Error al agregar clientes: " + respuesta.statusCode());
            return null;
        }
    }

// DELETE: /clientes
    public void eliminarClientes() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rutaBase + "/clientes"))
                .DELETE()
                .build();

        HttpResponse<Void> respuesta = httpClient.send(request, HttpResponse.BodyHandlers.discarding());

        if (respuesta.statusCode() != 200 && respuesta.statusCode() != 204) {
            System.out.println("Error al eliminar todos los clientes: " + respuesta.statusCode());
        }
    }

// DELETE: /cliente/{curp}
    public Cliente eliminarClientePorCurp(String curp) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rutaBase + "/cliente/" + curp))
                .DELETE()
                .build();

        HttpResponse<String> respuesta = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (respuesta.statusCode() == 200) {
            return mapper.readValue(respuesta.body(), Cliente.class);
        } else {
            System.out.println("Error al eliminar cliente con CURP " + curp + ": " + respuesta.statusCode());
            return null;
        }
    }

}
