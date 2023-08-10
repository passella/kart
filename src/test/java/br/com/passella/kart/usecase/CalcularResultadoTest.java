package br.com.passella.kart.usecase;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@SpringBootTest
class CalcularResultadoTest {

    @Autowired
    private CalcularResultado calcularResultado;

    @Test
    void execute() {
        final var melhorVolta = new CalcularResultado.Input(LocalTime.of(23, 51, 14, 216), "038", "F.MASSA", 3, LocalTime.of(0, 1, 2, 769), 44.334);

        final var inputs = List.of(
                new CalcularResultado.Input(LocalTime.of(23, 49, 8, 277), "038", "F.MASSA", 1, LocalTime.of(0, 1, 2, 852), 44.275),
                new CalcularResultado.Input(LocalTime.of(23, 49, 10, 858), "033", "R.BARRICHELLO", 1, LocalTime.of(0, 1, 4, 352), 43.243),
                new CalcularResultado.Input(LocalTime.of(23, 49, 11, 75), "002", "K.RAIKKONEN", 1, LocalTime.of(0, 1, 4, 108), 43.408),
                new CalcularResultado.Input(LocalTime.of(23, 49, 12, 667), "023", "M.WEBBER", 1, LocalTime.of(0, 1, 4, 414), 43.202),
                new CalcularResultado.Input(LocalTime.of(23, 49, 30, 976), "015", "F.ALONSO", 1, LocalTime.of(0, 1, 18, 456), 35.47),
                new CalcularResultado.Input(LocalTime.of(23, 50, 11, 447), "038", "F.MASSA", 2, LocalTime.of(0, 1, 3, 170), 44.053),
                new CalcularResultado.Input(LocalTime.of(23, 50, 14, 860), "033", "R.BARRICHELLO", 2, LocalTime.of(0, 1, 4, 2), 43.48),
                new CalcularResultado.Input(LocalTime.of(23, 50, 15, 57), "002", "K.RAIKKONEN", 2, LocalTime.of(0, 1, 3, 982), 43.493),
                new CalcularResultado.Input(LocalTime.of(23, 50, 17, 472), "023", "M.WEBBER", 2, LocalTime.of(0, 1, 4, 805), 42.941),
                new CalcularResultado.Input(LocalTime.of(23, 50, 37, 987), "015", "F.ALONSO", 2, LocalTime.of(0, 1, 7, 11), 41.528),
                melhorVolta,
                new CalcularResultado.Input(LocalTime.of(23, 51, 18, 576), "033", "R.BARRICHELLO", 3, LocalTime.of(0, 1, 3, 716), 43.675),
                new CalcularResultado.Input(LocalTime.of(23, 51, 19, 44), "002", "K.RAIKKONEN", 3, LocalTime.of(0, 1, 3, 987), 43.49),
                new CalcularResultado.Input(LocalTime.of(23, 51, 21, 759), "023", "M.WEBBER", 3, LocalTime.of(0, 1, 4, 287), 43.287),
                new CalcularResultado.Input(LocalTime.of(23, 51, 46, 691), "015", "F.ALONSO", 3, LocalTime.of(0, 1, 8, 704), 40.504),
                new CalcularResultado.Input(LocalTime.of(23, 52, 1, 796), "011", "S.VETTEL", 1, LocalTime.of(0, 3, 31, 315), 13.169),
                new CalcularResultado.Input(LocalTime.of(23, 52, 17, 3), "038", "F.MASS", 4, LocalTime.of(0, 1, 2, 787), 44.321),
                new CalcularResultado.Input(LocalTime.of(23, 52, 22, 586), "033", "R.BARRICHELLO", 4, LocalTime.of(0, 1, 4, 10), 43.474),
                new CalcularResultado.Input(LocalTime.of(23, 52, 22, 120), "002", "K.RAIKKONEN", 4, LocalTime.of(0, 1, 3, 76), 44.118),
                new CalcularResultado.Input(LocalTime.of(23, 52, 25, 975), "023", "M.WEBBER", 4, LocalTime.of(0, 1, 4, 216), 43.335),
                new CalcularResultado.Input(LocalTime.of(23, 53, 6, 741), "015", "F.ALONSO", 4, LocalTime.of(0, 1, 20, 50), 34.763),
                new CalcularResultado.Input(LocalTime.of(23, 53, 39, 660), "011", "S.VETTEL", 2, LocalTime.of(0, 1, 37, 864), 28.435),
                new CalcularResultado.Input(LocalTime.of(23, 54, 57, 757), "011", "S.VETTEL", 3, LocalTime.of(0, 1, 18, 97), 35.633));
        final var executed = calcularResultado.execute(inputs);

        Assertions
                .assertThat(executed)
                .isNotNull();

        Assertions
                .assertThat(executed.resultados())
                .describedAs("Resultados não podem estar vazio")
                .isNotEmpty()
                .describedAs("Resultados com tamanho inválido")
                .hasSize(6);

        Assertions
                .assertThat(executed.getMelhorVoltaCorrida())
                .describedAs("Melhor volta calculada errada")
                .isEqualTo(melhorVolta);

        Assertions
                .assertThat(executed.getAtrasoTempo())
                .last()
                .extracting(Map.Entry::getValue)
                .isEqualTo(LocalTime.of(0,2 ,17 ,489 ));
    }
}
