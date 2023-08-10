package br.com.passella.kart.entrypoint;

import br.com.passella.kart.usecase.CalcularResultado;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/resultados")
public class Controller {

    private final CalcularResultado calcularResultado;
    private final ControllerConverter controllerConverter;

    public Controller(final CalcularResultado calcularResultado) {
        this.calcularResultado = calcularResultado;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> post(@RequestPart("arquivo") MultipartFile arquivo) {
        calcularResultado.execute()
        return ResponseEntity.ok(arquivo.getOriginalFilename());
    }

}
