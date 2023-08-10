package br.com.passella.kart.utils;

import org.apache.commons.lang3.RegExUtils;

import java.time.Duration;
import java.time.LocalTime;

public class LocalTimeUtils {
    public static LocalTime fromDuration(final Duration duration) {
        return LocalTime.of(Math.abs(duration.toHoursPart()), Math.abs(duration.toMinutesPart()), Math.abs(duration.toSecondsPart()), Math.abs(duration.toNanosPart()));
    }

    public static LocalTime fromInputString(final String hora) {
        return LocalTime.parse(hora);
    }

    public static LocalTime fromInputString(final String valor, final String format) {
        final var parts = valor.split("[:.]");
        return LocalTime.of(0,Integer.parseInt(parts[0]),Integer.parseInt(parts[1]) ,Integer.parseInt(parts[2]) );
    }
}
