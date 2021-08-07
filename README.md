# ifood Backend Test

Repositório para resolução do desafio da empresa iFood em: https://github.com/ifood/ifood-backend-basic-test

## Tecnologias utilizadas
- Java 11
- Spring Boot 2.x
- Resilience4j
- Spring Open Feign

## Como funciona?
A aplicação é uma API que consiste em basicamente dois endpoints que retornam informações respeito do clima:

### Endpoints

    GET /about -> Verifica se a aplicação está de pé.
    GET /weather?city={cidade} -> Retorna as informações sobre o clima atual de acordo com a cidade.

Para obter as informações sobre o clima, a aplicação está integrada com a OpenWeatherMaps. Segue um exemplo do JSON de 
resposta:

### Exemplo de Resposta

    {
        "id": "1427e5cf-d58d-46ec-9981-22e0ae54f625",
        "cityName": "São Paulo",
        "temperature": {
            "id": "1427e5cf-d58d-46ec-9981-22e0ae54f625",
            "temperature": "288.64",
            "temperatureMin": "287.09",
            "temperatureMax": "289.36",
            "feelsLike": "288.54",
            "humidity": 88
        },
        "weatherDescription": "broken clouds",
        "wind": {
            "id": "1427e5cf-d58d-46ec-9981-22e0ae54f625",
            "speed": "1.34",
            "deg": "70",
            "gust": "2.24"
        },
        "updatedAt": "2021-08-07T00:54:22.508761489"
    }

As temperaturas estão na unidade Kelvin.

## Cache

### Como foi feito?

Para evitar que a aplicação faça requisições desnecessárias ao OpenWeatherMaps foi desenvolvido um sistema simples de 
Cache utilizando um SyncronizedHashMap, armazenando a cidade como key e o seu respetivo clima como value. 

### Mecanismo de limpeza 
A cada três minutos o job de limpeza é executado. O job irá percorrer por todo o mapa verificando se há algum elemento 
no mapa cuja atualização esteja ultrapassada — considere um elemento como ultrapassado aquele cuja última 
atualização se deu a mais de 30 minutos da execução do job.

Os elementos encontrados serão excluídos do Mapa.

## Resilience4j
No desafio original foi sugerido a utilização da biblioteca de tolerância a falhas Hystrix. Entretanto, a Hystrix não 
está mais em desenvolvimento e para os novos projetos a sugestão é utilizar Resilience4, justamente o que foi feito 
neste projeto.

Uma das grandes vantagens da utilização do Resilience4j em comparação com o Hystrix é o fato do Resilience4j possuir 
apenas uma dependência externa (Vavr), enquanto o Hystrix possui várias dependências externas como Guava e Apache 
Commons. Para saber mais a respeito do Resilience4j, acesse: https://github.com/resilience4j/resilience4j#readme

### Circuit Breaker

Circuit Breaker é um padrão para prevenção de erros em cascata quando um serviço externo a nossa explicação está 
indisponível. Depois de um número de requisições mal-sucedidas, a aplicação irá rejeitar quaisquer outras requisições
dentro de um determinado período.

O CircuitBreaker da aplicação possui as seguintes configurações: 

    CircuitBreakerConfig config = CircuitBreakerConfig
                .custom()
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .slidingWindowSize(4)
                .slowCallRateThreshold(50.0f)
                .slowCallDurationThreshold(Duration.ofSeconds(2))
                .waitDurationInOpenState(Duration.ofSeconds(10))
                .build();

O CircuitBreaker irá ser ativado caso metade das requisições responderem num tempo maior que 2 segundos. A partir disso,
o Breaker estará no estado de OPEN durante 10 segundos. Isso significa que qualquer requisição durante estes 10 segundos
serão rejeitadas automaticamente, lançando HttpClientErrorException com status 500 (INTERNAL_SERVER_ERROR).

## Pontos de melhoria

- Pensar numa estrutura de Cache mais robusta a partir de uma biblioteca específica.
- Incluir mais ferramentas de tolerância a falhas, como retry, fallbacks e demais funcionalidades disponíveis no 
  Resilience4j.
- Testes de carga para verificar o comportamento da aplicação lidando com requisições concorrentes.
- Dockerizar a aplicação (Em andamento...)