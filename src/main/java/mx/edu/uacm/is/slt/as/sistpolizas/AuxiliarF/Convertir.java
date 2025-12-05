package mx.edu.uacm.is.slt.as.sistpolizas.AuxiliarF;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class Convertir {

    public static OffsetDateTime stringAOffsetDateTime(String fechaStr) {
        try {
            return OffsetDateTime.parse(fechaStr, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        } catch (DateTimeParseException e) {
            System.err.println(" Formato de fecha y hora invalido: " + fechaStr);
            return null;
        }
    }

    /**
     * Convierte un String a int.
     *
     * @param numeroStr la cadena con el n�mero
     * @return int o 0 si hay error
     */
    public static int stringAInt(String numeroStr) {
        try {
            return Integer.parseInt(numeroStr);
        } catch (NumberFormatException e) {
            System.err.println("Numero entero invalido: " + numeroStr);
            return 0;
        }
    }

    /**
     * Convierte un String a float.
     *
     * @param numeroStr la cadena con el n�mero
     * @return float o 0.0f si hay error
     */
    public static float stringAFloat(String numeroStr) {
        try {
            return Float.parseFloat(numeroStr);
        } catch (NumberFormatException e) {
            System.err.println("Numero decimal invalido: " + numeroStr);
            return 0.0f;
        }
    }

    /**
     * Convierte un String a UUID.
     *
     * @param uuidStr la cadena con el UUID
     * @return UUID o null si hay error
     */
    public static UUID stringAUUID(String uuidStr) {
        try {
            return UUID.fromString(uuidStr);
        } catch (IllegalArgumentException e) {
            System.err.println("UUID invalido: " + uuidStr);
            return null;
        }
    }
        //reemplazarEspaciosPorGuion
        public static String espaciosAGuion(String entrada) {
        if (entrada == null) {
            return null;
        }
        return entrada.contains(" ") ? entrada.replace(" ", "-") : entrada;
    }
    
}
