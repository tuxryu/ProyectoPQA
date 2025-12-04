package mx.edu.uacm.is.slt.as.sistpolizas.httpClient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import mx.edu.uacm.is.slt.as.sistpolizas.modelo.Beneficiario;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.List;
import java.net.http.HttpRequest;

public class BeneficiarioHttpClient {
    private String rutaBase;
    private HttpClient httpClient;
    private ObjectMapper mapper;

    public BeneficiarioHttpClient(String rutaBase) {
        this.rutaBase = rutaBase;
        this.httpClient = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule()); // Para manejar fechas correctamente
    }

    // 1. Obtener beneficiarios de una póliza
    public List<Beneficiario> getTodosLosBeneficiarios() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rutaBase + "/beneficiarios")) // <-- Ruta general
                .GET()
                .build();

        HttpResponse<String> respuesta = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (respuesta.statusCode() == 200) {
            return mapper.readValue(respuesta.body(), new TypeReference<List<Beneficiario>>(){});
        } else {
            System.out.println("Error al descargar la lista completa de beneficiarios. Status: " + respuesta.statusCode());
            return List.of();
        }
    }

    // 2. Guardar beneficiario (Usando JSON Body)
    public Beneficiario agregarBeneficiario(Beneficiario beneficiario) throws Exception {
        String jsonBody = mapper.writeValueAsString(beneficiario);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rutaBase + "/beneficiario"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> respuesta = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (respuesta.statusCode() == 200 || respuesta.statusCode() == 201) {
            return mapper.readValue(respuesta.body(), Beneficiario.class);
        } else {
            System.out.println("Error agregando beneficiario: " + respuesta.statusCode());
            return null;
        }
    }
}
