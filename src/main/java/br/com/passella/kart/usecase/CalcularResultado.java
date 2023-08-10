package br.com.passella.kart.usecase;

import br.com.passella.kart.utils.LocalTimeUtils;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class CalcularResultado {
    public Resultados execute(final List<Input> inputs) {
        final var outputs = inputs
                .parallelStream()
                .collect(Collectors.groupingBy(Input::codigoPiloto))
                .entrySet()
                .stream()
                .map(CalcularResultado::mapResultados)
                .sorted((o1, o2) -> o1.classificacao.tempoTotalProva().compareTo(o2.classificacao().tempoTotalProva()))
                .toList();
        return new Resultados(getSorted(outputs));
    }

    private static Resultado mapResultados(final Map.Entry<String, List<Input>> inputList) {
        final var classificacao = inputList
                .getValue()
                .parallelStream()
                .map(input -> new Classificacao(1, input.codigoPiloto(), input.nomePiloto(), 1, input.tempoVolta(), input.velocidadeMediaVolta()))
                .reduce((classificacao1, classificacao2) -> {
                    final var quantidadeVoltasCompletadas = classificacao1.quantidadeVoltasCompletadas() + classificacao2.quantidadeVoltasCompletadas();
                    final var tempoTotalProva = plusLocalTime(classificacao1.tempoTotalProva(), classificacao2.tempoTotalProva());
                    final var velocidadeMedia = classificacao1.velocidadeMedia() + classificacao2.velocidadeMedia();
                    return new Classificacao(1, classificacao1.codigoPiloto(), classificacao1.nomePiloto(), quantidadeVoltasCompletadas, tempoTotalProva, velocidadeMedia);
                })
                .map(classificacaoMedia -> new Classificacao(classificacaoMedia.posicaoChegada(), classificacaoMedia.codigoPiloto(), classificacaoMedia.nomePiloto(), classificacaoMedia.quantidadeVoltasCompletadas(), classificacaoMedia.tempoTotalProva(), classificacaoMedia.velocidadeMedia() / classificacaoMedia.quantidadeVoltasCompletadas()))
                .orElse(null);
        final var melhorVolta = inputList
                .getValue()
                .stream()
                .min(Comparator.comparing(Input::tempoVolta))
                .orElse(null);
        return new Resultado(classificacao, melhorVolta);
    }

    private static List<Resultado> getSorted(final List<Resultado> resultados) {
        final var list = new ArrayList<Resultado>();
        for (var i = 0; i < resultados.size(); i++) {
            final var output = resultados.get(i).classificacao();
            final var classificacao = new Classificacao(i + 1, output.codigoPiloto(), output.nomePiloto(), output.quantidadeVoltasCompletadas(), output.tempoTotalProva(), output.velocidadeMedia());
            list.add(new Resultado(classificacao, resultados.get(i).melhorVolta()));
        }
        return list;
    }

    private static LocalTime plusLocalTime(final LocalTime localTime1, final LocalTime localTime2) {
        return localTime1
                .plusHours(localTime2.getHour())
                .plusMinutes(localTime2.getMinute())
                .plusSeconds(localTime2.getSecond())
                .plusNanos(localTime2.getSecond());
    }

    public record Input(LocalTime hora, String codigoPiloto, String nomePiloto, Integer numeroVolta, LocalTime tempoVolta, Double velocidadeMediaVolta) {
    }

    public record Classificacao(Integer posicaoChegada, String codigoPiloto, String nomePiloto, Integer quantidadeVoltasCompletadas, LocalTime tempoTotalProva, Double velocidadeMedia) {
    }

    public record Resultado(CalcularResultado.Classificacao classificacao, CalcularResultado.Input melhorVolta) {
    }

    public record Resultados(List<Resultado> resultados) {
        public Input getMelhorVoltaCorrida() {
            return resultados
                    .stream()
                    .map(Resultado::melhorVolta)
                    .min(Comparator.comparing(Input::tempoVolta))
                    .orElse(null);
        }

        public List<Map.Entry<Classificacao, LocalTime>> getAtrasoTempo() {
            return resultados
                    .parallelStream()
                    .map(Resultado::classificacao)
                    .map(classificacao -> {
                        final var diferenca = Duration.between(classificacao.tempoTotalProva(), resultados.get(0).classificacao().tempoTotalProva());
                        final var time = LocalTimeUtils.fromDuration(diferenca);
                        return (Map.Entry<Classificacao, LocalTime>) new AbstractMap.SimpleEntry<>(classificacao, time);
                    })
                    .filter(classificacaoLocalTimeEntry -> classificacaoLocalTimeEntry.getValue().isAfter(LocalTime.of(0,0,0)))
                    .toList();
        }
    }
}
