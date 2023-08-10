package br.com.passella.kart.entrypoint.controller.converter;

import br.com.passella.kart.usecase.CalcularResultado.Input;
import br.com.passella.kart.utils.DoubleUtils;
import br.com.passella.kart.utils.LocalTimeUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ControllerConverter {
    public List<Input> toInput(final MultipartFile arquivo) throws IOException, ParseException {
        final var conteudo = new String(arquivo.getBytes());
        final var linhas = conteudo.split(System.lineSeparator());
        final var inputs = new ArrayList<Input>();
        for (var i = 1; i < linhas.length; i++) {
            final var linha = linhas[i];
            final var campos = linha.split("\t");
            final var hora = campos[0];
            final var piloto = campos[1];
            final var numeroVolta = campos[2];
            final var tempoVolta = campos[3];
            final var velocidadeMedia = campos[4];
            final var dadosPiloto = piloto.split(" ");
            final var input = new Input(LocalTimeUtils.fromInputString(hora),
                    dadosPiloto[0].trim(),
                    dadosPiloto[2].trim(),
                    Integer.parseInt(numeroVolta),
                    LocalTimeUtils.fromInputString(tempoVolta, "m:ss.SSS"),
                    DoubleUtils.parseDouble(velocidadeMedia));
            inputs.add(input);
        }

        return inputs;
    }
}
