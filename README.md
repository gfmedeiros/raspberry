# Raspberry Awards

## Pré-Requisitos

- JVM versão 11 ou superior;
- Maven para os testes;
- A porta 8080 deve estar disponível.

## Como iniciar a API

A API pode ser iniciada  através do código-fonte,  ou através do binário disponível na seção de releases.

### Através do Código Fonte (Maven)

Na pasta raiz do projeto, onde o arquivo `pom.xml` se encontra, rode
o commando:

    mvn spring-boot:run

### Através do Binário
Baixe o binário da seção de releases e execute o seguinte comando:

    java -jar raspberry-1.0.0.jar

## Como consumir a API

A API possui apenas um endpoint:

- GET /api: retorna a query conforme a especificação dada.

Para testar use seu browser ou cliente de sua preferência e acesse:

http://localhost:8080/api

## Como realizar os testes de integração

Na pasta raiz do projeto, onde o arquivo pom.xml se encontra, rode o commando:

    mvn test

## Implementação

### Importação dos Dados

A importação do CSV é feita através de um job do Spring Batch. 
Filmes com múltiplos produtores geram multiplas linhas no banco de dados, para consumo pela query.

### Query

A query é realizada usando uma window function particionada por produtor. Cada filme é comparado com o próximo filme do produtor para obter o intervalo entre prêmios.

### Web

A parte web foi feita usando um Spring MVC controller.