package br.com.passella.kart.utils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class DoubleUtils {
    public static Double parseDouble(final String valor) throws ParseException {
        final var locale = new Locale("pt", "BR");
        final var format = NumberFormat.getInstance(locale);
        final var number = format.parse(valor);
        return number.doubleValue();
    }
}
