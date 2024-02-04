[JAVA_BADGE]:https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white

[SPRING_BADGE]: https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white

[MONGO_BADGE]:https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white


<h1 align="center" style="font-weight: bold;">backend-challenge Ame Digital 💻</h1>

<div align="center">  

![java][JAVA_BADGE]
![spring][SPRING_BADGE]
![mongo][MONGO_BADGE]

</div>

<p align="center">
 <a href="#started">Getting Started</a> • 
  <a href="#routes">API Endpoints</a> •
 <a href="./Challenge.md">Ver Challenge</a>
</p>

<p align="center">
  <b>Resolução do backend-challenge da empresa Ame Digital</b>
</p>

<h2 id="started">🚀 Getting started</h2>

Clone o projeto:
```bash
git clone https://github.com/caiofrz/swapi-spring-OpenFeign.git
cd swapi-spring-OpenFeign
``````

<h3>Pre requisitos</h3>

- [Java 17+]([https://github.com/](https://www.java.com/pt-BR/download/manual.jsp))
- [MongoDB]([https://github.com](https://www.mongodb.com/pt-br))
- Dica: você pode usar o Mongo DB Atlas

<h3> Configuração das variáveis de ambiente</h2>

Use o `application.properties` como referência para o seu arquivo de configuração. Lembre-se de substituir a URL para a URL do seu banco.

```yaml
spring.data.mongodb.uri=mongodb+srv://sua_url
```

<h3>Rodando o projeto</h3>

```bash
mvn spring-boot:run
``````

<h3>Rodando os testes</h3>

```bash
mvn test
``````

<h2 id="routes">📍 API Endpoints</h2>

Here you can list the main routes of your API, and what are their expected request bodies.
​
| route | description                                          
|----------------------|-----------------------------------------------------
| <kbd>GET /planets/{id}</kbd>     | recupera um planeta específico por id [response details](#get-planetl)
| <kbd>GET /planets/?name={nome_do_planete}</kbd>     | recupera um planeta específico por
nome [response details](#get-planet)
| <kbd>GET /planets?page=0&size=3&sort=name,ASC</kbd>     | recupera todos os planetas cadastrados com paginação<br />
name = numero da pagina<br/> size = quantidade de itens <br/> sort,order = atributo de ordenação, direção da ordenação(
ASC, DESC)   [response details](#get-planet-all)
| <kbd>POST /planets</kbd>     | cadastra um novo planeta [request details](#post-planet)
| <kbd>DELETE /planets/{id}</kbd>     | deleta um planeta específico por id
| <kbd>GET /docs</kbd>     | documentação completa via Swagger

<h3 id="get-planet">GET /planets/{id} | /planets/?name=Endor </h3>

**RESPONSE**
```json
{
    _id: "ab545bjh89kphjbr8"
    "name": "Endor",
    "weather": "temperate",
    "terrain": "gas giant",
    "appearancesNumber": 1
}
```

<h3 id="get-planet-all">GET /planets?page=0&size=3&sort=name,ASC </h3>

**RESPONSE**

```json
{
  "content": [
    {
      "_id": "65a93a706d10b577c808f4f0",
      "name": "Terra",
      "weather": "teste",
      "terrain": "teste",
      "appearancesNumber": 2
    },
    {
      "_id": "65afbfc9413547755fe9ee73",
      "name": "Tatooine",
      "weather": "arid",
      "terrain": "desert",
      "appearancesNumber": 5
    },
    {
      "_id": "65afc032413547755fe9ee74",
      "name": "Bespin",
      "weather": "temperate",
      "terrain": "gas giant",
      "appearancesNumber": 1
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 3,
    "sort": [
      {
        "direction": "ASC",
        "property": "string",
        "ignoreCase": false,
        "nullHandling": "NATIVE",
        "ascending": true,
        "descending": false
      }
    ],
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalPages": 1,
  "totalElements": 3,
  "last": true,
  "size": 3,
  "number": 0,
  "sort": [
    {
      "direction": "ASC",
      "property": "string",
      "ignoreCase": false,
      "nullHandling": "NATIVE",
      "ascending": true,
      "descending": false
    }
  ],
  "numberOfElements": 3,
  "first": true,
  "empty": false
}
```

<h3 id="post-planet">POST /planets</h3>

**REQUEST**
```json
{
  "name": "Endor",
  "weather": "temperate",
  "terrain": "gas giant"
}
```

**RESPONSE**

```json
{
  _id: "ab545bjh89kphjbr8"
  "name": "Endor",
  "weather": "temperate",
  "terrain": "gas giant",
  "appearancesNumber": 1
}
```

## Funcionalidades

- CRUD dos planetas
- Consultas paginadas e ordenadas
- Integração com API a pública do [Star Wars](https://swapi.dev/api/) usando Spring Cloud OpenFeign


## Feedback

Se você tiver algum feedback, por favor não deixe de dá-lo.

Me contate através do [github](https://github.com/caiofrz)
ou [LinkedIn](https://www.linkedin.com/in/caio-ferraz-almeida/) 
