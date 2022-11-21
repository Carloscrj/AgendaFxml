package com.example.agendaactividad.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * Funciones auxiliares para el manejo de fechas
 *
 *
 */
public class DateUtil {

    /** El patrón de fecha que se utiliza para la conversión. Cambia como quieras */
    private static final String DATE_PATTERN = "dd.MM.yyyy";

    /** El formateador de fechas. */
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_PATTERN);

    /**
     *
     * Devuelve la fecha dada como una cadena bien formateada. Lo anterior definido
     * {@link DateUtil#DATE_PATTERN} es usado.
     *
     * @param date la fecha que se devolverá como un String
     * @return formateado a string
     */
    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

    /**
     * Convierte un String en el formato del definido {@link DateUtil#DATE_PATTERN}
     * a un {@link LocalDate} objeto.
     *
     * Devuelve nula si el String no se pudo convertir
     *
     *
     * @param dateString la fecha es un String
     * @return el objeto de fecha o nulo si no se pudo convertir
     */
    public static LocalDate parse(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Comprueba la cadena si es una fecha válida.
     *
     * @param dateString
     * @return verdadero si el String es una fecha válida
     */
    public static boolean validDate(String dateString) {
        // Trate de analizar el String.
        return DateUtil.parse(dateString) != null;
    }
}
