package br.com.passella.kart.utils;

import java.time.Duration;
import java.time.LocalTime;

public class LocalTimeutils {
    public static LocalTime fromDuration(final Duration duration) {
        return LocalTime.of(Math.abs(duration.toHoursPart()), Math.abs(duration.toMinutesPart()), Math.abs(duration.toSecondsPart()), Math.abs(duration.toNanosPart()));
    }

    public static LocalTime fromString(String hora) {
        return LocalTime.parse(hora);
    }
}
