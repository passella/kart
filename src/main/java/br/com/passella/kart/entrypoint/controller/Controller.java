package br.com.passella.kart.entrypoint.controller;

import br.com.passella.kart.entrypoint.controller.converter.ControllerConverter;
import br.com.passella.kart.usecase.CalcularResultado;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping("/api/v1/resultados")
public class Controller {

    private final CalcularResultado calcularResultado;
    private final ControllerConverter controllerConverter;

    public Controller(final CalcularResultado calcularResultado, final ControllerConverter controllerConverter) {
        this.calcularResultado = calcularResultado;
        this.controllerConverter = controllerConverter;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CalcularResultado.Resultados> post(@RequestPart("arquivo") final MultipartFile arquivo) throws IOException, ParseException {
        final var execute = calcularResultado.execute(controllerConverter.toInput(arquivo));
        return ResponseEntity.ok(execute);
    }

}
