# Kart

Desafio para calcular resultados de corridas

para executar será necessário ter o Java 17 instalado na máquina

#### Linux:
`./mvnw spring-boot:run`
#### Windows:
`.\mvnw spring-boot:run`

Após a aplicação terminar de subir, acessar [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html), é uma interface swagger, nela terá o endpoint para ser chamado:

`curl -X 'POST' \
'http://localhost:8080/api/v1/resultados' \
-H 'accept: */*' \
-H 'Content-Type: multipart/form-data' \
-F 'arquivo=@input.txt;type=text/plain'`

o padrão do arquivo é separado por tabs, existe um arquivo de exemplo na pasta raiz `input.txt`

use a interface web para realizar o input do arquivo

![](/home/passella/Dev/projetos/kart/img.png)
