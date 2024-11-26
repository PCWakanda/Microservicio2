package org.example.microservicio2.Util;


import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class LogUtils {
    public static String formatLogMessage(String message) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String timestamp = OffsetDateTime.now().format(formatter);
        return String.format("%s INFO --- %s", timestamp, message);
    }
}
