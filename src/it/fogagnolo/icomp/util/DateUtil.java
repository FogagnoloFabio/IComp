
package it.fogagnolo.icomp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static final String DATETIME = "dd/MM/yyyy HH:mm:ss.SSS";

    /**
     * Questo metodo restituisce la data corrente formattata
     * 
     * @return java.lang.String La data formattata.
     */
    public static String getCurrentDate() {

        SimpleDateFormat s = new SimpleDateFormat(DATETIME);
        Date currentTime = new Date();
        String dateString = s.format(currentTime);

        return dateString;
    }

    public static String getElapsed(long inizio, long fine) {

        long elapsed = (fine - inizio) / 1000L;
        int sec = (int) elapsed % 60;
        elapsed = elapsed / 60;
        int min = (int) elapsed % 60;
        elapsed = elapsed / 60;

        String result = elapsed + "h " + min + "m " + sec + "s";
        return result;
    }

}
